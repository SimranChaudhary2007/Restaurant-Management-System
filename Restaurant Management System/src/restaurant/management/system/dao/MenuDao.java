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
import restaurant.management.system.model.RestaurantData;

/**
 *
 * @author ACER
 */
public class MenuDao {
    MySqlConnection mySql = new MySqlConnection();

    // Create menu table if not exists
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS menu ("
            + "item_id INT AUTO_INCREMENT PRIMARY KEY,"
            + "item_name VARCHAR(100) NOT NULL UNIQUE,"
            + "price DECIMAL(10,2) NOT NULL,"
            + "category VARCHAR(50) NOT NULL,"
            + "description TEXT,"
            + "item_image MEDIUMBLOB"
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

    public List<MenuData> getAllMenuWithImages() {
    List<MenuData> menus = new ArrayList<>();
    String query = "SELECT item_id, item_name, item_image, item_category, price, item_description, rating, reviews FROM menu";
    Connection conn = null;
    
    try {
        conn = mySql.openConnection();
        PreparedStatement stmnt = conn.prepareStatement(query);
        ResultSet resultSet = stmnt.executeQuery();
        
        while (resultSet.next()) {
            MenuData menu = new MenuData();
            menu.setItemId(resultSet.getInt("item_id"));
            menu.setItemName(resultSet.getString("item_name"));
            menu.setItemImage(resultSet.getBytes("item_image"));
            menu.setItemCategory(resultSet.getString("item_category"));
            menu.setItemPrice(resultSet.getDouble("price"));
            
            // Handle potentially null fields
            String description = resultSet.getString("item_description");
            menu.setItemDescription(description != null ? description : "");
            
            String rating = resultSet.getString("rating");
            menu.setRating(rating != null ? rating : "0");
            
            String reviews = resultSet.getString("reviews");
            menu.setReviews(reviews != null ? reviews : "");
            
            menus.add(menu);
        }
    } catch (SQLException e) {
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Unexpected error: " + e.getMessage());
        e.printStackTrace();
    } finally {
        if (conn != null) {
            mySql.closeConnection(conn);
        }
    }
    
    return menus;
}

    // Add new menu item
    public boolean addMenuItem(MenuData item) {
        createTableIfNotExists();
        String sql = "INSERT INTO menu (name, type, price, description) VALUES (?, ?, ?, ?)";
        
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemCategory());
            pstmt.setDouble(3, item.getItemPrice());
            pstmt.setString(4, item.getItemDescription());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // Update menu item
    public boolean updateMenuItem(MenuData item) {
        createTableIfNotExists();
        String sql = "UPDATE menu SET item_name = ?, category = ?, price = ?, description = ? WHERE id = ?";
        
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemCategory());
            pstmt.setDouble(3, item.getItemPrice());
            pstmt.setString(4, item.getItemDescription());
            pstmt.setInt(5, item.getItemId());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // Delete menu item
    public boolean deleteMenuItem(int menuId) {
        createTableIfNotExists();
        String sql = "DELETE FROM menu WHERE id = ?";
        
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, menuId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}


