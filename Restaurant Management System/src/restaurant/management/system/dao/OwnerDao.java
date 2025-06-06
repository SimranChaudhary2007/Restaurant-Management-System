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
import restaurant.management.system.model.OwnerData;


/**
 *
 * @author acer
 */
public class OwnerDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean register(OwnerData owner){
        Connection conn = mySql.openConnection();
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS owner ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "full_name VARCHAR(100) NOT NULL,"
            + "restaurant_name VARCHAR(100) NOT NULL,"
            + "phone_number VARCHAR(20) NOT NULL,"
            + "address TEXT,"
            + "email VARCHAR(100) NOT NULL UNIQUE,"
            + "username VARCHAR(50) NOT NULL UNIQUE,"
            + "password VARCHAR(255) NOT NULL,"
            + "profile_picture BLOB,"
            + "restaurant_picture BLOB,"
            + ")";
        
        String query = "INSERT INTO owner (full_name, restaurant_name,address, phone_number, email, username, password) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement createTableStmt = conn.prepareStatement(createTableSQL);
            PreparedStatement stmnt = conn.prepareStatement(query);
            createTableStmt.executeUpdate();
            
            stmnt.setString(1, owner.getFullName());
            stmnt.setString(2, owner.getRestaurantName());
            stmnt.setString(3, owner.getRestaurantAddress());
            stmnt.setString(4, owner.getPhoneNumber());
            stmnt.setString(5, owner.getEmail());
            stmnt.setString(6, owner.getUsername());
            stmnt.setString(7, owner.getPassword()); 
            int result = stmnt.executeUpdate();
            return result > 0;
        }catch(Exception e){
            return false;
        }finally {
            mySql.closeConnection(conn);
        }
    }
    
    public OwnerData login(LoginRequest loginOData){
        String query = "SELECT * FROM owner WHERE email=? and password=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt= conn.prepareStatement(query);
            stmnt.setString(1,loginOData.getEmail());
            stmnt.setString(2,loginOData.getPassword());
            ResultSet result= stmnt.executeQuery();
            System.out.println("Result:" + result);
            if (result.next()){
                int id = result.getInt("id");
                String fullName = result.getString("full_name");
                String restaurantName = result.getString("restaurant_name");
                String phoneNumber = result.getString("phone_number");
                String address = result.getString("address");
                String email = result.getString("email");
                String username = result.getString("username");
                String password = result.getString("password");
                OwnerData owner = new OwnerData(id, fullName, restaurantName, phoneNumber, address, email, username, password);
                return owner;
            
            } else{
                return null;
            }
        }catch(Exception e){
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
     public boolean updateProfilePicture(int ownerId, byte[] profilePicture) {
        String query = "UPDATE owner SET profile_picture = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBytes(1, profilePicture);
            stmt.setInt(2, ownerId);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public byte[] getProfilePicture(int ownerId) {
        String query = "SELECT profile_picture FROM owner WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ownerId);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getBytes("profile_picture");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    // NEW METHOD: Get owner by ID
    public OwnerData getOwnerById(int ownerId) {
        String query = "SELECT * FROM owner WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ownerId);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String fullName = result.getString("full_name");
                String restaurantName = result.getString("restaurant_name");
                String phoneNumber = result.getString("phone_number");
                String address = result.getString("address");
                String email = result.getString("email");
                String username = result.getString("username");
                String password = result.getString("password");
                byte[] profilePicture = result.getBytes("profile_picture");
                
                OwnerData owner = new OwnerData(id, fullName, restaurantName, phoneNumber, address, email, username, password);
                owner.setProfilePicture(profilePicture);
                return owner;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
}