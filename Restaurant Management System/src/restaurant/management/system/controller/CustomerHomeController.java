/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.CustomerHomeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.RestaurantData;

/**
 * Controller class for Customer Home View
 */
public class CustomerHomeController {
    
    private CustomerHomeView customerHomeView;
    private List<RestaurantData> restaurants;
    
    public CustomerHomeController(CustomerHomeView customerHomeView) {
        this.customerHomeView = customerHomeView;
        setupEventListeners(); // Make sure this is called
        loadRestaurants(); 
    }
    
    
    private void setupEventListeners() {
        // Add mouse listeners for navigation
        customerHomeView.getProfileLabel().addMouseListener(new ProfileClickListener());
        customerHomeView.getMenuLabel().addMouseListener(new MenuClickListener());
        customerHomeView.getOrderLabel().addMouseListener(new OrderClickListener());
        customerHomeView.getLogoutLabel().addMouseListener(new LogoutNav());
    }
    
    private void loadRestaurants() {
    try {
        OwnerDao ownerDao = new OwnerDao();
        restaurants = ownerDao.getAllRestaurantsWithImages();
        
        System.out.println("=== DEBUG INFO ===");
        System.out.println("Number of restaurants retrieved: " + restaurants.size());
        
        for (RestaurantData r : restaurants) {
            System.out.println("Restaurant: " + r.getRestaurantName());
            System.out.println("Has image: " + (r.getRestaurantImage() != null));
            if (r.getRestaurantImage() != null) {
                System.out.println("Image size: " + r.getRestaurantImage().length + " bytes");
            }
        }
        System.out.println("===================");
        
        // Display restaurants in the view
        displayRestaurants();
        
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Error loading restaurants: " + e.getMessage());
    }
    }
    
    private void displayRestaurants() {
        for (RestaurantData restaurant : restaurants) {
        System.out.println("Restaurant: " + restaurant.getRestaurantName());
        System.out.println("Owner: " + restaurant.getOwnerName());
        System.out.println("Address: " + restaurant.getAddress());
        System.out.println("Has Image: " + (restaurant.getRestaurantImage() != null));
        System.out.println("---");
    }
    
    // THIS IS THE KEY LINE - uncomment and use it:
    customerHomeView.displayRestaurants(restaurants);
}
    
    public void open() {
        customerHomeView.setVisible(true);
    }
    
    /**
     * Refresh the restaurant list (call this when you know a new restaurant image was added)
     */
    public void refreshRestaurants() {
        loadRestaurants();
    }
    /**
     * Close the customer home view
     */
    public void close() {
        customerHomeView.dispose();
    }
    
    // Event Listeners for Navigation
    class ProfileClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Navigate to customer profile
            // CustomerProfileController controller = new CustomerProfileController();
            // controller.open();
            System.out.println("Profile clicked");
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
        @Override
        public void mouseEntered(MouseEvent e) {
            customerHomeView.getProfileLabel().setCursor(new Cursor(Cursor.HAND_CURSOR));
            customerHomeView.getProfileLabel().setForeground(Color.RED);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            customerHomeView.getProfileLabel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            customerHomeView.getProfileLabel().setForeground(Color.BLACK);
        }
    }
    
    class MenuClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Navigate to menu view
            System.out.println("Menu clicked");
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
        @Override
        public void mouseEntered(MouseEvent e) {
            customerHomeView.getMenuLabel().setCursor(new Cursor(Cursor.HAND_CURSOR));
            customerHomeView.getMenuLabel().setForeground(Color.RED);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            customerHomeView.getMenuLabel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            customerHomeView.getMenuLabel().setForeground(Color.BLACK);
        }
    }
    
    class OrderClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Navigate to orders view
            System.out.println("Orders clicked");
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
        @Override
        public void mouseEntered(MouseEvent e) {
            customerHomeView.getOrderLabel().setCursor(new Cursor(Cursor.HAND_CURSOR));
            customerHomeView.getOrderLabel().setForeground(Color.RED);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            customerHomeView.getOrderLabel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            customerHomeView.getOrderLabel().setForeground(Color.BLACK);
        }
    }
    
    class LogoutNav implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int choice = JOptionPane.showConfirmDialog(customerHomeView,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                close();
                // Navigate back to login
                // LoginController loginController = new LoginController();
                // loginController.open();
                System.out.println("Logged out");
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
        @Override
        public void mouseEntered(MouseEvent e) {
            customerHomeView.getLogoutLabel().setCursor(new Cursor(Cursor.HAND_CURSOR));
            customerHomeView.getLogoutLabel().setForeground(Color.RED);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            customerHomeView.getLogoutLabel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            customerHomeView.getLogoutLabel().setForeground(Color.BLACK);
        }
    }
    
    class RestaurantCardClickListener implements MouseListener {
        private RestaurantData restaurant;
        
        public RestaurantCardClickListener(RestaurantData restaurant) {
            this.restaurant = restaurant;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            // Navigate to restaurant details or menu
            JOptionPane.showMessageDialog(customerHomeView,
                "Selected: " + restaurant.getRestaurantName() + "\n" +
                "Address: " + restaurant.getAddress() + "\n" +
                "Owner: " + restaurant.getOwnerName() + "\n" +
                "Phone: " + restaurant.getPhoneNumber(),
                "Restaurant Selected",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
        @Override
        public void mouseEntered(MouseEvent e) {
            ((Component) e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            ((Component) e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
