/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

import java.sql.Timestamp;

/**
 *
 * @author acer
 */
public class NoticeData {
    private int noticeId;
    private int ownerId;
    private String title;
    private String content;
    private String priority; // HIGH, MEDIUM, LOW
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;

    // Default constructor
    public NoticeData() {
    }

    // Constructor with essential fields
    public NoticeData(int ownerId, String title, String content, String priority, String createdBy) {
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.createdBy = createdBy;
        this.isActive = true;
    }

    // Constructor with all fields
    public NoticeData(int noticeId, int ownerId, String title, String content, 
                     String priority, boolean isActive, Timestamp createdAt, 
                     Timestamp updatedAt, String createdBy) {
        this.noticeId = noticeId;
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
