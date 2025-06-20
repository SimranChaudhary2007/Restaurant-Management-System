/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

import java.time.LocalDateTime;

/**
 *
 * @author acer
 */
public class SuggestionData {
    private int suggestionId;
    private int customerId;
    private int ownerId;
    private String customerName;
    private String restaurantName;
    private String suggestionText;
    private LocalDateTime createdAt;
    private boolean isRead;
    
    public SuggestionData() {}
    
    public SuggestionData(int customerId, int ownerId, String customerName, 
                         String restaurantName, String suggestionText) {
        this.customerId = customerId;
        this.ownerId = ownerId;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.suggestionText = suggestionText;
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
    
    //setter
    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public void setSuggestionText(String suggestionText) {
        this.suggestionText = suggestionText;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
    
    //getter
    public int getSuggestionId() {
        return suggestionId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getOwnerId() {
        return ownerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getRestaurantName() {
        return restaurantName;
    }
    public String getSuggestionText() {
        return suggestionText;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public boolean isRead() {
        return isRead;
    }
}
