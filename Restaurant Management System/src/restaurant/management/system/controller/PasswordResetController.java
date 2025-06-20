/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;
import restaurant.management.system.UIElements.PasswordField;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.ResetRequest;
import restaurant.management.system.view.PasswordResetView;
import restaurant.management.system.view.LoginView;

/**
 *
 * @author acer
 */
public class PasswordResetController {
    private PasswordResetView passwordResetView;
    private String userEmail;
    
    public PasswordResetController(PasswordResetView view, String email) {
        this.passwordResetView = view;
        this.userEmail = email;
        this.passwordResetView.getDoneButton().addActionListener(new DoneButtonHandler());
        this.passwordResetView.getBackButton().addActionListener(new BackButtonHandler());
        
        setPasswordFieldPlaceholder(this.passwordResetView.getNewPassword(), "New Password");
        setPasswordFieldPlaceholder(this.passwordResetView.getConfirmPassword(), "Confirm Password");
    }
    
    public void open() {
        this.passwordResetView.setVisible(true);
    }
    
    public void close() {
        this.passwordResetView.dispose();
    }
    
    private void setPasswordFieldPlaceholder(PasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder);
        passwordField.setEchoChar((char) 0);
        passwordField.setForeground(Color.GRAY);
        
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    private boolean isPlaceholder(String text, String placeholder) {
        return text.equals(placeholder);
    }
    
    private boolean validatePasswords() {
        String newPassword = String.valueOf(passwordResetView.getNewPassword().getPassword());
        String confirmPassword = String.valueOf(passwordResetView.getConfirmPassword().getPassword());
        
        if (isPlaceholder(newPassword, "New Password") || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(passwordResetView, "Please enter a new password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (isPlaceholder(confirmPassword, "Confirm Password") || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(passwordResetView, "Please confirm your password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(passwordResetView, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(passwordResetView, "Password must be at least 6 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void resetPassword() {
        String newPassword = String.valueOf(passwordResetView.getNewPassword().getPassword());
        
        OwnerDao ownerDao = new OwnerDao();
        ResetRequest resetRequest = new ResetRequest(userEmail, newPassword);
        
        boolean success = ownerDao.updatePassword(resetRequest);
        
        if (success) {
            JOptionPane.showMessageDialog(passwordResetView, 
                "Password has been reset successfully!\nYou can now login with your new password.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Navigate back to login
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            close();
        } else {
            JOptionPane.showMessageDialog(passwordResetView, 
                "Failed to reset password. Please try again.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class DoneButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validatePasswords()) {
                resetPassword();
            }
        }
    }
    
    class BackButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(passwordResetView, 
                "Are you sure you want to go back? Any progress will be lost.", 
                "Confirmation", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
                close();
            }
        }
    }
    
    public PasswordResetView getPasswordResetView() {
        return passwordResetView;
    }
}
