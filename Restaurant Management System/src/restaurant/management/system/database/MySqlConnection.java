/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.database;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author acer
 */
public class MySqlConnection implements DbConnection {

   


    @Override
    public void closeConnection(Connection conn) {
    }

    @Override
    public Connection openConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}