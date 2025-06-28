/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.view.CustomerBillView;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.view.CustomerHomeView;
import restaurant.management.system.view.CustomerProfileView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.CustomerOrderView;

/**
 *
 * @author acer
 */
public class CustomerBillController {
    private CustomerBillView customerBillView;
    private CustomerData currentCustomerData;
    private int currentCustomerId;
    private OrderDao orderDao;
    private CustomerDao customerDao;
    
    // Static tracking of open CustomerBillController instances
    private static java.util.Map<Integer, CustomerBillController> openControllers = new java.util.HashMap<>();

    public CustomerBillController(CustomerBillView view, int customerId) {
        this.customerBillView = view;
        this.currentCustomerId = customerId;
        this.orderDao = new OrderDao();
        this.customerDao = new CustomerDao();
        this.currentCustomerData = getCustomerData(customerId);
        
        // Register this controller instance
        openControllers.put(customerId, this);
        
        this.customerBillView.homeNavigation(new HomeNav(customerBillView.getHomelabel()));
        this.customerBillView.profileNavigation(new ProfileNav(customerBillView.getProfilelabel()));
        this.customerBillView.orderNavigation(new OrderNav(customerBillView.getOrderlabel()));
        this.customerBillView.logoutNavigation(new LogoutNav(customerBillView.getLogoutlabel()));
        
        loadAndDisplayBilledOrders();
    }

    private CustomerData getCustomerData(int customerId) {
        try {
            return customerDao.getCustomerById(customerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadAndDisplayBilledOrders() {
        try {
            // Get only billed orders for this customer
            List<OrderData> billedOrders = orderDao.getBilledOrdersByCustomer(currentCustomerId);
            
            if (billedOrders == null) {
                return;
            }
            
            customerBillView.displayOrders(billedOrders);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshOrders() {
        loadAndDisplayBilledOrders();
    }

    public void open() {
        customerBillView.setVisible(true);
    }

    public void close() {
        customerBillView.dispose();
        
        // Unregister this controller instance
        openControllers.remove(currentCustomerId);
    }
    
    // Method to add a specific order to the bill view
    public void addOrderToBill(OrderData order) {
        if (order != null && "BILLED".equals(order.getOrderStatus())) {
            customerBillView.addOrder(order);
        }
    }
    
    // Static method to open CustomerBillView for a specific customer
    public static void openCustomerBillView(int customerId) {
        CustomerBillView billView = new CustomerBillView();
        CustomerBillController controller = new CustomerBillController(billView, customerId);
        controller.open();
    }
    
    // Static method to refresh CustomerBillView for a specific customer
    public static void refreshCustomerBillView(int customerId) {
        CustomerBillController controller = openControllers.get(customerId);
        if (controller != null) {
            controller.refreshOrders();
        }
    }
    
    // Static method to remove a controller from tracking when closed
    public static void unregisterController(int customerId) {
        openControllers.remove(customerId);
    }
    
    class HomeNav implements MouseListener{
        
        private JLabel homelabel;
        
        public HomeNav(JLabel label) {
            this.homelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentCustomerData == null) {
                JOptionPane.showMessageDialog(null, "Error: Customer data not loaded", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CustomerHomeView customerHomeView = new CustomerHomeView();
            CustomerHomeController customerHomeController= new CustomerHomeController(customerHomeView, currentCustomerData);
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
            if (currentCustomerData == null) {
                JOptionPane.showMessageDialog(null, "Error: Customer data not loaded", "Error", JOptionPane.ERROR_MESSAGE);
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
            if (currentCustomerData == null) {
                JOptionPane.showMessageDialog(null, "Error: Customer data not loaded", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CustomerOrderView customerOrderView = new CustomerOrderView();
            CustomerOrderController customerOrderController = new CustomerOrderController(customerOrderView, currentCustomerId);
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
                JFrame customerBillView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
                customerBillView.dispose();

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
            logoutlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}