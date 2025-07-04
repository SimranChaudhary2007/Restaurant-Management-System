/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.ResetRequest;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.model.StaffRequestData;

/**
 *
 * @author acer
 */
public class StaffDao {
    MySqlConnection mySql = new MySqlConnection();
    
    public boolean register(StaffData staff) {
        Connection conn = mySql.openConnection();
        try {
            conn.setAutoCommit(false); // Start transaction
            
            // Create table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS staff ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "full_name VARCHAR(100) NOT NULL,"
                + "restaurant_name VARCHAR(100) NOT NULL,"
                + "phone_number VARCHAR(20) NOT NULL,"
                + "email VARCHAR(100) NOT NULL UNIQUE,"
                + "username VARCHAR(50) NOT NULL UNIQUE,"
                + "password VARCHAR(255) NOT NULL,"
                + "owner_id INT,"
                + "position VARCHAR(50),"
                + "salary DECIMAL(10,2),"
                + "profile_picture MEDIUMBLOB,"
                + "account_status ENUM('PENDING', 'ACTIVE', 'REJECTED', 'SUSPENDED') DEFAULT 'PENDING',"
                + "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (owner_id) REFERENCES owner(id) ON DELETE CASCADE"
                + ")";
            
            try (PreparedStatement createTableStmt = conn.prepareStatement(createTableSQL)) {
                createTableStmt.executeUpdate();
            }

            // Check for duplicates first
            if (isEmailRegistered(conn, staff.getEmail())) {
                throw new SQLException("Email already registered");
            }
            if (isUsernameRegistered(conn, staff.getUsername())) {
                throw new SQLException("Username already taken");
            }

            // Insert new staff
            String query = "INSERT INTO staff (full_name, restaurant_name, phone_number, email, username, password, account_status, owner_id) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, staff.getFullName());
                stmt.setString(2, staff.getRestaurantName());
                stmt.setString(3, staff.getPhoneNumber());
                stmt.setString(4, staff.getEmail());
                stmt.setString(5, staff.getUsername());
                stmt.setString(6, staff.getPassword());
                stmt.setString(7, staff.getAccountStatus());
                
                    if (staff.getOwnerId() > 0) {
                   stmt.setInt(8, staff.getOwnerId());
               } else {
                   stmt.setNull(8, java.sql.Types.INTEGER);
               }

                
                int result = stmt.executeUpdate();
                conn.commit(); // Commit transaction
                return result > 0;
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    mySql.closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public StaffData login(LoginRequest loginSData) {
        if (loginSData == null || 
            loginSData.getEmail() == null || loginSData.getEmail().trim().isEmpty() ||
            loginSData.getPassword() == null || loginSData.getPassword().trim().isEmpty()) {
            return null;
        }

        String query = "SELECT * FROM staff WHERE email=? and password=?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, loginSData.getEmail());
            stmnt.setString(2, loginSData.getPassword());
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String fullName = result.getString("full_name");
                    String restaurantName = result.getString("restaurant_name");
                    String phoneNumber = result.getString("phone_number");
                    String email = result.getString("email");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    byte[] profilePicture = result.getBytes("profile_picture");
                    int ownerId = result.getInt("owner_id");
                    String accountStatus = result.getString("account_status");
                    Timestamp createdDate = result.getTimestamp("created_date");

                    StaffData staff = new StaffData(id, fullName, restaurantName, phoneNumber, email, username, password, accountStatus, createdDate, ownerId);
                    staff.setProfilePicture(profilePicture);
                    return staff;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean isEmailRegistered(String email) {
        String query = "SELECT 1 FROM staff WHERE email = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, email);
            try (ResultSet result = stmnt.executeQuery()) {
                return result.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean updateProfilePicture(int staffId, byte[] profilePicture) {
        if (staffId <= 0 || profilePicture == null || profilePicture.length == 0) {
            return false;
        }

        String query = "UPDATE staff SET profile_picture = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setBytes(1, profilePicture);
            stmnt.setInt(2, staffId);
            return stmnt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public byte[] getProfilePicture(int staffId) {
        String query = "SELECT profile_picture FROM staff WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, staffId);
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    return result.getBytes("profile_picture");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public StaffData getStaffById(int staffId) {
        String query = "SELECT * FROM staff WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, staffId);
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String fullName = result.getString("full_name");
                    String restaurantName = result.getString("restaurant_name");
                    String phoneNumber = result.getString("phone_number");
                    String email = result.getString("email");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    byte[] profilePicture = result.getBytes("profile_picture");
                    int ownerId = result.getInt("owner_id");
                    String accountStatus = result.getString("account_status");
                    Timestamp createdDate = result.getTimestamp("created_date");
                    
                    StaffData staff = new StaffData(id, fullName, restaurantName, phoneNumber, email, username, password, accountStatus, createdDate, ownerId);
                    staff.setProfilePicture(profilePicture);
                    return staff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public boolean updateStaffProfile(int staffId, String fullName, String restaurantName, String phoneNumber, String email) {
        if (staffId <= 0 || 
            fullName == null || fullName.trim().isEmpty() ||
            restaurantName == null || restaurantName.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            return false;
        }

        String query = "UPDATE staff SET full_name = ?, restaurant_name = ?, phone_number = ?, email = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, fullName);
            stmnt.setString(2, restaurantName);
            stmnt.setString(3, phoneNumber);
            stmnt.setString(4, email);
            stmnt.setInt(5, staffId);
            return stmnt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }    
    public boolean updateStaff(StaffData staff) {
        String query = "UPDATE staff SET position = ?, salary = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, staff.getPosition());
            if (staff.getSalary() != null) {
                stmnt.setBigDecimal(2, staff.getSalary());
            } else {
                stmnt.setBigDecimal(2, null);
            }
            stmnt.setInt(3, staff.getId());
            return stmnt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    // NEW METHOD: Delete staff by ID
    public boolean deleteStaff(int staffId) {
        String query = "DELETE FROM staff WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, staffId);
            return stmnt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    // NEW METHOD: Get all staff by owner ID
    public List<StaffData> getAllStaffByOwnerId(int ownerId) {
        List<StaffData> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff WHERE owner_id = ? ORDER BY created_date DESC";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, ownerId);
            try (ResultSet result = stmnt.executeQuery()) {
                while (result.next()) {
                    staffList.add(mapResultSetToStaffData(result));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return staffList;
    }
    
    public StaffData checkEmail(String email) {
        String query = "SELECT * from staff where email=?";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, email);
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String fullName = result.getString("full_name");
                    String restaurantName = result.getString("restaurant_name");
                    String phoneNumber = result.getString("phone_number");
                    String emailResult = result.getString("email");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    int ownerId = result.getInt("owner_id");
                    String accountStatus = result.getString("account_status");
                    Timestamp createdDate = result.getTimestamp("created_date");
                    
                    StaffData staff = new StaffData(id, fullName, restaurantName, phoneNumber, emailResult, username, password, accountStatus, createdDate, ownerId);
                    return staff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public boolean updatePassword(ResetRequest reset) {
        String query = "UPDATE staff SET password=? where email=?";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, reset.getPassword());
            stmnt.setString(2, reset.getEmail());
            return stmnt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean createStaffFromPendingRequest(StaffRequestData staffRequestData, String temporaryPassword) {
        String query = "INSERT INTO staff (full_name, restaurant_name, phone_number, email, username, password, account_status, owner_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE', ?)";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, staffRequestData.getFullName());
            stmt.setString(2, staffRequestData.getRestaurantName());
            stmt.setString(3, staffRequestData.getPhoneNumber());
            stmt.setString(4, staffRequestData.getEmail());
            stmt.setString(5, staffRequestData.getUsername());
            stmt.setString(6, temporaryPassword);
            stmt.setInt(7, staffRequestData.getOwnerId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean staffAccountExists(String email) {
        String sql = "SELECT COUNT(*) FROM staff WHERE email = ?";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setString(1, email);
            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }
    
    public boolean isUsernameRegistered(String username) {
        String query = "SELECT 1 FROM staff WHERE username = ?";
        Connection conn = mySql.openConnection();
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, username);
            try (ResultSet result = stmnt.executeQuery()) {
                return result.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    // Private helper methods
    private boolean isEmailRegistered(Connection conn, String email) throws SQLException {
        String query = "SELECT 1 FROM staff WHERE email = ?";
        try (
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private boolean isUsernameRegistered(Connection conn, String username) throws SQLException {
        String query = "SELECT 1 FROM staff WHERE username = ?";
        try (
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private StaffData mapResultSetToStaffData(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String fullName = result.getString("full_name");
        String restaurantName = result.getString("restaurant_name");
        String phoneNumber = result.getString("phone_number");
        String email = result.getString("email");
        String username = result.getString("username");
        String password = result.getString("password");
        int ownerId = result.getInt("owner_id");
        String position = result.getString("position");
        java.math.BigDecimal salary = result.getBigDecimal("salary");
        byte[] profilePicture = result.getBytes("profile_picture");
        String accountStatus = result.getString("account_status");
        Timestamp createdDate = result.getTimestamp("created_date");

        StaffData staff = new StaffData(id, fullName, restaurantName, phoneNumber, 
                                       email, username, password);

        staff.setOwnerId(ownerId);
        staff.setPosition(position);
        staff.setSalary(salary);
        staff.setProfilePicture(profilePicture);
        staff.setAccountStatus(accountStatus);
        staff.setCreatedDate(createdDate);

        return staff;
    }
    
    public List<StaffData> getApprovedStaff() {
        List<StaffData> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff WHERE account_status = 'ACTIVE'";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            try (ResultSet result = stmnt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String fullName = result.getString("full_name");
                    String restaurantName = result.getString("restaurant_name");
                    String phoneNumber = result.getString("phone_number");
                    String email = result.getString("email");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String position = result.getString("position");
                    double salary = result.getDouble("salary");
                    byte[] profilePicture = result.getBytes("profile_picture");
                    String accountStatus = result.getString("account_status");
                    java.sql.Timestamp createdDate = result.getTimestamp("created_date");
                    int ownerId = result.getInt("owner_id");

                    StaffData staff = new StaffData(id, fullName, restaurantName, phoneNumber, email, username, password, accountStatus, createdDate, ownerId);
                    staff.setProfilePicture(profilePicture);
                    staff.setPosition(position);
                    staff.setSalary(salary);
                    staffList.add(staff);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return staffList;
    }
    
    public boolean updateStaffPositionAndSalary(int staffId, String position, double salary) {
        String query = "UPDATE staff SET position = ?, salary = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, position);
            stmnt.setBigDecimal(2, java.math.BigDecimal.valueOf(salary));
            stmnt.setInt(3, staffId);
            return stmnt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean deleteStaffById(int staffId) {
        String query = "DELETE FROM staff WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, staffId);
            return stmnt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}