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
            + "item_image LONGBLOB,"
            + "rating VARCHAR(10),"
            + "reviews VARCHAR(500),"
            + "owner_id INT,"
            + "FOREIGN KEY (owner_id) REFERENCES owner(id)"
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

    public boolean addMenuItem(MenuData item) {
        createTableIfNotExists();

        // Validate that the owner exists before trying to insert
        if (!ownerExists(item.getOwnerId())) {
            System.err.println("ERROR: Owner with ID " + item.getOwnerId() + " does not exist in the database");
            return false;
        }

        System.out.println("DEBUG: Using owner_id: " + item.getOwnerId());

        String sql = "INSERT INTO menu (owner_id, item_name, item_category, price, item_description, item_image) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getOwnerId());
            pstmt.setString(2, item.getItemName());
            pstmt.setString(3, item.getItemCategory());
            pstmt.setDouble(4, item.getItemPrice());
            pstmt.setString(5, item.getItemDescription());
            pstmt.setBytes(6, item.getItemImage());

            int result = pstmt.executeUpdate();
            System.out.println("DEBUG: Menu item added successfully with owner_id: " + item.getOwnerId());
            return result > 0;
        } catch (SQLException e) {
            System.err.println("DEBUG: SQLException: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // Improved ownerExists method with better error handling
    private boolean ownerExists(int ownerId) {
        String sql = "SELECT COUNT(*) FROM owner WHERE id = ?";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("DEBUG: Owner ID " + ownerId + " exists: " + (count > 0));
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if owner exists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Add this method to help debug - get all existing owner IDs
    public void printAllOwnerIds() {
        String sql = "SELECT id, name FROM owner";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            System.out.println("DEBUG: Existing owners in database:");

            while (rs.next()) {
                System.out.println("Owner ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching owner IDs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Update menu item (removed rating and reviews)
    public boolean updateMenuItem(MenuData item) {
        String sql = "UPDATE menu SET item_name = ?, item_category = ?, price = ?, "
                   + "item_description = ?, item_image = ? WHERE item_id = ? AND owner_id = ?";
        
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getItemCategory());
            pstmt.setDouble(3, item.getItemPrice());
            pstmt.setString(4, item.getItemDescription());
            pstmt.setBytes(5, item.getItemImage());
            pstmt.setInt(6, item.getItemId());
            pstmt.setInt(7, item.getOwnerId());
            
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
    public boolean deleteMenuItem(int menuId, int ownerId) {
        String sql = "DELETE FROM menu WHERE item_id = ? AND owner_id = ?";
        
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, menuId);
            pstmt.setInt(2, ownerId);
            
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
    
    public List<MenuData> getMenuByOwnerAndCategory(int ownerId, String category) {
        List<MenuData> menus = new ArrayList<>();
        String query = "SELECT * FROM menu WHERE owner_id = ? AND item_category = ?";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, ownerId);
            stmt.setString(2, category);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                MenuData menu = new MenuData(
                    rs.getInt("item_id"),
                    rs.getBytes("item_image"),
                    rs.getString("item_name"),
                    rs.getString("item_category"),
                    rs.getDouble("price"),
                    rs.getString("item_description"),
                    0.0,  // Default rating
                    "0"   // Default reviews
                );
                menu.setOwnerId(rs.getInt("owner_id"));
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }
    
    public List<MenuData> getMenuByOwner(int ownerId) {
        List<MenuData> menus = new ArrayList<>();
        String query = "SELECT * FROM menu WHERE owner_id = ?";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, ownerId);
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
                menu.setOwnerId(rs.getInt("owner_id"));
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }
}


