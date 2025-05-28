/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.database;

import java.sql.Connection;
import java.sql.*;

/**
 *
 * @author acer
 */
public class MySqlConnection implements DbConnection {

    @Override
    public Connection openConnection() {
        try {
            String username = "root";
            String password = "newpassword";
            String database = "restaurant_management_system";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ database,username,password);
            return conn;
           
        } catch(Exception e){
            return null;
            
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try{
            if (conn!=null && !conn.isClosed()){
            conn.close();
            }
        }catch(Exception e){
        }
        
    }
}
