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
 * @author labish
 */
public class MenuDao {
    MySqlConnection mySql = new MySqlConnection();
    private ReviewDao reviewDao = new ReviewDao();

    // Create menu table if not exists (removed rating and reviews columns)
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS menu ("
            + "item_id INT AUTO_INCREMENT PRIMARY KEY,"
            + "item_name VARCHAR(100) NOT NULL,"
            + "item_category VARCHAR(50) NOT NULL,"
            + "price DECIMAL(10,2) NOT NULL,"
            + "item_description TEXT,"
            + "item_image LONGBLOB"
            + ")";
        
        try (Connection conn = mySql.openConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MenuData> getAllMenuWithImages() {
        List<MenuData> menus = new ArrayList<>();
        String query = "SELECT item_id, item_name, item_image, item_category, price, item_description FROM menu";
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
                
                // Calculate rating and review count from reviews table
                double avgRating = reviewDao.getAverageRating(menu.getItemId());
                int reviewCount = reviewDao.getReviewCount(menu.getItemId());
                
                menu.setRating(avgRating);
                menu.setReviews(reviewCount + " reviews");
                
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

    // Add new menu item (removed rating and reviews)
    public boolean addMenuItem(MenuData item) {
        createTableIfNotExists();
        String sql = "INSERT INTO menu (item_name, item_category, price, item_description, item_image) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemCategory());
            pstmt.setDouble(3, item.getItemPrice());
            pstmt.setString(4, item.getItemDescription());
            pstmt.setBytes(5, item.getItemImage());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // Update menu item (removed rating and reviews)
    public boolean updateMenuItem(MenuData item) {
        createTableIfNotExists();
        String sql = "UPDATE menu SET item_name = ?, item_category = ?, price = ?, item_description = ?, item_image = ? WHERE item_id = ?";
        
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemCategory());
            pstmt.setDouble(3, item.getItemPrice());
            pstmt.setString(4, item.getItemDescription());
            pstmt.setBytes(5, item.getItemImage());
            pstmt.setInt(6, item.getItemId());
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
        String sql = "DELETE FROM menu WHERE item_id = ?";
        
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
    
    public List<MenuData> getMenuByCategory(String category) {
        List<MenuData> menus = new ArrayList<>();
        String query = "SELECT * FROM menu WHERE item_category = ?";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                
                // Calculate rating and review count from reviews table
                double avgRating = reviewDao.getAverageRating(itemId);
                int reviewCount = reviewDao.getReviewCount(itemId);
                
                MenuData menu = new MenuData(
                    itemId,
                    rs.getBytes("item_image"),
                    rs.getString("item_name"),
                    rs.getString("item_category"),
                    rs.getDouble("price"),
                    rs.getString("item_description"),
                    avgRating,
                    reviewCount + " reviews"
                );
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }
}


