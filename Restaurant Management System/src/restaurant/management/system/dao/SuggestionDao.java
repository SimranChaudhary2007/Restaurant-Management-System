/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;


import restaurant.management.system.model.SuggestionData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import restaurant.management.system.database.MySqlConnection;

/**
 *
 * @author acer
 */
public class SuggestionDao {
    MySqlConnection mySql = new MySqlConnection();

    public boolean createSuggestionTableIfNotExists() {
       String createTableSQL = "CREATE TABLE IF NOT EXISTS suggestion ("
                + "suggestion_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "customer_id INT NOT NULL,"
                + "owner_id INT NOT NULL,"
                + "customer_name VARCHAR(100) NOT NULL,"
                + "customer_email VARCHAR(100),"
                + "restaurant_name VARCHAR(100) NOT NULL,"
                + "suggestion_text TEXT NOT NULL,"
                + "created_at DATETIME NOT NULL,"
                + "is_read BOOLEAN DEFAULT FALSE"
                + ")";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(createTableSQL);
            stmnt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Error creating suggestion table: " + e.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean addSuggestion(SuggestionData suggestion) {
        if (suggestion == null) {
            return false;
        }
        
        createSuggestionTableIfNotExists();
        String query = "INSERT INTO suggestion (customer_id, owner_id, customer_name, customer_email, restaurant_name, suggestion_text, created_at, is_read) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, suggestion.getCustomerId());
            stmnt.setInt(2, suggestion.getOwnerId());
            stmnt.setString(3, suggestion.getCustomerName());
            stmnt.setString(4, suggestion.getCustomerEmail());
            stmnt.setString(5, suggestion.getRestaurantName());
            stmnt.setString(6, suggestion.getSuggestionText());
            stmnt.setTimestamp(7, Timestamp.valueOf(suggestion.getCreatedAt()));
            stmnt.setBoolean(8, suggestion.isRead());
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.err.println("Error adding suggestion: " + e.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public List<SuggestionData> getAllSuggestions() {
        List<SuggestionData> suggestions = new ArrayList<>();
        String query = "SELECT * FROM suggestion ORDER BY created_at DESC";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                SuggestionData suggestion = new SuggestionData();
                suggestion.setSuggestionId(rs.getInt("suggestion_id"));
                suggestion.setCustomerId(rs.getInt("customer_id"));
                suggestion.setOwnerId(rs.getInt("owner_id"));
                suggestion.setCustomerName(rs.getString("customer_name"));
                suggestion.setCustomerEmail(rs.getString("customer_email"));
                suggestion.setRestaurantName(rs.getString("restaurant_name"));
                suggestion.setSuggestionText(rs.getString("suggestion_text"));
                suggestion.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                suggestion.setRead(rs.getBoolean("is_read"));
                suggestions.add(suggestion);
            }
        } catch (Exception e) {
            System.err.println("Error fetching suggestions: " + e.getMessage());
        } finally {
            mySql.closeConnection(conn);
        }
        return suggestions;
    }

    public List<SuggestionData> getSuggestionsByOwner(int ownerId) {
        List<SuggestionData> suggestions = new ArrayList<>();
        String query = "SELECT * FROM suggestion WHERE owner_id = ? ORDER BY created_at DESC";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, ownerId);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                SuggestionData suggestion = new SuggestionData();
                suggestion.setSuggestionId(rs.getInt("suggestion_id"));
                suggestion.setCustomerId(rs.getInt("customer_id"));
                suggestion.setOwnerId(rs.getInt("owner_id"));
                suggestion.setCustomerName(rs.getString("customer_name"));
                suggestion.setCustomerEmail(rs.getString("customer_email"));
                suggestion.setRestaurantName(rs.getString("restaurant_name"));
                suggestion.setSuggestionText(rs.getString("suggestion_text"));
                suggestion.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                suggestion.setRead(rs.getBoolean("is_read"));
                suggestions.add(suggestion);
            }
        } catch (Exception e) {
            System.err.println("Error fetching owner suggestions: " + e.getMessage());
        } finally {
            mySql.closeConnection(conn);
        }
        return suggestions;
    }

    public int getUnreadSuggestionsCount() {
        String query = "SELECT COUNT(*) FROM suggestion WHERE is_read = false";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error counting unread suggestions: " + e.getMessage());
        } finally {
            mySql.closeConnection(conn);
        }
        return 0;
    }

    public boolean markSuggestionsAsRead() {
        String query = "UPDATE suggestion SET is_read = true WHERE is_read = false";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            int result = stmnt.executeUpdate();
            return true; // Return true even if no rows were affected (all were already read)
        } catch (Exception e) {
            System.err.println("Error marking suggestions as read: " + e.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean deleteSuggestion(int suggestionId) {
        String query = "DELETE FROM suggestion WHERE suggestion_id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1, suggestionId);
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.err.println("Error deleting suggestion: " + e.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}