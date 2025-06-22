/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

/**
 *
 * @author labis
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.ReviewData;

/**
 * Data Access Object for Review operations
 */
public class ReviewDao {
    private MySqlConnection mySql = new MySqlConnection();
    
    // Create reviews table if not exists
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS reviews ("
            + "review_id INT AUTO_INCREMENT PRIMARY KEY,"
            + "item_id INT NOT NULL,"
            + "customer_name VARCHAR(100) NOT NULL,"
            + "customer_email VARCHAR(150) NOT NULL,"
            + "rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),"
            + "comment TEXT NOT NULL,"
            + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
            + "FOREIGN KEY (item_id) REFERENCES menu(item_id) ON DELETE CASCADE,"
            + "UNIQUE KEY unique_customer_item (item_id, customer_email)"
            + ")";
        
        try (Connection conn = mySql.openConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Add new review
    public boolean addReview(ReviewData review) {
    // Validate input
    if (review.getCustomerName() == null || review.getCustomerName().trim().isEmpty()) {
        System.err.println("Customer name cannot be null or empty");
        return false;
    }
    if (review.getCustomerEmail() == null || review.getCustomerEmail().trim().isEmpty()) {
        System.err.println("Customer email cannot be null or empty");
        return false;
    }
    if (review.getRating() < 1 || review.getRating() > 5) {
        System.err.println("Rating must be between 1 and 5");
        return false;
    }
    if (review.getComment() == null || review.getComment().trim().isEmpty()) {
        System.err.println("Comment cannot be null or empty");
        return false;
    }
    
    createTableIfNotExists();
    String sql = "INSERT INTO reviews (item_id, customer_name, customer_email, rating, comment) VALUES (?, ?, ?, ?, ?)";
    
    Connection conn = null;
    try {
        conn = mySql.openConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, review.getItemId());
        pstmt.setString(2, review.getCustomerName());
        pstmt.setString(3, review.getCustomerEmail());
        pstmt.setInt(4, review.getRating());
        pstmt.setString(5, review.getComment());
        
        int result = pstmt.executeUpdate();
        return result > 0;
    } catch (SQLException e) {
        if (e.getErrorCode() == 1062) { // Duplicate entry error
            System.err.println("Customer has already reviewed this item");
            return false;
        }
        e.printStackTrace();
        return false;
    } finally {
        if (conn != null) {
            mySql.closeConnection(conn);
        }
    }
}
    
    // Update existing review
    public boolean updateReview(ReviewData review) {
        String sql = "UPDATE reviews SET customer_name = ?, rating = ?, comment = ? WHERE review_id = ? AND customer_email = ?";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, review.getCustomerName());
            pstmt.setInt(2, review.getRating());
            pstmt.setString(3, review.getComment());
            pstmt.setInt(4, review.getReviewId());
            pstmt.setString(5, review.getCustomerEmail());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
    }
    
    // Delete review
    public boolean deleteReview(int reviewId, String customerEmail) {
        String sql = "DELETE FROM reviews WHERE review_id = ? AND customer_email = ?";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            pstmt.setString(2, customerEmail);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
    }
    
    // Get all reviews for a specific menu item
    public List<ReviewData> getReviewsByItemId(int itemId) {
        List<ReviewData> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE item_id = ? ORDER BY created_at DESC";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ReviewData review = new ReviewData(
                    rs.getInt("review_id"),
                    rs.getInt("item_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
        
        return reviews;
    }
    
    // Get specific review by customer email and item id
    public ReviewData getCustomerReview(int itemId, String customerEmail) {
        String sql = "SELECT * FROM reviews WHERE item_id = ? AND customer_email = ?";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            pstmt.setString(2, customerEmail);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new ReviewData(
                    rs.getInt("review_id"),
                    rs.getInt("item_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_email"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
        
        return null;
    }
    
    // Get average rating for a menu item
    public double getAverageRating(int itemId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM reviews WHERE item_id = ?";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
        
        return 0.0;
    }
    
    // Get total review count for a menu item
    public int getReviewCount(int itemId) {
        String sql = "SELECT COUNT(*) as review_count FROM reviews WHERE item_id = ?";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("review_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
        
        return 0;
    }
    
    // Check if customer has already reviewed this item
    public boolean hasCustomerReviewed(int itemId, String customerEmail) {
        return getCustomerReview(itemId, customerEmail) != null;
    }
}
