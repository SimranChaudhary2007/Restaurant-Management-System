/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import restaurant.management.system.database.MySqlConnection;
import java.sql.Connection;
import restaurant.management.system.model.NoticeData;
/**
 *
 * @author acer
 */
public class NoticeDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean addNotice(NoticeData notice) {
        Connection conn = mySql.openConnection();

        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS notices ( "
            + "notice_id INT PRIMARY KEY AUTO_INCREMENT,"
            + "owner_id INT NOT NULL," 
            + "title VARCHAR(255) NOT NULL,"
            + "content TEXT NOT NULL,"
            + "priority ENUM('HIGH', 'MEDIUM', 'LOW') DEFAULT 'MEDIUM',"
            + "is_active BOOLEAN DEFAULT TRUE,"
            + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
            + "created_by VARCHAR(100) NOT NULL,"
            + "FOREIGN KEY (owner_id) REFERENCES owner(id) ON DELETE CASCADE"
            +")";
         
        String sql = "INSERT INTO notices (owner_id, title, content, is_active) VALUES (?, ?, ?, ?)";
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmnt.setInt(1, notice.getOwnerId());
            stmnt.setString(2, notice.getTitle());
            stmnt.setString(3, notice.getContent());
            stmnt.setBoolean(4, notice.isActive());
            
            int rowsAffected = stmnt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the generated notice ID
                try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        notice.setNoticeId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all active notices for a specific owner
     */
    public List<NoticeData> getActiveNoticesByOwner(int ownerId) {
        List<NoticeData> notices = new ArrayList<>();
        String sql = "SELECT * FROM notices WHERE owner_id = ? AND is_active = TRUE ORDER BY created_at DESC";
        Connection conn = mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, ownerId);
            
            try (ResultSet rs = stmnt.executeQuery()) {
                while (rs.next()) {
                    NoticeData notice = new NoticeData();
                    notice.setNoticeId(rs.getInt("notice_id"));
                    notice.setOwnerId(rs.getInt("owner_id"));
                    notice.setTitle(rs.getString("title"));
                    notice.setContent(rs.getString("content"));
                    notice.setPriority(rs.getString("priority"));
                    notice.setActive(rs.getBoolean("is_active"));
                    notice.setCreatedAt(rs.getTimestamp("created_at"));
                    notice.setUpdatedAt(rs.getTimestamp("updated_at"));
                    notice.setCreatedBy(rs.getString("created_by"));
                    
                    notices.add(notice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return notices;
    }
    
    public List<NoticeData> getNoticesForStaff(int ownerId) {
        List<NoticeData> notices = new ArrayList<>();
        String sql = "SELECT * FROM notices WHERE owner_id = ? AND is_active = TRUE " +
                    "ORDER BY CASE priority " +
                    "WHEN 'HIGH' THEN 1 " +
                    "WHEN 'MEDIUM' THEN 2 " +
                    "WHEN 'LOW' THEN 3 " +
                    "END, created_at DESC";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            try (PreparedStatement stmnt = conn.prepareStatement(sql)) {
                stmnt.setInt(1, ownerId);
                
                try (ResultSet rs = stmnt.executeQuery()) {
                    while (rs.next()) {
                        NoticeData notice = new NoticeData();
                        notice.setNoticeId(rs.getInt("notice_id"));
                        notice.setOwnerId(rs.getInt("owner_id"));
                        notice.setTitle(rs.getString("title"));
                        notice.setContent(rs.getString("content"));
                        notice.setPriority(rs.getString("priority"));
                        notice.setActive(rs.getBoolean("is_active"));
                        notice.setCreatedAt(rs.getTimestamp("created_at"));
                        notice.setUpdatedAt(rs.getTimestamp("updated_at"));
                        notice.setCreatedBy(rs.getString("created_by"));
                        
                        notices.add(notice);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return notices;
    }
    /**
     * Get all notices for a specific owner (including inactive)
     */
    public List<NoticeData> getAllNoticesByOwner(int ownerId) {
        List<NoticeData> notices = new ArrayList<>();
        String sql = "SELECT * FROM notices WHERE owner_id = ?";
        Connection conn = mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, ownerId);
            
            try (ResultSet rs = stmnt.executeQuery()) {
                while (rs.next()) {
                    NoticeData notice = new NoticeData();
                    notice.setNoticeId(rs.getInt("notice_id"));
                    notice.setOwnerId(rs.getInt("owner_id"));
                    notice.setTitle(rs.getString("title"));
                    notice.setContent(rs.getString("content"));
                    notice.setPriority(rs.getString("priority"));
                    notice.setActive(rs.getBoolean("is_active"));
                    notice.setCreatedAt(rs.getTimestamp("created_at"));
                    notice.setUpdatedAt(rs.getTimestamp("updated_at"));
                    notice.setCreatedBy(rs.getString("created_by"));
                    
                    notices.add(notice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return notices;
    }
    
    /**
     * Update a notice
     */
    public boolean updateNotice(NoticeData notice) {
        String sql = "UPDATE notices SET title = ?, content = ?, priority = ?, updated_at = CURRENT_TIMESTAMP WHERE notice_id = ?";
        Connection conn = mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setString(1, notice.getTitle());
            stmnt.setString(2, notice.getContent());
            stmnt.setString(3, notice.getPriority());
            stmnt.setInt(4, notice.getNoticeId());
            
            return stmnt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deactivate a notice (soft delete)
     */
    public boolean deactivateNotice(int noticeId) {
        String sql = "UPDATE notices SET is_active = FALSE, updated_at = CURRENT_TIMESTAMP WHERE notice_id = ?";
        Connection conn = mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, noticeId);
            return stmnt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Activate a notice
     */
    public boolean activateNotice(int noticeId) {
        String sql = "UPDATE notices SET is_active = TRUE, updated_at = CURRENT_TIMESTAMP WHERE notice_id = ?";
        Connection conn = mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, noticeId);
            return stmnt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete a notice permanently
     */
    public boolean deleteNotice(int noticeId) {
        String sql = "DELETE FROM notices WHERE notice_id = ?";
        Connection conn = mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, noticeId);
            return stmnt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get a specific notice by ID
     */
    public NoticeData getNoticeById(int noticeId) {
        String sql = "SELECT * FROM notices WHERE notice_id = ?";
        Connection conn = mySql.openConnection();
        
        try (
            PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, noticeId);
            
            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    NoticeData notice = new NoticeData();
                    notice.setNoticeId(rs.getInt("notice_id"));
                    notice.setOwnerId(rs.getInt("owner_id"));
                    notice.setTitle(rs.getString("title"));
                    notice.setContent(rs.getString("content"));
                    notice.setPriority(rs.getString("priority"));
                    notice.setActive(rs.getBoolean("is_active"));
                    notice.setCreatedAt(rs.getTimestamp("created_at"));
                    notice.setUpdatedAt(rs.getTimestamp("updated_at"));
                    notice.setCreatedBy(rs.getString("created_by"));
                    
                    return notice;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
     public List<NoticeData> getAllNoticesDebug() {
        List<NoticeData> notices = new ArrayList<>();
        String sql = "SELECT * FROM notices ORDER BY created_at DESC";
        Connection conn = null;
        
        try {
            conn = mySql.openConnection();
            try (PreparedStatement stmnt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmnt.executeQuery()) {
                    while (rs.next()) {
                        NoticeData notice = new NoticeData();
                        notice.setNoticeId(rs.getInt("notice_id"));
                        notice.setOwnerId(rs.getInt("owner_id"));
                        notice.setTitle(rs.getString("title"));
                        notice.setContent(rs.getString("content"));
                        notice.setPriority(rs.getString("priority"));
                        notice.setActive(rs.getBoolean("is_active"));
                        notice.setCreatedAt(rs.getTimestamp("created_at"));
                        notice.setUpdatedAt(rs.getTimestamp("updated_at"));
                        notice.setCreatedBy(rs.getString("created_by"));
                        
                        notices.add(notice);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return notices;
    }
}