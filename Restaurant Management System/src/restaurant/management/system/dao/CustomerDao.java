/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import restaurant.management.system.database.MySqlConnection;
import java.sql.ResultSet;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.LoginRequest;

/**
 *
 * @author acer
 */
public class CustomerDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean register(CustomerData customer){
        Connection conn = mySql.openConnection();
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS customer ("
            +"id INT AUTO_INCREMENT PRIMARY KEY,"
            +"full_name VARCHAR(100) NOT NULL,"
            +"address TEXT,"
            +"phone_number VARCHAR(20) NOT NULL,"
            +"email VARCHAR(100) NOT NULL UNIQUE,"
            +"username VARCHAR(50) NOT NULL UNIQUE,"
            +"password VARCHAR(255) NOT NULL,"
            +"profile_picture BLOB"
            +")";
        
        
        String query = "INSERT INTO customer (full_name, address, phone_number, email, username, password) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement createTableStmt = conn.prepareStatement(createTableSQL);
            PreparedStatement stmnt = conn.prepareStatement(query);
            createTableStmt.executeUpdate();
            
            stmnt.setString(1, customer.getFullName());
            stmnt.setString(2, customer.getAddress());
            stmnt.setString(3, customer.getPhoneNumber());
            stmnt.setString(4, customer.getEmail());
            stmnt.setString(5, customer.getUsername());
            stmnt.setString(6, customer.getPassword()); 
            stmnt.setBytes(7, null);
            int result = stmnt.executeUpdate();
            return result > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            mySql.closeConnection(conn);
        }
    }
    
    public CustomerData login(LoginRequest loginCData){
        String query = "SELECT * FROM customer WHERE email=? and password=?";
        Connection conn= mySql.openConnection();
        try{
            PreparedStatement stmnt= conn.prepareStatement(query);
            stmnt.setString(1,loginCData.getEmail());
            stmnt.setString(2,loginCData.getPassword());
            ResultSet result= stmnt.executeQuery();
            System.out.println("Result:" + result);
            if (result.next()){
                int id = result.getInt("id");
                String fullName = result.getString("full_name");
                String address = result.getString("Address");
                String phoneNumber = result.getString("phone_number");
                String email = result.getString("email");
                String username = result.getString("username");
                String password = result.getString("password");
                CustomerData customer = new CustomerData(id, fullName, address, phoneNumber, email, username, password);
                return customer;
            } else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean isEmailRegistered(String email) {
        String query = "SELECT 1 FROM customer WHERE email = ?";
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
    
    public boolean updateProfilePicture(int customerId, byte[] profilePicture) {
        String query = "UPDATE customer SET profile_picture = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setBytes(1, profilePicture);
            stmnt.setInt(2, customerId);
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public byte[] getProfilePicture(int customerId) {
        String query = "SELECT profile_picture FROM customer WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, customerId);
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
    
    public CustomerData getCustomerById(int customerId) {
        String query = "SELECT * FROM customer WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, customerId);
            ResultSet result = stmnt.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String fullName = result.getString("full_name");
                String address = result.getString("address");
                String phoneNumber = result.getString("phone_number");
                String email = result.getString("email");
                String username = result.getString("username");
                String password = result.getString("password");
                byte[] profilePicture = result.getBytes("profile_picture");
                
                CustomerData customer = new CustomerData(id, fullName, address, phoneNumber, email, username, password);
                customer.setProfilePicture(profilePicture);
                return customer;
            }
        } catch (Exception e) {
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
        }
    
        public boolean updateCustomerProfile(int customerId, String fullName, String address, String phoneNumber, String email) {
        String query = "UPDATE customer SET full_name = ?, address = ?, phone_number = ?, email = ? WHERE id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, fullName);
            stmnt.setString(2, address);
            stmnt.setString(3, phoneNumber);
            stmnt.setString(4, email);
            stmnt.setInt(5, customerId);
            int result = stmnt.executeUpdate();
            
            System.out.println("Update customer profile result: " + result + " for customer ID: " + customerId);
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error updating customer profile: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}

