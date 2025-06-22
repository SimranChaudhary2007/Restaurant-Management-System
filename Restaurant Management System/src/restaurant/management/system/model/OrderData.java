/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

import java.util.List;

/**
 *
 * @author labis
 */
public class OrderData {
    private String orderId;
    private int tableNumber;
    private String orderDate;
    private String orderTime;
    private double totalAmount;
    private String orderStatus;
    private List<OrderItem> orderItems;
    private int customerId;
    
    // Constructors
    public OrderData() {
    }
    
    public OrderData(String orderId, int tableNumber, String orderDate, String orderTime, 
                    double totalAmount, String orderStatus) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }
    
    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public int getTableNumber() {
        return tableNumber;
    }
    
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public String getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getOrderTime() {
        return orderTime;
    }
    
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    // Inner class for order items
    public static class OrderItem {
        private int orderItemId;
        private String orderId;
        private int menuId;
        private String itemName;
        private int quantity;
        private double price;
        private double subtotal;
        
        // Constructors
        public OrderItem() {
        }
        
        public OrderItem(int orderItemId, String orderId, int menuId, String itemName, 
                        int quantity, double price, double subtotal) {
            this.orderItemId = orderItemId;
            this.orderId = orderId;
            this.menuId = menuId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
            this.subtotal = subtotal;
        }
        
        // Getters and Setters
        public int getOrderItemId() {
            return orderItemId;
        }
        
        public void setOrderItemId(int orderItemId) {
            this.orderItemId = orderItemId;
        }
        
        public String getOrderId() {
            return orderId;
        }
        
        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
        
        public int getMenuId() {
            return menuId;
        }
        
        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }
        
        public String getItemName() {
            return itemName;
        }
        
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public double getPrice() {
            return price;
        }
        
        public void setPrice(double price) {
            this.price = price;
        }
        
        public double getSubtotal() {
            return subtotal;
        }
        
        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
    }
}