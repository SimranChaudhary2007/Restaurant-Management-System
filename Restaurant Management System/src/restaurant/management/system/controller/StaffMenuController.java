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
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.StaffHomeView;
import restaurant.management.system.view.StaffMenuView;
import restaurant.management.system.view.StaffOrdersView;
import restaurant.management.system.view.StaffProfileView;

/**
 *
 * @author labis
 */
public class StaffMenuController {
    
    private StaffMenuView staffMenuView;
    private MenuDao menuDao;
    private int currentStaffId;
    private int currentOwnerId;
    
    private List<MenuData> allMenu;
    private List<MenuData> filteredMenu;
    private MenuData currentMenuItem;
    
    
    public StaffMenuController(StaffMenuView view, int staffId){
        this.staffMenuView = view;
        this.menuDao = new MenuDao();
        this.currentStaffId = staffId;
        this.allMenu = new ArrayList<>();
        this.filteredMenu = new ArrayList<>();
        this.currentMenuItem = null;
        
        this.staffMenuView.homeNavigation(new StaffMenuController.HomeNav(staffMenuView.getHomelabel()));
        this.staffMenuView.profileNavigation(new StaffMenuController.ProfileNav(staffMenuView.getProfilelabel()));
        this.staffMenuView.orderNavigation(new StaffMenuController.OrderNav(staffMenuView.getOrderlabel()));
        this.staffMenuView.logoutNavigation(new StaffMenuController.LogoutNav(staffMenuView.getLogoutlabel()));
        
        
        setupNavigationListeners();
        loadMenuItems();
    }
    
    public void setCurrentMenuItem(MenuData item) {
        this.currentMenuItem = item;
    }
    
    public void open(){
        this.staffMenuView.setVisible(true);
    }
    
    public void close(){
        this.staffMenuView.dispose();
    }
    
    
    private void loadMenuItems() {
        try {
            // Load all menu items for this owner
            allMenu = menuDao.getMenuByStaff(currentStaffId);
            filteredMenu = new ArrayList<>(allMenu);
            
            // Use the view's displayMenu method to show the items
            staffMenuView.displayMenu(allMenu);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(staffMenuView, 
                "Error loading menu: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupNavigationListeners() {
    // Get the tabbed pane from the view
    JTabbedPane tabbedPane = staffMenuView.getMenuTabbedPane();
    
    // Setup listeners for each icon
    addNavigationListener(staffMenuView.getCoffeeIcon(), tabbedPane, 0); // Hot Beverage
    addNavigationListener(staffMenuView.getDrinksIcon(), tabbedPane, 1); // Cold Beverage
    addNavigationListener(staffMenuView.getMomoIcon(), tabbedPane, 2);   // MoMo
    addNavigationListener(staffMenuView.getPizzaIcon(), tabbedPane, 3);  // Pizza
    addNavigationListener(staffMenuView.getBurgerIcon(), tabbedPane, 4); // Burger
    addNavigationListener(staffMenuView.getRamenIcon(), tabbedPane, 5);  // Ramen
    addNavigationListener(staffMenuView.getSpaghettiIcon(), tabbedPane, 6); // Spaghetti
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
    
    
    class HomeNav implements MouseListener{
        
        private JLabel homelabel;
        
        public HomeNav(JLabel label) {
            this.homelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            StaffHomeView staffHomeView = new StaffHomeView();
            StaffHomeController staffHomeController= new StaffHomeController(staffHomeView, currentStaffId, currentOwnerId);
            staffHomeController.open();
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
            StaffProfileView staffProfileView = new StaffProfileView();
            StaffProfileController staffProfileController= new StaffProfileController(staffProfileView, currentStaffId);
            staffProfileController.open();
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
            StaffOrdersView staffOrdersView = new StaffOrdersView();
            StaffOrdersController staffOrdersController= new StaffOrdersController(staffOrdersView, currentStaffId);
            staffOrdersController.open();
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
            JFrame adminMenuView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            adminMenuView.dispose();

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
