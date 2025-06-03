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
        String query = "INSERT INTO owner (full_name, address, phone_number, email, username, password) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, customer.getFullName());
            stmnt.setString(2, customer.getAddress());
            stmnt.setString(3, customer.getPhoneNumber());
            stmnt.setString(4, customer.getEmail());
            stmnt.setString(5, customer.getUsername());
            stmnt.setString(6, customer.getPassword()); 
            int result = stmnt.executeUpdate();
            return result > 0;
        }catch(Exception e){
            return false;
        }finally {
            mySql.closeConnection(conn);
        }
    }
    
    public CustomerData login(LoginRequest loginCData){
        String query = "SELECT * FROM owner WHERE email=? and password=?";
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
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}
