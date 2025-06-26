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
import restaurant.management.system.view.CustomerOrderView;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.view.CustomerHomeView;
import restaurant.management.system.view.CustomerProfileView;
import restaurant.management.system.view.LoginView;

/**
 *
 * @author acer
 */
public class CustomerOrderController {
    private CustomerOrderView customerOrderView;
    private CustomerData currentCustomerData;
    private int currentCustomerId;
    private OrderDao orderDao;
    private CustomerDao customerDao;

    public CustomerOrderController(CustomerOrderView view, int customerId) {
        this.customerOrderView = view;
        this.currentCustomerId = customerId;
        this.orderDao = new OrderDao();
        this.customerDao = new CustomerDao();
        this.currentCustomerData = getCustomerData(customerId);
        
        this.customerOrderView.homeNavigation(new HomeNav(customerOrderView.getHomelabel()));
        this.customerOrderView.profileNavigation(new ProfileNav (customerOrderView.getProfilelabel()));
        this.customerOrderView.billsNavigation(new BillsNav (customerOrderView.getBillslabel()));
        this.customerOrderView.logoutNavigation(new LogoutNav(customerOrderView.getLogoutlabel()));
        
        loadAndDisplayOrders();
    }

    private CustomerData getCustomerData(int customerId) {
        try {
            return customerDao.getCustomerById(customerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadAndDisplayOrders() {
        try {
            List<OrderData> orders = orderDao.getOrdersByCustomer(currentCustomerId);
            
            if (orders == null) {
                return;
            }
            
            customerOrderView.displayOrders(orders);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshOrders() {
        loadAndDisplayOrders();
    }

    public void open() {
        customerOrderView.setVisible(true);
    }

    public void close() {
        customerOrderView.dispose();
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
    
    class BillsNav implements MouseListener{
        
        private JLabel billlabel;
        
        public BillsNav(JLabel label) {
            this.billlabel = label;
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
                JFrame customerOrderView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
                customerOrderView.dispose();

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