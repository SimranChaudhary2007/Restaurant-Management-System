/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import restaurant.management.system.model.MenuData;
import restaurant.management.system.database.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class MenuDao {
    MySqlConnection mySql = new MySqlConnection();

    // Create menu table if not exists
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS menu ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "name VARCHAR(100) NOT NULL,"
            + "type VARCHAR(50) NOT NULL,"
            + "price DECIMAL(10,2) NOT NULL,"
            + "description TEXT"
            + ")";
        
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(createTableSQL);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // Get all menu items
    public List<MenuData> getAllMenuItems() {
        createTableIfNotExists();
        List<MenuData> menuList = new ArrayList<>();
        String sql = "SELECT * FROM menu";
        
        Connection conn = mySql.openConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                MenuData item = new MenuData(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price"),
                    rs.getString("description")
                );
                menuList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return menuList;
    }

    
}


