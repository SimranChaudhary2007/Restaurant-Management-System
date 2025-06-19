/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

/**
 *
 * @author labish
 */
public class MenuData {
    private int itemId;
    private byte[] itemImage;
    private String itemName;
    private String itemCategory;
    private double itemPrice;
    private String itemDescription;
    private String rating;
    private String reviews;

    // Default constructor (ADDED - This was missing)
    public MenuData() {
        // Default constructor
    }

    // Constructor without menuId (for new items)
    public MenuData(byte[] itemImage, String itemName, String itemCategory, double itemPrice, String itemDescription, String rating, String reviews) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.rating = rating;
        this.reviews = reviews;
    }

    // Constructor with all fields
    public MenuData(int itemId, byte[] itemImage, String itemName, String itemCategory, double itemPrice, String itemDescription, String rating, String reviews) {
        this.itemId = itemId;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.rating = rating;
        this.reviews = reviews;
    }

    // Getters
    public int getItemId() {
        return itemId;
    }

    public byte[] getItemImage() {
        return itemImage;
    }
    
    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }
    
    public String getRating() {
        return rating;
    }
    
    public String getReviews() {
        return reviews;
    }

    // Setters
    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    public void setReviews(String reviews) {
        this.reviews = reviews;
    }


    
    public static final String[] CATEGORIES = {
        "Hot Beverage", 
        "Cold Beverage", 
        "MoMo", 
        "Pizza", 
        "Burger", 
        "Ramen", 
        "Spaghetti"
    };

    // Helper method to validate categories
    public static boolean isValidCategory(String category) {
        for (String validCat : CATEGORIES) {
            if (validCat.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }
    
    // Improved toString
    @Override
    public String toString() {
        return String.format("%s (Rs. %.2f) - %s", 
            itemName, itemPrice, itemCategory);
    }
}
