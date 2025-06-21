/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.view.CustomerMenuView;
import restaurant.management.system.view.CustomerMenuView.CartPopup;

/**
 *
 * @author labis
 */
public class CustomerMenuController {
    private CustomerMenuView customerMenuView;
    private MenuDao menuDao;
    
    public CustomerMenuController(CustomerMenuView view){
        this.customerMenuView = view;
        this.menuDao = new MenuDao();
        
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
            CustomerMenuView.CartPopup cartPopup = customerMenuView.new CartPopup(customerMenuView);
            cartPopup.setPlaceOrderAction(ev -> {
                JOptionPane.showMessageDialog(customerMenuView, "Order placed successfully!");
                cartPopup.dispose();
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
    
    // Validate cart has items
    if (cartPopup.getCartItems().isEmpty()) {
        JOptionPane.showMessageDialog(customerMenuView, 
            "Please add at least one item to your cart", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    return true;
}

private void showOrderConfirmation(String tableNumber, List<CustomerMenuView.CartPopup.CartItem> items) {
    StringBuilder summary = new StringBuilder();
    summary.append("Order for Table: ").append(tableNumber).append("\n\n");
    summary.append("Items:\n");
    
    for (CustomerMenuView.CartPopup.CartItem item : items) {
        summary.append("- ").append(item.getName())
               .append(" (Qty: ").append(item.getQuantity())
               .append(", Price: Rs.").append(item.getPrice()).append(")\n");
    }
    
    JOptionPane.showMessageDialog(customerMenuView, 
        summary.toString(), 
        "Order Confirmation", 
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
 