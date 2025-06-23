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
    private String customerEmail;
    private String restaurantName; // Added restaurantName field
    private String suggestionText;
    private LocalDateTime createdAt; // Using LocalDateTime for date/time
    private boolean isRead;

    /**
     * Default no-argument constructor.
     * Useful for frameworks or when setting properties via setters.
     */
    public SuggestionData() {
        // Default constructor
    }

    /**
     * Constructor for creating a new SuggestionData object when the
     * suggestion_id and created_at are not yet known (e.g., before saving to DB).
     * The database will typically auto-generate the ID and set the creation timestamp.
     *
     * @param customerId The ID of the customer making the suggestion.
     * @param suggestionText The actual text of the suggestion.
     * @param customerName The name of the customer.
     * @param customerEmail The email of the customer.
     * @param restaurantName The name of the restaurant the suggestion is for.
     * @param ownerId The ID of the owner/restaurant associated with this suggestion.
     * @param isRead The read status of the suggestion (true if read, false if unread).
     * @param createdAt The timestamp when the suggestion was created. Can be null if the database sets it.
     */
    public SuggestionData(int customerId, String suggestionText, String customerName,
                          String customerEmail, String restaurantName, int ownerId,
                          boolean isRead, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.suggestionText = suggestionText;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.restaurantName = restaurantName; // Initialize restaurantName
        this.ownerId = ownerId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    /**
     * Full constructor for retrieving SuggestionData objects from the database,
     * where all fields including the auto-generated ID and creation timestamp are available.
     *
     * @param suggestionId The unique ID of the suggestion.
     * @param customerId The ID of the customer making the suggestion.
     * @param ownerId The ID of the owner/restaurant associated with this suggestion.
     * @param customerName The name of the customer.
     * @param customerEmail The email of the customer.
     * @param restaurantName The name of the restaurant the suggestion is for.
     * @param suggestionText The actual text of the suggestion.
     * @param createdAt The timestamp when the suggestion was created.
     * @param isRead The read status of the suggestion (true if read, false if unread).
     */
    public SuggestionData(int suggestionId, int customerId, int ownerId, String customerName,
                          String customerEmail, String restaurantName, String suggestionText,
                          LocalDateTime createdAt, boolean isRead) {
        this.suggestionId = suggestionId;
        this.customerId = customerId;
        this.ownerId = ownerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.restaurantName = restaurantName;
        this.suggestionText = suggestionText;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    // --- Getters and Setters ---

    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public String getRestaurantName() { // Getter for restaurantName
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) { // Setter for restaurantName
        this.restaurantName = restaurantName;
    }

    public String getSuggestionText() {
        return suggestionText;
    }

    public void setSuggestionText(String suggestionText) {
        this.suggestionText = suggestionText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}