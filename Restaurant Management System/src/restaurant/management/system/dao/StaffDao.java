/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                stmt.setInt(8, staff.getOwnerId());
                
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
                    
                    StaffData staff = new StaffData(id, fullName, restaurantName, phoneNumber, email, username, password);
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
                    
                    StaffData staff = new StaffData(id, fullName, restaurantName, phoneNumber, email, username, password);
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
    
    public StaffData checkEmail(String email) {
        String query = "SELECT * from staff where email=?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, email);
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String fullName = result.getString("full_name");
                    String address = result.getString("address");
                    String phoneNumber = result.getString("phone_number");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    StaffData staff = new StaffData(id, fullName, address, phoneNumber, email, username, password);
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
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
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
        String query = "INSERT INTO staff (full_name, restaurant_name, phone_number, email, username, password, account_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE')";
        Connection conn = mySql.openConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, staffRequestData.getFullName());
            stmt.setString(2, staffRequestData.getRestaurantName());
            stmt.setString(3, staffRequestData.getPhoneNumber());
            stmt.setString(4, staffRequestData.getEmail());
            stmt.setString(5, staffRequestData.getUsername());
            stmt.setString(6, temporaryPassword);
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
        try (PreparedStatement stmnt = conn.prepareStatement(sql)) {
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
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
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
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private boolean isUsernameRegistered(Connection conn, String username) throws SQLException {
        String query = "SELECT 1 FROM staff WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}