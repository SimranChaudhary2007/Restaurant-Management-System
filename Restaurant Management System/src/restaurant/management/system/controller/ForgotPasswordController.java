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
import restaurant.management.system.UIElements.RoundedTextField;
import restaurant.management.system.controller.mail.SMTPSMailSender;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.ResetRequest;
import restaurant.management.system.view.ForgotPasswordView;
import restaurant.management.system.view.LoginView;

/**
 *
 * @author acer
 */
public class ForgotPasswordController {
    private ForgotPasswordView forgetPasswordView;
    private String generatedOtp;
    private String userEmail;
    private boolean isOtpVerified = false;
    
    public ForgotPasswordController(ForgotPasswordView view) {
        this.forgetPasswordView = view;
        
        // Initialize event listeners
        this.forgetPasswordView.getNextButton().addActionListener(new NextButtonHandler());
        this.forgetPasswordView.getBackButton().addActionListener(new BackButtonHandler());
        
        // Set up placeholders
        setTextFieldPlaceholder(this.forgetPasswordView.getVerificationcode(), "Verification Code");
        setTextFieldPlaceholder(this.forgetPasswordView.getNewpassword(), "New Password");
        setTextFieldPlaceholder(this.forgetPasswordView.getConfirmpassword(), "Confirm Password");
        
        // Initially disable password fields until OTP is verified
        this.forgetPasswordView.getNewpassword().setEnabled(false);
        this.forgetPasswordView.getConfirmpassword().setEnabled(false);
    }
    
    public ForgotPasswordController(ForgotPasswordView view, String email) {
        this(view);
        this.userEmail = email;
        sendOtpToEmail();
    }
    
    public void open() {
        this.forgetPasswordView.setVisible(true);
    }
    
    public void close() {
        this.forgetPasswordView.dispose();
    }
    
    private void setTextFieldPlaceholder(RoundedTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    private boolean isPlaceholder(String text, String placeholder) {
        return text.equals(placeholder);
    }
    
    private void sendOtpToEmail() {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Email is required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if email exists in database
        OwnerDao ownerDao = new OwnerDao();
        OwnerData user = ownerDao.checkEmail(userEmail);
        
        if (user == null) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Email does not exist in our records", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Generate OTP
        generatedOtp = String.valueOf((int)(Math.random() * 900000) + 100000); // 6-digit OTP
        
        // Prepare email content
        String subject = "Your Password Reset OTP";
        String body = "Hello " + user.getFullName() + ",\n\n" +
                     "Your OTP for password reset is: " + generatedOtp + "\n\n" +
                     "Use this code to reset your password. This OTP is valid for 10 minutes.\n\n" +
                     "If you didn't request this reset, please ignore this email.\n\n" +
                     "Best regards,\nRestaurant Management System";
        
        // Send email
        boolean mailSent = SMTPSMailSender.sendMail(userEmail, subject, body);
        
        if (mailSent) {
            JOptionPane.showMessageDialog(forgetPasswordView, 
                "OTP has been sent to " + userEmail + "\nPlease check your email and enter the verification code.", 
                "OTP Sent", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(forgetPasswordView, 
                "Failed to send email. Please try again later.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean verifyOtp() {
        String enteredOtp = forgetPasswordView.getVerificationcode().getText().trim();
        
        if (isPlaceholder(enteredOtp, "Verification Code") || enteredOtp.isEmpty()) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Please enter the verification code", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!enteredOtp.equals(generatedOtp)) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Invalid verification code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean validatePasswords() {
        String newPassword = forgetPasswordView.getNewpassword().getText().trim();
        String confirmPassword = forgetPasswordView.getConfirmpassword().getText().trim();
        
        if (isPlaceholder(newPassword, "New Password") || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Please enter a new password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (isPlaceholder(confirmPassword, "Confirm Password") || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Please confirm your password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(forgetPasswordView, "Password must be at least 6 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void resetPassword() {
        String newPassword = forgetPasswordView.getNewpassword().getText().trim();
        
        OwnerDao ownerDao = new OwnerDao();
        ResetRequest resetRequest = new ResetRequest(userEmail, newPassword);
        
        boolean success = ownerDao.updatePassword(resetRequest);
        
        if (success) {
            JOptionPane.showMessageDialog(forgetPasswordView, 
                "Password has been reset successfully!\nYou can now login with your new password.", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Navigate back to login
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            close();
        } else {
            JOptionPane.showMessageDialog(forgetPasswordView, 
                "Failed to reset password. Please try again.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class NextButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isOtpVerified) {
                // First step: Verify OTP
                if (verifyOtp()) {
                    isOtpVerified = true;
                    JOptionPane.showMessageDialog(forgetPasswordView, 
                        "OTP verified successfully! Please enter your new password.", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Enable password fields
                    forgetPasswordView.getNewpassword().setEnabled(true);
                    forgetPasswordView.getConfirmpassword().setEnabled(true);
                    
                    // Change button text
                    forgetPasswordView.getNextButton().setText("Reset");
                }
            } else {
                // Second step: Reset password
                if (validatePasswords()) {
                    resetPassword();
                }
            }
        }
    }
    
    class BackButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(forgetPasswordView, 
                "Are you sure you want to go back? Any progress will be lost.", 
                "Confirm", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
                close();
            }
        }
    }
    
    public ForgotPasswordView getForgetPasswordView() {
        return forgetPasswordView;
    }
}
