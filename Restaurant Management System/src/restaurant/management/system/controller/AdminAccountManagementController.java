package restaurant.management.system.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.ResetRequest;
import restaurant.management.system.view.AdminAccountManagementView;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pradeepta 3434
 */
public class AdminAccountManagementController {
    private AdminAccountManagementView adminAccountManagementView;
    private OwnerDao ownerDao = new OwnerDao();
    private int currentOwnerId;
    
    public AdminAccountManagementController(AdminAccountManagementView view, int ownerId) {
        this.adminAccountManagementView = view;
        this.currentOwnerId = ownerId;
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

            // Hide panel
            adminAccountManagementView.hideChangePasswordPanel();
        } else {
            JOptionPane.showMessageDialog(adminAccountManagementView, 
                "Failed to update password. Please try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
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
}