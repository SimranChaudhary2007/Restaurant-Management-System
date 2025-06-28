/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.UIElements.CustomerMenuCardPanel;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.view.CustomerHomeView;
import restaurant.management.system.view.CustomerMenuView;
import restaurant.management.system.view.CustomerMenuView.CartPopup;
import restaurant.management.system.view.CustomerOrderView;
import restaurant.management.system.view.CustomerProfileView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.CustomerBillView;

/**
 *
 * @author labis
 */
public class CustomerMenuController {
    private CustomerMenuView customerMenuView;
    private MenuDao menuDao;
    private OrderDao orderDao;
    private int currentCustomerId;
    private CustomerData currentCustomerData;
    
    public CustomerMenuController(CustomerMenuView view, int customerId){
        this.customerMenuView = view;
        this.menuDao = new MenuDao();
        this.orderDao = new OrderDao();
        this.currentCustomerId = customerId;
        this.currentCustomerData = getCustomerData(customerId);

        setupCartButtonListener();
        setupNavigationListeners();
        loadAndDisplayMenuItems();
        
        this.customerMenuView.homeNavigation(new HomeNav(customerMenuView.getHomelabel()));
        this.customerMenuView.profileNavigation(new ProfileNav(customerMenuView.getProfilelabel()));
        this.customerMenuView.orderNavigation(new OrderNav (customerMenuView.getOrderlabel()));
        this.customerMenuView.billsNavigation(new BillsNav (customerMenuView.getBillslabel()));
        this.customerMenuView.logoutNavigation(new LogoutNav(customerMenuView.getLogoutlabel()));
    }
    
    public void displayMenuItems(List<MenuData> menuItems, CustomerData customer) {
        customerMenuView.displayMenu(menuItems, customer);
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
                        order.setCustomerId(currentCustomerId);
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
        // Ensure we have customer data before displaying
        if (currentCustomerData == null) {
            currentCustomerData = getCustomerData(currentCustomerId);
        }
        customerMenuView.displayMenu(menuItems, currentCustomerData);
    }
    
    private CustomerData getCustomerData(int customerId) {
        CustomerDao customerDao = new CustomerDao();
        return customerDao.getCustomerById(customerId);
    }
    
    class HomeNav implements MouseListener{
        
        private JLabel homelabel;
        
        public HomeNav(JLabel label) {
            this.homelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentCustomerData == null) {
                currentCustomerData = getCustomerData(currentCustomerId);
            }
            
            if (currentCustomerData == null) {
                JOptionPane.showMessageDialog(customerMenuView, 
                    "Error loading customer data. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CustomerHomeView customerHomeView = new CustomerHomeView();
            CustomerHomeController customerHomeController = new CustomerHomeController(customerHomeView, currentCustomerData);
            customerHomeController.open();
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
            homelabel.setForeground(Color.white);
            homelabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            homelabel.setForeground(Color.black);
            homelabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    class ProfileNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public ProfileNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Ensure we have customer data before navigating
            if (currentCustomerData == null) {
                currentCustomerData = getCustomerData(currentCustomerId);
            }
            
            // Check if customer data is still null
            if (currentCustomerData == null) {
                JOptionPane.showMessageDialog(customerMenuView, 
                    "Error loading customer data. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CustomerProfileView customerProfileView = new CustomerProfileView();
            CustomerProfileController customerProfileController = new CustomerProfileController(customerProfileView, currentCustomerData);
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
    
}