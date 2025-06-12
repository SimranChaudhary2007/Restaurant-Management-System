/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.CustomerHomeView;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import restaurant.management.system.UIElements.RoundedTextField;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.RestaurantData;


public class CustomerHomeController {
    
    private CustomerHomeView customerHomeView;
    private List<RestaurantData> allRestaurants;
    private List<RestaurantData> filteredRestaurants;
    private Timer searchTimer;
    private final String PLACEHOLDER_TEXT = "Search";
    
    public CustomerHomeController(CustomerHomeView customerHomeView) {
        this.customerHomeView = customerHomeView;
        this.allRestaurants = new ArrayList<>();
        this.filteredRestaurants = new ArrayList<>();
        
        setupSearchField();
        loadRestaurants();
        removeFocusFromSearchField();
    }
    
    //Search
    private void setupSearchField() {
        RoundedTextField searchField = customerHomeView.getSearchTextField();
        
        setPlaceholderAppearance(searchField);
        
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(PLACEHOLDER_TEXT)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                    searchField.setCaretPosition(0);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    setPlaceholderAppearance(searchField);
                }
            }
        });
        
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleSearchInput();
            }
        });
        
        searchTimer = new Timer(300, e -> performSearch());
        searchTimer.setRepeats(false);
    }
    
    private void setPlaceholderAppearance(RoundedTextField roundtextField) {
        roundtextField.setText(PLACEHOLDER_TEXT);
        roundtextField.setForeground(Color.GRAY);
        roundtextField.setCaretPosition(0);
    }
    
    private void removeFocusFromSearchField() {
        SwingUtilities.invokeLater(() -> {
            customerHomeView.getSearchTextField().setFocusable(false);
            customerHomeView.getSearchTextField().setFocusable(true);
            customerHomeView.requestFocus();
        });
    }
    
    private void handleSearchInput() {
        if (searchTimer.isRunning()) {
            searchTimer.stop();
        }
        searchTimer.start();
    }
    
    private void performSearch() {
        RoundedTextField searchField = customerHomeView.getSearchTextField();
        String searchText = searchField.getText().trim();
        
        if (searchText.equals(PLACEHOLDER_TEXT) || searchText.isEmpty()) {
            displayAllRestaurants();
            return;
        }
        
        filteredRestaurants = allRestaurants.stream()
                .filter(restaurant -> matchesSearchCriteria(restaurant, searchText))
                .collect(Collectors.toList());
        
        displayFilteredRestaurants();
    }
    
    private boolean matchesSearchCriteria(RestaurantData restaurant, String searchText) {
        String lowerSearchText = searchText.toLowerCase();
        
        // Search in restaurant name
        if (restaurant.getRestaurantName() != null && 
            restaurant.getRestaurantName().toLowerCase().contains(lowerSearchText)) {
            return true;
        }
        
        // Search in owner name
        if (restaurant.getOwnerName() != null && 
            restaurant.getOwnerName().toLowerCase().contains(lowerSearchText)) {
            return true;
        }
        
        // Search in address
        if (restaurant.getAddress() != null && 
            restaurant.getAddress().toLowerCase().contains(lowerSearchText)) {
            return true;
        }
                
        return false;
    }

    private void loadRestaurants() {
        try {
            OwnerDao ownerDao = new OwnerDao();
            allRestaurants = ownerDao.getAllRestaurantsWithImages();
            filteredRestaurants = new ArrayList<>(allRestaurants);
            displayAllRestaurants();
        } catch (Exception e) {
        }
    }
    
    private void displayAllRestaurants() {
        customerHomeView.displayRestaurants(allRestaurants);
    }
    
    private void displayFilteredRestaurants() {
        customerHomeView.displayRestaurants(filteredRestaurants);
    }
    
    public void clearSearch() {
        RoundedTextField searchField = customerHomeView.getSearchTextField();
        setPlaceholderAppearance(searchField);
        displayAllRestaurants();
    }
    
    public void open() {
        customerHomeView.setVisible(true);
        removeFocusFromSearchField();
    }
    
    public void refreshRestaurants() {
        loadRestaurants();
    }

    public void close() {
        if (searchTimer != null && searchTimer.isRunning()) {
            searchTimer.stop();
        }
        customerHomeView.dispose();
    }
    
    public int getSearchResultsCount() {
        return filteredRestaurants.size();
    }
    
    public String getCurrentSearchText() {
        RoundedTextField searchField = customerHomeView.getSearchTextField();
        String text = searchField.getText().trim();
        return text.equals(PLACEHOLDER_TEXT) ? "" : text;
    }
    
    class RestaurantCardClickListener implements MouseListener {
        private RestaurantData restaurant;
        
        public RestaurantCardClickListener(RestaurantData restaurant) {
            this.restaurant = restaurant;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            // Navigate to restaurant details or menu
            System.out.println("Navigating to restaurant: " + restaurant.getRestaurantName());
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
