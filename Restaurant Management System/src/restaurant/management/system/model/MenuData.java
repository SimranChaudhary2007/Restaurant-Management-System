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
        private double rating;
        private String reviews;
           
    public MenuData() {
        this.itemId = 0;
        this.itemName = "";
        this.itemCategory = "";
        this.itemDescription = "";
        this.itemPrice = 0.0;
        this.itemImage = null;
        this.rating = 0.0;
        this.reviews = "";
    }
    
    public MenuData(int itemId, String itemName, String itemCategory, String itemDescription, double itemPrice, byte[] itemImage, double rating, String reviews) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.rating = rating;
        this.reviews = reviews;
    }
    
    public MenuData(int itemId,String itemName, String  itemCategory, String itemDescription, double price, byte[] image) {
        this.itemId= itemId;
        this.itemImage= itemImage;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemPrice= itemPrice;
        this.itemDescription=  itemDescription;
    }

    // Constructor without menuId (for new items)
    public MenuData(byte[] itemImage, String itemName, String itemCategory, double itemPrice, String itemDescription, double rating, String reviews) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.rating = rating;
        this.reviews = reviews;
    }

    // Constructor with all fields
    public MenuData(int itemId, byte[] itemImage, String itemName, String itemCategory, double itemPrice, String itemDescription, double rating, String reviews) {
        this.itemId = itemId;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.rating = rating;
        this.reviews = reviews;
    }
    
    public MenuData(double itemPrice){
        this.itemPrice = itemPrice;
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
    
    public double getRating() {
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
    
    public void setRating(double rating) {
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
    
    public boolean isValid() {
        return isValidName() && isValidCategory() && isValidPrice() && isValidId();
    }

    private boolean isValidName() {
        return itemName != null && !itemName.trim().isEmpty();
    }

    private boolean isValidCategory() {
        return itemCategory != null && !itemCategory.trim().isEmpty();
    }

    private boolean isValidPrice() {
        return itemPrice > 0;
    }

    private boolean isValidId() {
        return itemId >= 0; // 0 means new item, positive means existing
    }
    
    // Improved toString
    @Override
    public String toString() {
        return String.format("%s (Rs. %.2f) - %s", 
            itemName, itemPrice, itemCategory);
    }
}
