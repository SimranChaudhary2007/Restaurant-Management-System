/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

/**
 *
 * @author labis
 */

import java.sql.Timestamp;

/**
 * Model class for Review data
 */
public class ReviewData {
    private int reviewId;
    private int itemId;
    private String customerName;
    private String customerEmail; // To identify customers for edit/delete
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public ReviewData() {}
    
    // Constructor without IDs (for new reviews)
    public ReviewData(int itemId, String customerName, String customerEmail, 
                 int rating, String comment) {
        this.itemId = itemId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.rating = rating;
        this.comment = comment;
    }
    
    // Full constructor
    public ReviewData(int reviewId, int itemId, String customerName, 
                 String customerEmail, int rating, String comment, 
                 Timestamp createdAt, Timestamp updatedAt) {
        this.reviewId = reviewId;
        this.itemId = itemId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
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
}
