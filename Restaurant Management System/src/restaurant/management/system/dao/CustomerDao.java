/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.CustomerData;

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
}
