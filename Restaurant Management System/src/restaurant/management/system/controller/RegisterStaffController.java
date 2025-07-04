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
import restaurant.management.system.dao.StaffRequestDao;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.view.RegisterUsernamePasswordView;

/**
 *
 * @author acer
 */
public class RegisterStaffController {
    private  RegisterStaffView registerStaffView = new RegisterStaffView();
    private StaffRequestDao staffRequestDao;
    public RegisterStaffController(RegisterStaffView registerStaffView){
        this.registerStaffView = registerStaffView;
        this.staffRequestDao = new StaffRequestDao();
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
            
            if (staffRequestDao.hasExistingPendingRequest(email)) {
                JOptionPane.showMessageDialog(registerStaffView, 
                    "A registration request for this email is already pending admin approval.\nPlease wait for the admin to process your request.", 
                    "Request Pending", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validate restaurant name
            restaurant.management.system.dao.OwnerDao ownerDao = new restaurant.management.system.dao.OwnerDao();
            restaurant.management.system.model.OwnerData owner = ownerDao.getOwnerByRestaurantName(restaurantName);
            if (owner == null) {
                JOptionPane.showMessageDialog(registerStaffView, "Restaurant unavailable.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int ownerId = owner.getId();
            
            // If all validation passes, go directly to username/password registration (no OTP for staff)
            StaffData staffData = new StaffData(fullName, restaurantName, phoneNumber, email);
            staffData.setOwnerId(ownerId);
            RegisterUsernamePasswordView registerUsernamePasswordView = new RegisterUsernamePasswordView();
            RegisterUsernamePasswordController registerUsernamePasswordController = new RegisterUsernamePasswordController(registerUsernamePasswordView, staffData);
            registerUsernamePasswordController.open();
            close();
        }
    }
}