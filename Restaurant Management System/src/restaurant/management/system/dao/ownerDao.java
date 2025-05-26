/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.UserOwnerData;


/**
 *
 * @author acer
 */
public class ownerDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean register(UserOwnerData owner){
        String query = "INSERT INTO owner (full_name, restaurant_name, phone_number, address, email, username, password) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, owner.getFullName());
            stmnt.setString(2, owner.getRestaurantName());
            stmnt.setString(3, owner.getPhoneNumber());
            stmnt.setString(4, owner.getAddress());
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
}
