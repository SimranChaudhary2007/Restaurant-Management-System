/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.view.CustomerMenuView;
import restaurant.management.system.view.CustomerMenuView.CartPopup;

/**
 *
 * @author labis
 */
public class CustomerMenuController {
    private CustomerMenuView customerMenuView;
    private MenuDao menuDao;
    private OrderDao orderDao;
    
    public CustomerMenuController(CustomerMenuView view){
        this.customerMenuView = view;
        this.menuDao = new MenuDao();
        this.orderDao = new OrderDao();
        
        setupCartButtonListener();
        setupNavigationListeners();
        loadAndDisplayMenuItems();
    }
    
    public void open(){
        this.customerMenuView.setVisible(true);
    }
    
    public void close(){
        this.customerMenuView.dispose();
    }
    
    private void setupCartButtonListener() {
        customerMenuView.getCartButton().addActionListener(e -> {
            // Get current cart items from the view
            Map<MenuData, Integer> cartItems = customerMenuView.getCartItems();
            
            CustomerMenuView.CartPopup cartPopup = customerMenuView.new CartPopup(customerMenuView);
            
            // Populate cart popup with current items
            cartPopup.populateCartItems(cartItems);
            
            // Set up place order action
            cartPopup.setPlaceOrderAction(ev -> {
                if (validateCart(cartPopup)) {
                    try {
                        // Create and save order
                        String tableNumber = cartPopup.getTableNumber();
                        Map<MenuData, Integer> currentCartItems = cartPopup.getCartItems();
                        
                        if (currentCartItems.isEmpty()) {
                            JOptionPane.showMessageDialog(customerMenuView, 
                                "Your cart is empty. Please add items before placing order.", 
                                "Empty Cart", 
                                JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                        // Generate order ID
                        String orderId = generateOrderId();
                        
                        // Calculate total amount
                        double totalAmount = calculateTotalAmount(currentCartItems);
                        
                        // Create order data
                        OrderData order = new OrderData();
                        order.setOrderId(orderId);
                        order.setTableNumber(Integer.parseInt(tableNumber));
                        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        order.setOrderTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                        order.setTotalAmount(totalAmount);
                        order.setOrderStatus("Pending");
                        
                        // Create order items list
                        List<OrderData.OrderItem> orderItems = new ArrayList<>();
                        for (Map.Entry<MenuData, Integer> entry : currentCartItems.entrySet()) {
                            MenuData menuItem = entry.getKey();
                            Integer quantity = entry.getValue();
                            
                            OrderData.OrderItem orderItem = new OrderData.OrderItem();
                            orderItem.setMenuId(menuItem.getItemId());
                            orderItem.setItemName(menuItem.getItemName());
                            orderItem.setQuantity(quantity);
                            orderItem.setPrice(menuItem.getItemPrice());
                            orderItem.setSubtotal(menuItem.getItemPrice() * quantity);
                            
                            orderItems.add(orderItem);
                        }
                        order.setOrderItems(orderItems);
                        
                        // Save order to database
                        boolean orderSaved = orderDao.saveOrder(order);
                        
                        if (orderSaved) {
                            // Show success message with order details
                            showOrderConfirmation(orderId, tableNumber, currentCartItems, totalAmount);
                            
                            // Clear the cart
                            customerMenuView.clearCart();
                            
                            // Close the cart popup
                            cartPopup.dispose();
                        } else {
                            JOptionPane.showMessageDialog(customerMenuView, 
                                "Failed to save order. Please try again.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(customerMenuView, 
                            "An error occurred while placing the order: " + ex.getMessage(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
            cartPopup.setVisible(true);
        });
    }
    
    private boolean validateCart(CustomerMenuView.CartPopup cartPopup) {
        // Validate table number
        String tableNumber = cartPopup.getTableNumber();
        if (tableNumber.isEmpty()) {
            JOptionPane.showMessageDialog(customerMenuView, 
                "Please enter a table number", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!tableNumber.matches("\\d+")) {
            JOptionPane.showMessageDialog(customerMenuView, 
                "Table number must be numeric", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Additional validation for table number range (optional)
        try {
            int tableNum = Integer.parseInt(tableNumber);
            if (tableNum <= 0 || tableNum > 100) { // Assuming max 100 tables
                JOptionPane.showMessageDialog(customerMenuView, 
                    "Please enter a valid table number (1-100)", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(customerMenuView, 
                "Invalid table number format", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private String generateOrderId() {
        // Generate unique order ID with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ORD" + timestamp + String.format("%03d", (int)(Math.random() * 1000));
    }
    
    private double calculateTotalAmount(Map<MenuData, Integer> cartItems) {
        double total = 0.0;
        for (Map.Entry<MenuData, Integer> entry : cartItems.entrySet()) {
            MenuData menuItem = entry.getKey();
            Integer quantity = entry.getValue();
            total += menuItem.getItemPrice() * quantity;
        }
        return total;
    }
    
    private void showOrderConfirmation(String orderId, String tableNumber, 
                                     Map<MenuData, Integer> items, double totalAmount) {
        StringBuilder summary = new StringBuilder();
        summary.append("Order Placed Successfully!\n\n");
        summary.append("Order ID: ").append(orderId).append("\n");
        summary.append("Table Number: ").append(tableNumber).append("\n\n");
        summary.append("Items Ordered:\n");
        summary.append("----------------------------------------\n");
        
        for (Map.Entry<MenuData, Integer> entry : items.entrySet()) {
            MenuData menuItem = entry.getKey();
            Integer quantity = entry.getValue();
            double itemTotal = menuItem.getItemPrice() * quantity;
            
            summary.append(String.format("%-20s x%d @ Rs.%.2f = Rs.%.2f\n", 
                menuItem.getItemName(), quantity, menuItem.getItemPrice(), itemTotal));
        }
        
        summary.append("----------------------------------------\n");
        summary.append(String.format("Total Amount: Rs.%.2f\n", totalAmount));
        summary.append("\nYour order is being prepared!");
        
        JOptionPane.showMessageDialog(customerMenuView, 
            summary.toString(), 
            "Order Placed Successfully", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void setupNavigationListeners() {
        // Get the tabbed pane from the view
        JTabbedPane tabbedPane = customerMenuView.getMenuTabbedPane();
        
        // Setup listeners for each icon
        addNavigationListener(customerMenuView.getCoffeeIcon(), tabbedPane, 0); // Hot Beverage
        addNavigationListener(customerMenuView.getDrinksIcon(), tabbedPane, 1); // Cold Beverage
        addNavigationListener(customerMenuView.getMomoIcon(), tabbedPane, 2);   // MoMo
        addNavigationListener(customerMenuView.getPizzaIcon(), tabbedPane, 3);  // Pizza
        addNavigationListener(customerMenuView.getBurgerIcon(), tabbedPane, 4); // Burger
        addNavigationListener(customerMenuView.getRamenIcon(), tabbedPane, 5);  // Ramen
        addNavigationListener(customerMenuView.getSpaghettiIcon(), tabbedPane, 6); // Spaghetti
    }

    private void addNavigationListener(JLabel label, JTabbedPane tabbedPane, int tabIndex) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabbedPane.setSelectedIndex(tabIndex);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Optional: Add visual feedback on hover
                label.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                // Optional: Remove visual feedback
                label.setBorder(BorderFactory.createEmptyBorder());
            }
        });
    }

    private void loadAndDisplayMenuItems() {
        List<MenuData> menuItems = menuDao.getAllMenuWithImages();
        customerMenuView.displayMenu(menuItems);
    }
}