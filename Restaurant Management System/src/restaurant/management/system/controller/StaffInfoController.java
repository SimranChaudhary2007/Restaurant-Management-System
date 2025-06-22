/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import restaurant.management.system.view.StaffInfoView;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.dao.StaffDao;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.StaffHomeView;
import restaurant.management.system.view.StaffProfileView;

/**
 *
 * @author samee
 */
public class StaffInfoController {
    private StaffInfoView staffInfoView;
    private int currentStaffId;
    private int currentOwnerId;
    private StaffDao staffDao = new StaffDao();

    public StaffInfoController(StaffInfoView view) {
        this.staffInfoView = view;
        
        this.staffInfoView.homeNavigation(new HomeNav(staffInfoView.getHomelabel()));
        this.staffInfoView.profileNavigation(new ProfileNav(staffInfoView.getProfilelabel()));
        this.staffInfoView.menuNavigation(new MenuNav(staffInfoView.getMenulabel()));
        this.staffInfoView.orderNavigation(new OrderNav(staffInfoView.getOrderlabel()));
        this.staffInfoView.logoutNavigation(new LogoutNav(staffInfoView.getLogoutlabel()));
        
        loadAndDisplayStaff();
    }

    private void loadAndDisplayStaff() {
        List<StaffData> staffList = staffDao.getApprovedStaff();
        staffInfoView.displayStaff(staffList, this);
    }

    public void open() {
        staffInfoView.setVisible(true);
    }

    public void close() {
        staffInfoView.dispose();
    }

    public void refreshStaffDisplay() {
        loadAndDisplayStaff();
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
    
    class MenuNav implements MouseListener{
        
        private JLabel menulabel;
        
        public MenuNav(JLabel label) {
            this.menulabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
//            StaffMenuView staffMenuView = new StaffMenuView();
//            StaffMenuController staffMenuController= new StaffMenuController(staffMenuView);
//            staffMenuController.open();
//            close();
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
//            StaffOrderView staffOrderView = new StaffOrderView();
//            StaffOrderController staffOrderController= new StaffOrderController(staffOrderView, currentStaffId);
//            staffOrderController.open();
//            close();
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
            JFrame staffInfoView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            staffInfoView.dispose();

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