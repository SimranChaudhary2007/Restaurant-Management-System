/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

/**
 *
 * @author ACER
 */
public class MenuData {
    private int menuId;
    private String itemName;
    private String itemType;
    private double itemPrice;
    private String itemDescription;

    // Constructor without menuId (for new items)
    public MenuData(String itemName, String itemType, double itemPrice, String itemDescription) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    // Constructor with all fields
    public MenuData(int menuId, String itemName, String itemType, double itemPrice, String itemDescription) {
        this.menuId = menuId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    // Getters
    public int getMenuId() {
        return menuId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    // Setters
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public String toString() {
        return itemName + " (" + itemType + ")";
    }
}