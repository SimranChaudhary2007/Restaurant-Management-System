/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

/**
 *
 * @author acer
 */
public class RestaurantData {
    private int ownerId;            // Foreign key to owner table
    private String restaurantName;
    private String address;
    private byte[] restaurantImage;
    private String phoneNumber;
    private String email;           // Owner's email (from join)
    private String ownerName;       // Owner's full name (from join)
    
    // Default constructor
    public RestaurantData() {
    }
    
    // Constructor with basic fields
    public RestaurantData(int ownerId, String restaurantName, String address, String phoneNumber) {
        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    // Constructor with all fields
    public RestaurantData(int ownerId, String restaurantName, String address, 
                         byte[] restaurantImage, String phoneNumber, String email, String ownerName) {
        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.restaurantImage = restaurantImage;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownerName = ownerName;
    }
    
    //Setters
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public void setRestaurantImage(byte[] restaurantImage) {
        this.restaurantImage = restaurantImage;
    }
     public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    //getter
    public int getOwnerId() {
        return ownerId;
    }
    public String getAddress() {
        return address;
    }
    public byte[] getRestaurantImage() {
        return restaurantImage;
    }
    public String getRestaurantName() {
        return restaurantName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getOwnerName() {
        return ownerName;
    }
}