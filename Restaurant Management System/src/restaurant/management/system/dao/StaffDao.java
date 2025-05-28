/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.StaffData;

/**
 *
 * @author acer
 */
public class StaffDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean register(StaffData staff){
        String query = "INSERT INTO staff (full_name, restaurant_name, phone_number, email, username, password) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
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
}
