package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.view.StaffOrdersView;

/**
 *
 * @author ACER
 */
public class StaffOrdersController {
    private StaffOrdersView staffOrdersView;
    private int currentStaffId;
    private OrderDao orderDao;
    
    public StaffOrdersController(StaffOrdersView view, int staffId) {
        this.staffOrdersView = view;
        this.orderDao = new OrderDao();
        this.currentStaffId = staffId;
        
        this.staffOrdersView.homeNavigation(new HomeNav(staffOrdersView.getHomelabel()));
        this.staffOrdersView.profileNavigation(new ProfileNav(staffOrdersView.getProfilelabel()));
        this.staffOrdersView.menuNavigation(new MenuNav(staffOrdersView.getMenulabel()));
        this.staffOrdersView.logoutNavigation(new LogoutNav(staffOrdersView.getLogoutlabel()));
        
        this.staffOrdersView.getPendingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJTabbedPane().setSelectedIndex(0);
                loadPendingOrders();
            }
        });
        
        this.staffOrdersView.getRecivedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJTabbedPane().setSelectedIndex(1);
                loadReceivedOrders();
            }
        });
        
        this.staffOrdersView.getBillButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJTabbedPane().setSelectedIndex(2);
                loadBilledOrders();
            }
        });
        
        // Load pending orders when the view is opened
        loadPendingOrders();
    }
    
    public void refreshOrderDisplay() {
        // Refresh all order tabs
        loadPendingOrders();
        loadReceivedOrders();
        loadBilledOrders();
    }
    
    private void loadPendingOrders() {
        try {
            List<OrderData> pendingOrders = orderDao.getOrdersByStatus("PENDING");
            staffOrdersView.displayPendingOrders(pendingOrders);
        } catch (Exception e) {
            System.err.println("Error loading pending orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadReceivedOrders() {
        try {
            List<OrderData> receivedOrders = orderDao.getOrdersByStatus("RECEIVED");
            staffOrdersView.displayReceivedOrders(receivedOrders);
        } catch (Exception e) {
            System.err.println("Error loading received orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadBilledOrders() {
        try {
            List<OrderData> billedOrders = orderDao.getOrdersByStatus("BILLED");
            staffOrdersView.displayBilledOrders(billedOrders);
        } catch (Exception e) {
            System.err.println("Error loading billed orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void open(){
        this.staffOrdersView.setVisible(true);
    }
    
    public void close(){
        this.staffOrdersView.dispose();
    }
    
    // Navigation classes
    class HomeNav implements MouseListener {
        private javax.swing.JLabel homelabel;
        
        public HomeNav(javax.swing.JLabel label) {
            this.homelabel = label;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            // Navigate to staff home
            restaurant.management.system.view.StaffHomeView staffHomeView = new restaurant.management.system.view.StaffHomeView();
            restaurant.management.system.controller.StaffHomeController staffHomeController = new restaurant.management.system.controller.StaffHomeController(staffHomeView, currentStaffId, 1); // Assuming owner ID is 1
            staffHomeController.open();
            close();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
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
    
    class ProfileNav implements MouseListener {
        private javax.swing.JLabel profilelabel;
        
        public ProfileNav(javax.swing.JLabel label) {
            this.profilelabel = label;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            restaurant.management.system.view.StaffProfileView staffProfileView = new restaurant.management.system.view.StaffProfileView();
            restaurant.management.system.controller.StaffProfileController staffProfileController = new restaurant.management.system.controller.StaffProfileController(staffProfileView, currentStaffId);
            staffProfileController.open();
            close();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
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
    
    class MenuNav implements MouseListener {
        private javax.swing.JLabel menulabel;
        
        public MenuNav(javax.swing.JLabel label) {
            this.menulabel = label;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            restaurant.management.system.view.AdminMenuView adminMenuView = new restaurant.management.system.view.AdminMenuView();
            restaurant.management.system.controller.AdminMenuController adminMenuController = new restaurant.management.system.controller.AdminMenuController(adminMenuView, 1); // Assuming owner ID is 1
            adminMenuController.open();
            close();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
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
    
    class LogoutNav implements MouseListener {
        private javax.swing.JLabel logoutlabel;
        
        public LogoutNav(javax.swing.JLabel label) {
            this.logoutlabel = label;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            restaurant.management.system.view.LoginView loginView = new restaurant.management.system.view.LoginView();
            restaurant.management.system.controller.LoginController loginController = new restaurant.management.system.controller.LoginController(loginView);
            loginController.open();
            close();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
        
        @Override
        public void mouseEntered(MouseEvent e) {
            logoutlabel.setForeground(Color.white);
            logoutlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            logoutlabel.setForeground(Color.black);
            logoutlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
} 