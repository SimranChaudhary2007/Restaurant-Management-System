/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import restaurant.management.system.controller.mail.SMTPSMailSender;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.view.OTPView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.PasswordResetView;

/**
 *
 * @author acer
 */
public class OTPController {
    private OTPView otpView;
    private String generatedOtp;
    private String userEmail;
    
    public OTPController(OTPView view) {
        this.otpView = view;
        this.otpView.getNextButton().addActionListener(new NextButtonHandler());
        this.otpView.getBackButton().addActionListener(new BackButtonHandler());
    }
    
    public OTPController(OTPView view, String email) {
        this(view);
        this.userEmail = email;
        sendOtpToEmail();
    }
    
    public void open() {
        this.otpView.setVisible(true);
    }
    
    public void close() {
        this.otpView.dispose();
    }
    
    
    private void sendOtpToEmail() {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            JOptionPane.showMessageDialog(otpView, "Email is required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        OwnerDao ownerDao = new OwnerDao();
        OwnerData user = ownerDao.checkEmail(userEmail);
        
        if (user == null) {
            JOptionPane.showMessageDialog(otpView, "Email does not exist in our records", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        generatedOtp = String.valueOf((int)(Math.random() * 900000) + 100000); // 6-digit OTP
        
        String subject = "Your Password Reset OTP";
        String body = "Hello " + user.getFullName() + ",\n\n" +
                     "Your OTP for password reset is: " + generatedOtp + "\n\n" +
                     "Use this code to reset your password. This OTP is valid for 10 minutes.\n\n" +
                     "If you didn't request this reset, please ignore this email.\n\n" +
                     "Best regards,\nSajilo Serve (Restaurant Management System)";
        
        boolean mailSent = SMTPSMailSender.sendMail(userEmail, subject, body);
        
        if (mailSent) {
            JOptionPane.showMessageDialog(otpView, 
                "OTP has been sent to " + userEmail + "\nPlease check your email and enter the verification code.", 
                "OTP Sent", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(otpView, 
                "Failed to send email. Please try again later.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean verifyOtp() {
        String enteredOtp = otpView.getVerificationcode().getText().trim();
        
        if (enteredOtp.isEmpty()) {
            JOptionPane.showMessageDialog(otpView, "Please enter the verification code", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!enteredOtp.equals(generatedOtp)) {
            JOptionPane.showMessageDialog(otpView, "Invalid verification code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    class NextButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (verifyOtp()) {
                JOptionPane.showMessageDialog(otpView, 
                    "OTP verified successfully! Redirecting to password reset page.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Navigate to password reset page
                PasswordResetView passwordResetView = new PasswordResetView();
                PasswordResetController passwordResetController = new PasswordResetController(passwordResetView, userEmail);
                passwordResetController.open();
                close();
            }
        }
    }
    
     class BackButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(otpView, 
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
    
    public OTPView getOTPView() {
        return otpView;
    }
}