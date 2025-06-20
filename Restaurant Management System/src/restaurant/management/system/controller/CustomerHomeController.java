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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import restaurant.management.system.UIElements.RoundedTextField;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.RestaurantData;
import restaurant.management.system.view.CustomerMenuView;
import restaurant.management.system.view.CustomerProfileView;
import restaurant.management.system.view.LoginView;


public class CustomerHomeController {
    private CustomerHomeView adminHomeView = new CustomerHomeView();
    private CustomerData currentCustomerData;
    private CustomerHomeView customerHomeView;
    private List<RestaurantData> allRestaurants;
    private List<RestaurantData> filteredRestaurants;
    private Timer searchTimer;
    private final String PLACEHOLDER_TEXT = "Search";
    
    public CustomerHomeController(CustomerHomeView customerHomeView, CustomerData customerData) {
        this.customerHomeView = customerHomeView;
        this.currentCustomerData = customerData;
        this.customerHomeView.profileNavigation(new ProfileNav(customerHomeView.getProfilelabel()));
        this.customerHomeView.menuNavigation(new MenuNav (customerHomeView.getMenulabel()));
        this.customerHomeView.orderNavigation(new OrderNav (customerHomeView.getOrderlabel()));
        this.customerHomeView.logoutNavigation(new LogoutNav(customerHomeView.getLogoutlabel()));
        this.allRestaurants = new ArrayList<>();
        this.filteredRestaurants = new ArrayList<>();
        
        setupSearchField();
        loadRestaurants();
        removeFocusFromSearchField();
    }
    
    class ProfileNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public ProfileNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            CustomerProfileView adminProfileView = new CustomerProfileView();
            CustomerProfileController customerProfileController= new CustomerProfileController(adminProfileView,  currentCustomerData);
            customerProfileController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            profilelabel.setForeground(Color.white);
            profilelabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            profilelabel.setForeground(Color.black);
            profilelabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    class MenuNav implements MouseListener{
        
        private JLabel menulabel;
        
        public MenuNav(JLabel label) {
            this.menulabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            menulabel.setForeground(Color.white);
            menulabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            menulabel.setForeground(Color.black);
            menulabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class OrderNav implements MouseListener{
        
        private JLabel orderlabel;
        
        public OrderNav(JLabel label) {
            this.orderlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            orderlabel.setForeground(Color.white);
            orderlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            orderlabel.setForeground(Color.black);
            orderlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class LogoutNav implements MouseListener{
        
        private JLabel logoutlabel;
        
        public LogoutNav(JLabel label) {
            this.logoutlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to logout?", "Logout Confirmation",
                JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
            JFrame adminHomeView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            adminHomeView.dispose();

            LoginView loginView = new LoginView();
            LoginController loginController= new LoginController(loginView);
            loginController.open();
            close();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            logoutlabel.setForeground(Color.WHITE);
            logoutlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            logoutlabel.setForeground(Color.BLACK);
            logoutlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
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
            CustomerMenuView customerMenuView = new CustomerMenuView();
            CustomerMenuController customerMenuContrller = new CustomerMenuController(customerMenuView);
            customerMenuContrller.open();
            close();
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
