/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.ResetRequest;
import restaurant.management.system.model.RestaurantData;


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
            + "profile_picture MEDIUMBLOB,"
            + "restaurant_picture MEDIUMBLOB"
            + ")";
        
        String query = "INSERT INTO owner (full_name, restaurant_name, address, phone_number, email, username, password) "
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
    
    //login
    public OwnerData login(LoginRequest loginOData){
        String query = "SELECT * FROM owner WHERE email=? and password=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt= conn.prepareStatement(query);
            stmnt.setString(1,loginOData.getEmail());
            stmnt.setString(2,loginOData.getPassword());
            ResultSet result= stmnt.executeQuery();
            System.out.println("Result:" + result);
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
            byte[] restaurantPicture = result.getBytes("restaurant_picture");

            OwnerData owner = new OwnerData(id, fullName, restaurantName, phoneNumber, address, email, username, password);
            owner.setProfilePicture(profilePicture);
            owner.setRestaurantPicture(restaurantPicture);
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
    
    public boolean isEmailRegistered(String email) {
        String query = "SELECT 1 FROM owner WHERE email = ?";
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
    
    //Profile picture
    public boolean updateProfilePicture(int ownerId, byte[] profilePicture) {
        String query = "UPDATE owner SET profile_picture = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setBytes(1, profilePicture);
            stmnt.setInt(2, ownerId);
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean updateUsername(int ownerId, String currentUsername, String currentPassword, String newUsername) {
        String query = "UPDATE owner SET username = ? WHERE id = ? AND username = ? AND password = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, newUsername);
            stmnt.setInt(2, ownerId);
            stmnt.setString(3, currentUsername);
            stmnt.setString(4, currentPassword);

            int result = stmnt.executeUpdate();
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
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, ownerId);
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
    
    //Restaurant Picture
    public boolean updateRestaurantPicture(int ownerId, byte[] restaurantPicture) {
        String query = "UPDATE owner SET restaurant_picture = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setBytes(1, restaurantPicture);
            stmnt.setInt(2, ownerId);
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public byte[] getRestaurantPicture(int ownerId) {
        String query = "SELECT restaurant_picture FROM owner WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, ownerId);
            ResultSet result = stmnt.executeQuery();
            if (result.next()) {
                return result.getBytes("restaurant_picture");
            }
        } catch (Exception e) {
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public OwnerData getOwnerById(int ownerId) {
        String query = "SELECT * FROM owner WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, ownerId);
            ResultSet result = stmnt.executeQuery();
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
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public List<RestaurantData> getAllRestaurantsWithImages() {
        List<RestaurantData> restaurants = new ArrayList<>();
        String query = "SELECT id as owner_id, restaurant_name, address, restaurant_picture, phone_number, email, full_name " +
                       "FROM owner WHERE restaurant_picture IS NOT NULL AND restaurant_picture != ''";
        Connection conn = mySql.openConnection();

        try (
             PreparedStatement stmnt = conn.prepareStatement(query);
             ResultSet resultSet = stmnt.executeQuery()) {

            while (resultSet.next()) {
                RestaurantData restaurant = new RestaurantData();
                restaurant.setOwnerId(resultSet.getInt("owner_id"));
                restaurant.setRestaurantName(resultSet.getString("restaurant_name"));
                restaurant.setAddress(resultSet.getString("address"));
                restaurant.setRestaurantImage(resultSet.getBytes("restaurant_picture"));
                restaurant.setPhoneNumber(resultSet.getString("phone_number"));
                restaurant.setEmail(resultSet.getString("email"));
                restaurant.setOwnerName(resultSet.getString("full_name"));
                restaurants.add(restaurant);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching restaurants with images: " + e.getMessage());
        } finally {
            mySql.closeConnection(conn);
        }

        return restaurants;
    }

    public RestaurantData getRestaurantByOwnerId(int ownerId) {
        RestaurantData restaurant = null;
        String query = "SELECT id as owner_id, restaurant_name, address, restaurant_picture, phone_number, email, full_name " +
                       "FROM owner WHERE id = ? AND restaurant_picture IS NOT NULL";
        Connection conn = mySql.openConnection();

        try (
            PreparedStatement stmnt = conn.prepareStatement(query)) {
            stmnt.setInt(1, ownerId);

            try (ResultSet resultSet = stmnt.executeQuery()) {
                if (resultSet.next()) {
                    restaurant = new RestaurantData(); // Initialize the object here
                    restaurant.setOwnerId(resultSet.getInt("owner_id"));
                    restaurant.setRestaurantName(resultSet.getString("restaurant_name"));
                    restaurant.setAddress(resultSet.getString("address"));
                    restaurant.setRestaurantImage(resultSet.getBytes("restaurant_picture"));
                    restaurant.setPhoneNumber(resultSet.getString("phone_number"));
                    restaurant.setEmail(resultSet.getString("email"));
                    restaurant.setOwnerName(resultSet.getString("full_name"));
                }
            }

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error fetching restaurant by owner ID: " + e.getMessage());
            } finally {
                mySql.closeConnection(conn);
            }

            return restaurant;
    }
    public boolean updateOwnerProfile(int ownerId, String fullName, String restaurantName, String phoneNumber, String email, String address) {
    String query = "UPDATE owner SET full_name = ?, restaurant_name = ?, phone_number = ?, email = ?, address = ? WHERE id = ?";
    Connection conn = mySql.openConnection();
    try {
        PreparedStatement stmnt = conn.prepareStatement(query);
        stmnt.setString(1, fullName);
        stmnt.setString(2, restaurantName);
        stmnt.setString(3, phoneNumber);
        stmnt.setString(4, email);
        stmnt.setString(5, address);
        stmnt.setInt(6, ownerId);
        int result = stmnt.executeUpdate();
        
        System.out.println("Update Owner profile result: " + result + " for owner ID: " + ownerId);
        return result > 0;
    } catch (Exception e) {
        System.out.println("Error updating owner profile: " + e.getMessage());
        e.printStackTrace();
        return false;
    } finally {
        mySql.closeConnection(conn);
    }
    }
    
    public OwnerData checkEmail(String email){
        String query = "SELECT * from owner where email=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,email);
            ResultSet result = stmnt.executeQuery();
            if(result.next()){
               int id = result.getInt("id");
            String fullName = result.getString("full_name");
            String restaurantName = result.getString("restaurant_name");
            String phoneNumber = result.getString("phone_number");
            String address = result.getString("address");
            String username = result.getString("username");
            String password = result.getString("password");
                OwnerData owner = new OwnerData(id, fullName, restaurantName, phoneNumber, address, email, username, password);
                return owner;
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
        String query= "UPDATE owner SET password=? where email=?";
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