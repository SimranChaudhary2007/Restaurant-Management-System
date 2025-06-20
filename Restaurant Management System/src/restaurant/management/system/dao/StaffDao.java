/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.ResetRequest;
import restaurant.management.system.model.StaffData;

/**
 *
 * @author acer
 */
public class StaffDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean register(StaffData staff){
        Connection conn = mySql.openConnection();
        
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
            + "FOREIGN KEY (owner_id) REFERENCES owner(id) ON DELETE CASCADE"
            +")";
        
 
        String query = "INSERT INTO staff (full_name, restaurant_name, phone_number, email, username, password) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement createTableStmt = conn.prepareStatement(createTableSQL);
            PreparedStatement stmnt = conn.prepareStatement(query);
            createTableStmt.executeUpdate();
            
            stmnt.setString(1, staff.getFullName());
            stmnt.setString(2, staff.getRestaurantName());
            stmnt.setString(3, staff.getPhoneNumber());
            stmnt.setString(4, staff.getEmail());
            stmnt.setString(5, staff.getUsername());
            stmnt.setString(6, staff.getPassword()); 
            int result = stmnt.executeUpdate();
            return result > 0;
        }catch(Exception e){
            return false;
        }finally {
            mySql.closeConnection(conn);
        }
    }
    
    public StaffData login(LoginRequest loginSData){
        String query = "SELECT * FROM staff WHERE email=? and password=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt= conn.prepareStatement(query);
            stmnt.setString(1,loginSData.getEmail());
            stmnt.setString(2,loginSData.getPassword());
            ResultSet result= stmnt.executeQuery();
            System.out.println("Result:" + result);
            if (result.next()){
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
            
            } else{
                return null;
            }
        }catch(Exception e){
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean isEmailRegistered(String email) {
        String query = "SELECT 1 FROM staff WHERE email = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            ResultSet result = stmnt.executeQuery();
            return result.next();
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean updateProfilePicture(int staffId, byte[] profilePicture) {
        String query = "UPDATE staff SET profile_picture = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setBytes(1, profilePicture);
            stmnt.setInt(2, staffId);
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public byte[] getProfilePicture(int staffId) {
        String query = "SELECT profile_picture FROM staff WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, staffId);
            ResultSet result = stmnt.executeQuery();
            if (result.next()) {
                return result.getBytes("profile_picture");
            }
        } catch (Exception e) {
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public StaffData getStaffById(int staffId) {
        String query = "SELECT * FROM staff WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, staffId);
            ResultSet result = stmnt.executeQuery();
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
        } catch (Exception e) {
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public boolean updateStaffProfile(int staffId, String fullName, String restaurantName, String phoneNumber, String email) {
        String query = "UPDATE staff SET full_name = ?, restaurant_name = ?, phone_number = ?, email = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, fullName);
            stmnt.setString(2, restaurantName);
            stmnt.setString(3, phoneNumber);
            stmnt.setString(4, email);
            stmnt.setInt(5, staffId);
            int result = stmnt.executeUpdate();
            
            System.out.println("Update staff profile result: " + result + " for staff ID: " + staffId);
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error updating staff profile: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public StaffData checkEmail(String email){
        String query = "SELECT * from staff where email=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,email);
            ResultSet result = stmnt.executeQuery();
            if(result.next()){
               int id = result.getInt("id");
            String fullName = result.getString("full_name");
            String address = result.getString("address");
            String phoneNumber = result.getString("phone_number");
            String username = result.getString("username");
            String password = result.getString("password");
                StaffData staff = new StaffData(id, fullName, address, phoneNumber, email, username, password);
                return staff;
            } else {
                return null;
            }
        } catch (Exception e){
            return null;
        } finally{
            mySql.closeConnection(conn);
        }
    }
    
    public boolean updatePassword(ResetRequest reset){
        String query= "UPDATE cuxtomer SET password=? where email=?";
        Connection conn = mySql.openConnection();
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,reset.getPassword());           
            stmnt.setString(2,reset.getEmail());
            int result = stmnt.executeUpdate();
            return result>0;
        } catch(Exception e){
            return false;
        } finally{
            mySql.closeConnection(conn);
        }
    }
}

