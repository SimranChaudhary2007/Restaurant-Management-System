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
import restaurant.management.system.view.RegisterOwnerView;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.view.RegistrationOTPView;

/**
 *
 * @author ACER
 */
public class RegisterOwnerController {
    private RegisterOwnerView registerOwnerView = new RegisterOwnerView();
    public RegisterOwnerController(RegisterOwnerView registerOwnerView){
        this.registerOwnerView = registerOwnerView;
        this.registerOwnerView.registerOwner(new RegisterOwner());
        this.registerOwnerView.mainpage(new Mainpage());
        
        setPlaceholder(registerOwnerView.getFullNameTextField(), "Full Name");
        setPlaceholder(registerOwnerView.getRestaurantNameTextField(), "Restaurant Name");
        setPlaceholder(registerOwnerView.getRestaurantAddressTextField(), "Restaurant Address");
        setPlaceholder(registerOwnerView.getPhoneNumberTextField(), "Phone Number");
        setPlaceholder(registerOwnerView.getEmailTextField(),"E-mail");
    }
    public void open(){
        this.registerOwnerView.setVisible(true);

    }
    public void close(){
        this.registerOwnerView.dispose();
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
    
    class RegisterOwner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerOwnerView.getFullNameTextField().getText();
            String restaurantName = registerOwnerView.getRestaurantNameTextField().getText();
            String restaurantAddress = registerOwnerView.getRestaurantAddressTextField().getText();
            String phoneNumber = registerOwnerView.getPhoneNumberTextField().getText();
            String email = registerOwnerView.getEmailTextField().getText();
             //Validation
            if (fullName.isEmpty() || restaurantName.isEmpty() || phoneNumber.isEmpty() || restaurantAddress.isEmpty() || email.isEmpty() ||
                isPlaceholder(fullName, "Full Name") || isPlaceholder(restaurantName, "Restaurant Name") ||
                isPlaceholder(phoneNumber, "Phone Number") || isPlaceholder(restaurantAddress, "Restaurant Address") ||
                isPlaceholder(email, "E-mail")) {

                JOptionPane.showMessageDialog(registerOwnerView, "All fields must be filled.");
                return;
            }
            if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email)) {
                JOptionPane.showMessageDialog(registerOwnerView, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("^\\d{7,15}$", phoneNumber)) {
                JOptionPane.showMessageDialog(registerOwnerView, "Please enter a valid phone number (7 to 15 digits).", "Error", JOptionPane.ERROR_MESSAGE);

                return;
            }
            
            OwnerDao ownerDao = new OwnerDao();
            if (ownerDao.checkEmail(email) != null) {
                JOptionPane.showMessageDialog(registerOwnerView, "Email already exists in our system. Please use a different email.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String generatedOtp = String.valueOf((int)(Math.random() * 900000) + 100000);
            String subject = "Registration Verification - OTP";
            String body = "Hello " + fullName + ",\n\n" +
                         "Welcome to Sajilo Serve Restaurant Management System!\n\n" +
                         "You are registering as an Owner.\n" +
                         "Your OTP for registration verification is: " + generatedOtp + "\n\n" +
                         "Use this code to complete your registration. This OTP is valid for 10 minutes.\n\n" +
                         "If you didn't request this registration, please ignore this email.\n\n" +
                         "Best regards,\nSajilo Serve Team";
            
            boolean mailSent = restaurant.management.system.controller.mail.SMTPSMailSender.sendMail(email, subject, body);
            
            if (mailSent) {
                JOptionPane.showMessageDialog(registerOwnerView, 
                    "OTP has been sent to " + email + "\nPlease check your email and enter the verification code to continue registration.", 
                    "OTP Sent", JOptionPane.INFORMATION_MESSAGE);
                
                RegistrationOTPView otpView = new RegistrationOTPView();
                RegistrationOTPController otpController = new RegistrationOTPController(otpView, email, fullName, restaurantName, phoneNumber, "OWNER", restaurantAddress);
                otpController.open();
                close();
            } else {
                JOptionPane.showMessageDialog(registerOwnerView, 
                    "Failed to send verification email. Please try again later.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
