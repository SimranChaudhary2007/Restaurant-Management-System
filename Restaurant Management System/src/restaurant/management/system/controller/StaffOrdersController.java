package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.StaffHomeView;
import restaurant.management.system.view.StaffMenuView;
import restaurant.management.system.view.StaffOrdersView;
import restaurant.management.system.view.StaffProfileView;

/**
 *
 * @author ACER
 */
public class StaffOrdersController {
    private StaffOrdersView staffOrdersView;
    private int currentStaffId;
    private int currentOwnerId;
    private OrderDao orderDao;
    
    // Static tracking of open StaffOrdersController instances
    private static java.util.Map<Integer, StaffOrdersController> openControllers = new java.util.HashMap<>();
    
    public StaffOrdersController(StaffOrdersView view, int staffId) {
        this.staffOrdersView = view;
        this.orderDao = new OrderDao();
        this.currentStaffId = staffId;
        
        // Register this controller instance
        openControllers.put(staffId, this);
        
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
    
    // Static method to refresh all StaffOrdersController instances
    public static void refreshAllStaffOrdersViews() {
        for (StaffOrdersController controller : openControllers.values()) {
            controller.refreshOrderDisplay();
        }
    }
    
    // Static method to refresh specific StaffOrdersController
    public static void refreshStaffOrdersView(int staffId) {
        StaffOrdersController controller = openControllers.get(staffId);
        if (controller != null) {
            controller.refreshOrderDisplay();
        }
    }
    
    // Static method to unregister controller when closed
    public static void unregisterController(int staffId) {
        openControllers.remove(staffId);
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
        // Unregister this controller instance
        openControllers.remove(currentStaffId);
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
            StaffHomeView staffHomeView = new StaffHomeView();
            StaffHomeController staffHomeController = new StaffHomeController(staffHomeView, currentStaffId, currentOwnerId);
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
            StaffProfileView staffProfileView = new StaffProfileView();
            StaffProfileController staffProfileController = new StaffProfileController(staffProfileView, currentStaffId);
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
            StaffMenuView staffMenuView = new StaffMenuView();
            StaffMenuController staffMenuController = new StaffMenuController(staffMenuView, currentStaffId);
            staffMenuController.open();
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
            JFrame staffMenuView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            staffMenuView.dispose();

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