/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.CustomerHomeView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.RestaurantData;


public class CustomerHomeController {
    
    private CustomerHomeView customerHomeView;
    private List<RestaurantData> restaurants;
    
    public CustomerHomeController(CustomerHomeView customerHomeView) {
        this.customerHomeView = customerHomeView;
        loadRestaurants(); 
    }

    private void loadRestaurants() {
    try {
        OwnerDao ownerDao = new OwnerDao();
        restaurants = ownerDao.getAllRestaurantsWithImages();
        
 
        displayRestaurants();
        
    } catch (Exception e) {
    }
    }
    
    private void displayRestaurants() {
        for (RestaurantData restaurant : restaurants) {
        restaurant.getRestaurantName();
        restaurant.getOwnerName();
        restaurant.getAddress();
    }
    
    // THIS IS THE KEY LINE - uncomment and use it:
    customerHomeView.displayRestaurants(restaurants);
}
    
    public void open() {
        customerHomeView.setVisible(true);
    }
    
    public void refreshRestaurants() {
        loadRestaurants();
    }

    public void close() {
        customerHomeView.dispose();
    }
    
    class RestaurantCardClickListener implements MouseListener {
        private RestaurantData restaurant;
        
        public RestaurantCardClickListener(RestaurantData restaurant) {
            this.restaurant = restaurant;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            // Navigate to restaurant details or menu
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
