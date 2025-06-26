/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;
import java.awt.Color;
import java.awt.Cursor;
import restaurant.management.system.view.AdminOrdersView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.view.AdminHomeView;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.view.AdminProfileView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.model.OrderData;
import java.util.List;

public class AdminOrdersController {
    private AdminOrdersView adminOrderview;
    private int currentOwnerId;
    private OrderDao orderDao;
    
    public AdminOrdersController(AdminOrdersView view, int ownerId) {
        this.adminOrderview = view;
        this.orderDao = new OrderDao();
        this.currentOwnerId = ownerId;
        
        this.adminOrderview.homeNavigation(new HomeNav(adminOrderview.getHomelabel()));
        this.adminOrderview.profileNavigation(new ProfileNav(adminOrderview.getProfilelabel()));
        this.adminOrderview.menuNavigation(new MenuNav (adminOrderview.getMenulabel()));
        this.adminOrderview.logoutNavigation(new LogoutNav(adminOrderview.getLogoutlabel()));
        
        this.adminOrderview.getPendingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJTabbedPane().setSelectedIndex(0);
                loadPendingOrders();
            }
        });
        
        this.adminOrderview.getRecivedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJTabbedPane().setSelectedIndex(1);
                loadReceivedOrders();
            }
            
        });
        
        // Load pending orders when the view is opened
        loadPendingOrders();
        
    }
    
    public void refreshOrderDisplay() {
        // Refresh both pending and received orders
        loadPendingOrders();
        loadReceivedOrders();
    }
    
    private void loadPendingOrders() {
        try {
            List<OrderData> pendingOrders = orderDao.getOrdersByStatus("PENDING");
            adminOrderview.displayPendingOrders(pendingOrders);
        } catch (Exception e) {
            System.err.println("Error loading pending orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadReceivedOrders() {
        try {
            List<OrderData> receivedOrders = orderDao.getOrdersByStatus("RECEIVED");
            adminOrderview.displayReceivedOrders(receivedOrders);
        } catch (Exception e) {
            System.err.println("Error loading received orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void open(){
        this.adminOrderview .setVisible(true);
    }
    public void close(){
        this.adminOrderview .dispose();
    }
    
    class HomeNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public HomeNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminHomeView adminHomeView = new AdminHomeView();
            AdminHomeController adminHomeController= new AdminHomeController(adminHomeView, currentOwnerId);
            adminHomeController.open();
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
    
    class ProfileNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public ProfileNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminProfileView adminProfileView = new AdminProfileView();
            AdminProfileController adminProfileController= new AdminProfileController(adminProfileView, currentOwnerId);
            adminProfileController.open();
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
        AdminMenuView adminMenuView = new AdminMenuView();
        AdminMenuController adminMenuController = new AdminMenuController(adminMenuView, currentOwnerId);
        adminMenuController.open();
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
            JFrame adminOrderview = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            adminOrderview.dispose();

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
