/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import restaurant.management.system.view.CustomerMenuView;
import restaurant.management.system.view.CustomerMenuView.CartPopup;

/**
 *
 * @author labis
 */
public class CustomerMenuController {
    private CustomerMenuView customerMenuView;
    
    public CustomerMenuController(CustomerMenuView view){
        this.customerMenuView = view;
        
        setupCartButtonListener();
        setupNavigationListeners();
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
    
}
 