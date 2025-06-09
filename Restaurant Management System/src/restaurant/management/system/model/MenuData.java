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

   
}