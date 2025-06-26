/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.model.StaffRequestData;

/**
 *
 * @author acer
 */
public class StaffRequestDao {
    MySqlConnection mySql = new MySqlConnection();
    
    public boolean addPendingRequest(StaffRequestData request){
        Connection conn = mySql.openConnection();

        // Modified table creation - removed UNIQUE constraint on email
        String createTableSQL = "CREATE TABLE IF NOT EXISTS staffrequest ("
                + "request_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "full_name VARCHAR(100) NOT NULL,"
                + "restaurant_name VARCHAR(100) NOT NULL,"
                + "phone_number VARCHAR(15) NOT NULL,"
                + "email VARCHAR(100) NOT NULL,"  // Removed UNIQUE constraint
                + "username VARCHAR(50) NOT NULL,"
                + "password VARCHAR(255) NOT NULL,"
                + "owner_id INT NOT NULL,"
                + "status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',"
                + "request_type VARCHAR(50),"
                + "request_description TEXT,"
                + "request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "processed_date TIMESTAMP NULL,"
                + "processed_by VARCHAR(100) NULL"
                + ")";

        String query = "INSERT INTO staffrequest (full_name, restaurant_name, phone_number, email, username, password, owner_id, request_type, request_description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement createTableStmt = conn.prepareStatement(createTableSQL);
            PreparedStatement stmnt = conn.prepareStatement(query);
            createTableStmt.executeUpdate();

            stmnt.setString(1, request.getFullName());
            stmnt.setString(2, request.getRestaurantName());
            stmnt.setString(3, request.getPhoneNumber());
            stmnt.setString(4, request.getEmail());
            stmnt.setString(5, request.getUsername());
            stmnt.setString(6, request.getPassword());
            stmnt.setInt(7, request.getOwnerId());
            stmnt.setString(8, request.getRequestType());
            stmnt.setString(9, request.getRequestDescription());
            int result = stmnt.executeUpdate();
            return result > 0;
        }catch(Exception e){
            e.printStackTrace(); // Add this to see the actual error
            System.out.println("Error in addPendingRequest: " + e.getMessage());
            return false;
        }finally {
            mySql.closeConnection(conn);
        }
    }
    
    public List<StaffRequestData> getAllPendingRequests(int ownerId) {
        List<StaffRequestData> requests = new ArrayList<>();
        String query = "SELECT * FROM staffrequest WHERE status = 'PENDING' AND owner_id = ? ORDER BY request_date DESC";
        Connection conn= mySql.openConnection();
        
        try (PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, ownerId);
            try (ResultSet result = stmnt.executeQuery()) {
                while (result.next()) {
                    StaffRequestData request = new StaffRequestData(
                        result.getInt("request_id"),
                        result.getString("full_name"),
                        result.getString("restaurant_name"),
                        result.getString("phone_number"),
                        result.getString("email"),
                        result.getString("status"),
                        result.getTimestamp("request_date"),
                        result.getTimestamp("processed_date"),
                        result.getString("processed_by")
                    );
                    request.setUsername(result.getString("username"));
                    request.setPassword(result.getString("password"));
                    request.setOwnerId(result.getInt("owner_id"));
                    request.setRequestType(result.getString("request_type"));
                    request.setRequestDescription(result.getString("request_description"));
                    requests.add(request);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return requests;
    }
    
    public boolean approveRequest(int requestId, String adminEmail) {
        Connection conn = mySql.openConnection();
        try {
            // 1. Get the request details
            StaffRequestData request = getRequestById(requestId);
            if (request == null) return false;

            // 2. Update request status
            String updateRequestSQL = "UPDATE staffrequest SET status = 'APPROVED', " +
                                   "processed_date = CURRENT_TIMESTAMP, processed_by = ? " +
                                   "WHERE request_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(updateRequestSQL)) {
                stmt.setString(1, adminEmail != null ? adminEmail : "admin");
                stmt.setInt(2, requestId);
                if (stmt.executeUpdate() <= 0) return false;
            }

            // 3. Create staff account using chosen credentials
            StaffData newStaff = new StaffData(
                request.getFullName(),
                request.getRestaurantName(),
                request.getPhoneNumber(),
                request.getEmail()
            );
            newStaff.setUsername(request.getUsername());
            newStaff.setPassword(request.getPassword());
            newStaff.setAccountStatus("ACTIVE");
            newStaff.setOwnerId(request.getOwnerId());

            StaffDao staffDao = new StaffDao();
            boolean staffCreated = staffDao.register(newStaff);
            
            if (!staffCreated) {
                // Rollback the approval if staff creation fails
                String rollbackSQL = "UPDATE staffrequest SET status = 'PENDING', " +
                                   "processed_date = NULL, processed_by = NULL " +
                                   "WHERE request_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(rollbackSQL)) {
                    stmt.setInt(1, requestId);
                    stmt.executeUpdate();
                }
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean rejectRequest(int requestId, String processedBy) {
        Connection conn = mySql.openConnection();
        String query = "UPDATE staffrequest SET status = 'REJECTED', " +
                     "processed_date = CURRENT_TIMESTAMP, processed_by = ? " +
                     "WHERE request_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, processedBy);
            stmt.setInt(2, requestId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    private String generateUsername(String email) {
        return email.split("@")[0] + "_" + System.currentTimeMillis() % 1000;
    }

    private String generateTemporaryPassword() {
        return "Temp@" + (int)(Math.random() * 10000);
    }
    
    public StaffRequestData getRequestById(int requestId) {
        String query = "SELECT * FROM staffrequest WHERE request_id = ?";
        Connection conn= mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, requestId);
            
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    StaffRequestData req = new StaffRequestData(
                        result.getInt("request_id"),
                        result.getString("full_name"),
                        result.getString("restaurant_name"),
                        result.getString("phone_number"),
                        result.getString("email"),
                        result.getString("status"),
                        result.getTimestamp("request_date"),
                        result.getTimestamp("processed_date"),
                        result.getString("processed_by")
                    );
                    req.setUsername(result.getString("username"));
                    req.setPassword(result.getString("password"));
                    req.setOwnerId(result.getInt("owner_id"));
                    req.setRequestType(result.getString("request_type"));
                    req.setRequestDescription(result.getString("request_description"));
                    return req;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean hasExistingPendingRequest(String email) {
        String query = "SELECT COUNT(*) FROM staffrequest WHERE email = ? AND status = 'PENDING'";
        Connection conn= mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setString(1, email);
            
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    return result.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean cleanupOldRequests(int daysOld) {
        String query = "DELETE FROM staffrequest WHERE status != 'PENDING' AND processed_date < DATE_SUB(NOW(), INTERVAL ? DAY)";
        Connection conn= mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, daysOld);
            
            return stmnt.executeUpdate() >= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
