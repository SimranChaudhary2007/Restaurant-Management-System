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
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.RegisterStaffView;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.view.RegistrationOTPView;

/**
 *
 * @author acer
 */
public class RegisterStaffController {
    private  RegisterStaffView registerStaffView = new RegisterStaffView();
    public RegisterStaffController(RegisterStaffView registerStaffView){
        this.registerStaffView = registerStaffView;
        this.registerStaffView.registerStaff(new RegisterStaff());
        this.registerStaffView.mainpage(new Mainpage());
        
        setPlaceholder(registerStaffView.getFullNameTextField(), "Full Name");
        setPlaceholder(registerStaffView.getRestaurantNameTextField(), "Restaurant Name");
        setPlaceholder(registerStaffView.getPhoneNumberTextField(), "Phone Number");
        setPlaceholder(registerStaffView.getEmailTextField(), "E-mail");
    }
    public void open(){
        this.registerStaffView.setVisible(true);
    }
    public void close(){
        this.registerStaffView.dispose();
    }
    
    private void setPlaceholder(javax.swing.JTextField textField, String placeholder) {
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
            if (textField.getText().trim().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    });
}

    class Mainpage implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterAsView registerAsView = new RegisterAsView();
            RegisterAsController registerAsController = new RegisterAsController(registerAsView);
            registerAsController.open();
            close();
        }
        
    }
    private boolean isPlaceholder(String text, String placeholder) {
        return text.equals(placeholder);
    }
    class RegisterStaff implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerStaffView.getFullNameTextField().getText();
            String restaurantName = registerStaffView.getRestaurantNameTextField().getText();
            String phoneNumber = registerStaffView.getPhoneNumberTextField().getText();
            String email = registerStaffView.getEmailTextField().getText();
            
            
            // VALIDATION
            if (fullName.isEmpty() || restaurantName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() ||
                isPlaceholder(fullName, "Full Name") || isPlaceholder(restaurantName, "Restaurant Name") ||
                isPlaceholder(phoneNumber, "Phone Number")  ||
                isPlaceholder(email, "E-mail")) {

                JOptionPane.showMessageDialog(registerStaffView, "All fields must be filled.");
                return;
            }
            if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email)) {
                JOptionPane.showMessageDialog(registerStaffView, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("^\\d{7,15}$", phoneNumber)) {
                JOptionPane.showMessageDialog(registerStaffView, "Please enter a valid phone number (7 to 15 digits).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            StaffDao staffDao = new StaffDao();
            if (staffDao.isEmailRegistered(email)) {
                JOptionPane.showMessageDialog(registerStaffView, "Email already exists in our system. Please use a different email.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String generatedOtp = String.valueOf((int)(Math.random() * 900000) + 100000);
            String subject = "Registration Verification - OTP";
            String body = "Hello " + fullName + ",\n\n" +
                         "Welcome to Sajilo Serve Restaurant Management System!\n\n" +
                         "You are registering as a Staff.\n" +
                         "Your OTP for registration verification is: " + generatedOtp + "\n\n" +
                         "Use this code to complete your registration. This OTP is valid for 10 minutes.\n\n" +
                         "If you didn't request this registration, please ignore this email.\n\n" +
                         "Best regards,\nSajilo Serve Team";
            
            boolean mailSent = restaurant.management.system.controller.mail.SMTPSMailSender.sendMail(email, subject, body);
            
            if (mailSent) {
                JOptionPane.showMessageDialog(registerStaffView, 
                    "OTP has been sent to " + email + "\nPlease check your email and enter the verification code to continue registration.", 
                    "OTP Sent", JOptionPane.INFORMATION_MESSAGE);
                
                RegistrationOTPView otpView = new RegistrationOTPView();
                RegistrationOTPController otpController = new RegistrationOTPController(otpView, email, fullName, restaurantName, phoneNumber, "STAFF");
                otpController.open();
                close();
            } else {
                JOptionPane.showMessageDialog(registerStaffView, 
                    "Failed to send verification email. Please try again later.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
