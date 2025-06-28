/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.ResetRequest;
import restaurant.management.system.view.AdminAccountManagementView;
import restaurant.management.system.view.AdminHomeView;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.view.AdminProfileView;
import restaurant.management.system.view.LoginView;

/**
 *
 * @author pradeepta 3434
 */
public class AdminAccountManagementController {
    private AdminAccountManagementView adminAccountManagementView;
    private OwnerDao ownerDao = new OwnerDao();
    private int currentOwnerId;
    
    public AdminAccountManagementController(AdminAccountManagementView view, int ownerId) {
        System.out.println("DEBUG: AdminAccountManagementController created with ownerId = " + ownerId);
        this.adminAccountManagementView = view;
        this.currentOwnerId = ownerId;
        
        this.adminAccountManagementView.homeNavigation(new HomeNav(adminAccountManagementView.getHomelabel()));
        this.adminAccountManagementView.profileNavigation(new ProfileNav(adminAccountManagementView.getProfilelabel()));
        this.adminAccountManagementView.menuNavigation(new MenuNav (adminAccountManagementView.getMenulabel()));
        this.adminAccountManagementView.orderNavigation(new OrderNav (adminAccountManagementView.getOrderlabel()));
        this.adminAccountManagementView.logoutNavigation(new LogoutNav(adminAccountManagementView.getLogoutlabel()));
        
        initializeController();
    }
    
    private void initializeController() {
        // Change Username button action
        // Change Password submit button action
        adminAccountManagementView.getChangeButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePasswordChange();
            }
        });
        adminAccountManagementView.getChangeUsernameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminAccountManagementView.hideChangePasswordPanel();
                if (adminAccountManagementView.getChangeUsernamePanelRound().isVisible()) {
                    adminAccountManagementView.hideChangeUsernamePanel();
                } else {
                    adminAccountManagementView.showChangeUsernamePanel();
                }
            }
        });
        
        // Change Password button action
        adminAccountManagementView.getChangePasswordButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminAccountManagementView.hideChangeUsernamePanel();
                if (adminAccountManagementView.getChangePasswordPanelRound().isVisible()) {
                    adminAccountManagementView.hideChangePasswordPanel();
                } else {
                    adminAccountManagementView.showChangePasswordPanel();
                }
            }
        });
        
        // Change Username submit button action
        adminAccountManagementView.getChangeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUsernameChange();
            }
        });
        
        // Delete Account button action
        adminAccountManagementView.getDeleteAccountButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminAccountManagementView.hideChangeUsernamePanel();
                adminAccountManagementView.hideChangePasswordPanel();
                if (adminAccountManagementView.getDeleteAccountPanelRound().isVisible()) {
                    adminAccountManagementView.hideDeleteAccountPanel();
                } else {
                    adminAccountManagementView.showDeleteAccountPanel();
                }
            }
        });
        
        // Delete Account confirm button action
        adminAccountManagementView.getDeleteAccountConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAccountDeletion();
            }
        });
    }
    
    private void handleUsernameChange() {
        String currentUsername = adminAccountManagementView.getCurrentUsernameTextField().getText().trim();
        String password = new String(adminAccountManagementView.getPasswordTextField().getPassword()).trim();
        String newUsername = adminAccountManagementView.getNewUsernameTextField().getText().trim();
        
        // Validate inputs
        if (currentUsername.isEmpty() || password.isEmpty() || newUsername.isEmpty()) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Please fill in all fields", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (currentUsername.equals(newUsername)) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "New username cannot be the same as current username", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Attempt to update username
        boolean success = ownerDao.updateUsername(currentOwnerId, currentUsername, password, newUsername);
        
        if (success) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Username updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields after successful update
            adminAccountManagementView.getCurrentUsernameTextField().setText("");
            adminAccountManagementView.getPasswordTextField().setText("");
            adminAccountManagementView.getNewUsernameTextField().setText("");
            
            // Hide the panel
            adminAccountManagementView.hideChangeUsernamePanel();
        } else {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Failed to update username. Please check your current username and password.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handlePasswordChange() {
    
        String currentPassword = new String(adminAccountManagementView.getCurrentPasswordTextField().getPassword()).trim();
        String newPassword = new String(adminAccountManagementView.getNewPasswordTextField().getPassword()).trim();
        String confirmNewPassword = new String(adminAccountManagementView.getConfirmNewPasswordTextField().getPassword()).trim();

        // Validation checks
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Please fill in all fields", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "New password and confirm password do not match", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newPassword.equals(currentPassword)) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "New password cannot be the same as current password", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify current password is correct
        OwnerData owner = ownerDao.getOwnerById(currentOwnerId);
        if (owner == null) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Failed to retrieve owner information", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        OwnerData verifiedOwner = ownerDao.login(new LoginRequest(owner.getEmail(), currentPassword));
        if (verifiedOwner == null) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Current password is incorrect", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update password
        ResetRequest resetRequest = new ResetRequest(owner.getEmail(), newPassword);
        boolean success = ownerDao.updatePassword(resetRequest);

        if (success) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Password updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);

            // Clear fields
            adminAccountManagementView.getCurrentPasswordTextField().setText("");
            adminAccountManagementView.getNewPasswordTextField().setText("");
            adminAccountManagementView.getConfirmNewPasswordTextField().setText("");
            
            // Hide the panel
            adminAccountManagementView.hideChangePasswordPanel();
        } else {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Failed to update password. Please check your current password.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleAccountDeletion() {
        String password = new String(adminAccountManagementView.getDeletePasswordTextField().getPassword()).trim();
        String confirmPassword = new String(adminAccountManagementView.getDeleteConfirmPasswordTextField().getPassword()).trim();
        
        // Validate inputs
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Please fill in all fields", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Password and confirm password do not match", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Show confirmation dialog
        int confirmResult = JOptionPane.showConfirmDialog(adminAccountManagementView, 
            "Are you sure you want to delete your account? This action cannot be undone.", 
            "Confirm Account Deletion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmResult == JOptionPane.YES_OPTION) {
            // Attempt to delete account
            boolean success = ownerDao.deleteOwner(currentOwnerId, password);
            
            if (success) {
                JOptionPane.showMessageDialog(adminAccountManagementView, 
                    "Account deleted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                adminAccountManagementView.getDeletePasswordTextField().setText("");
                adminAccountManagementView.getDeleteConfirmPasswordTextField().setText("");
                
                // Hide the panel
                adminAccountManagementView.hideDeleteAccountPanel();
                
                // Navigate to login view
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
                adminAccountManagementView.dispose();
            } else {
                JOptionPane.showMessageDialog(adminAccountManagementView, 
                    "Failed to delete account. Please check your password.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void open() {
        this.adminAccountManagementView.setVisible(true);
    }
    
    public void close() {
        this.adminAccountManagementView.dispose();
    }
    
    public void setCurrentOwnerId(int ownerId) {
        this.currentOwnerId = ownerId;
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
            JFrame adminAccountManagementView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            adminAccountManagementView.dispose();

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
