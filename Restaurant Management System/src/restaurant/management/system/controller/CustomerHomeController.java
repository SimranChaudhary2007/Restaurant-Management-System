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
import restaurant.management.system.UIElements.CustomerMenuCardPanel;
import restaurant.management.system.UIElements.RoundedTextField;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.model.RestaurantData;
import restaurant.management.system.view.CustomerMenuView;
import restaurant.management.system.view.CustomerOrderView;
import restaurant.management.system.view.CustomerProfileView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.CustomerBillView;


public class CustomerHomeController {
    private CustomerHomeView adminHomeView = new CustomerHomeView();
    private int currentCustomerId;
    private CustomerData currentCustomerData;
    private CustomerHomeView customerHomeView;
    private List<RestaurantData> allRestaurants;
    private List<RestaurantData> filteredRestaurants;
    private Timer searchTimer;
    private final String PLACEHOLDER_TEXT = "Search";
    private CustomerData customer;
    private MenuDao menuDao;
    
    public CustomerHomeController(CustomerHomeView customerHomeView, CustomerData customerData) {
        this.customerHomeView = customerHomeView;
        this.currentCustomerData = customerData;
        this.currentCustomerId = customerData != null ? customerData.getId() : -1;
        this.customerHomeView.profileNavigation(new ProfileNav(customerHomeView.getProfilelabel()));
        this.customerHomeView.orderNavigation(new OrderNav (customerHomeView.getOrderlabel()));
        this.customerHomeView.billsNavigation(new BillsNav (customerHomeView.getBillslabel()));
        this.customerHomeView.logoutNavigation(new LogoutNav(customerHomeView.getLogoutlabel()));
        this.allRestaurants = new ArrayList<>();
        this.filteredRestaurants = new ArrayList<>();
        
        setupSearchField();
        loadRestaurants();
        removeFocusFromSearchField();
    }
    
     private void initializeMenuCards() {
        menuDao = new MenuDao();
        List<MenuData> menuItems = menuDao.getAllMenuWithImages();
        
        for (MenuData item : menuItems) {
            CustomerMenuCardPanel card = new CustomerMenuCardPanel(item);
            card.setCurrentCustomer(currentCustomerData); // Pass the whole customer object
            // Add card to view
        }
    }
    
    class ProfileNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public ProfileNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            CustomerProfileView customerProfileView = new CustomerProfileView();
            CustomerProfileController customerProfileController= new CustomerProfileController(customerProfileView,  currentCustomerData);
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
    
    class OrderNav implements MouseListener{
        
        private JLabel orderlabel;
        
        public OrderNav(JLabel label) {
            this.orderlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            CustomerOrderView customerOwnerView = new CustomerOrderView();
            CustomerOrderController customerOrderController = new CustomerOrderController(customerOwnerView, currentCustomerId);
            customerOrderController.open();
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
            orderlabel.setForeground(Color.white);
            orderlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            orderlabel.setForeground(Color.black);
            orderlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class BillsNav implements MouseListener{
        
        private JLabel billlabel;
        
        public BillsNav(JLabel label) {
            this.billlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            CustomerBillView customerBillView = new CustomerBillView();
            CustomerBillController customerBillController = new CustomerBillController(customerBillView, currentCustomerId);
            customerBillController.open();
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
            billlabel.setForeground(Color.white);
            billlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            billlabel.setForeground(Color.black);
            billlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
            JFrame customerHomeView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            customerHomeView.dispose();

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
        customerHomeView.displayRestaurants(allRestaurants, currentCustomerData.getId(), currentCustomerData.getFullName());
    }
    
    private void displayFilteredRestaurants() {
        customerHomeView.displayRestaurants(filteredRestaurants, currentCustomerData.getId(), currentCustomerData.getFullName());
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
            CustomerMenuController customerMenuContrller = new CustomerMenuController(customerMenuView, currentCustomerId);
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
