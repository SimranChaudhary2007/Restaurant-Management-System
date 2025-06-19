/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import restaurant.management.system.view.RegistrationOTPView;
import restaurant.management.system.view.RegisterStaffView;
import restaurant.management.system.view.RegisterUsernamePasswordView;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.view.RegisterCustomerView;
import restaurant.management.system.view.RegisterOwnerView;

/**
 *
 * @author acer
 */
public class RegistrationOTPController {
    private RegistrationOTPView otpView;
    private String generatedOtp;
    private String userEmail;
    private String fullName;
    private String restaurantName;
    private String phoneNumber;
    private String userType;
    private String additionalData;
    
    public RegistrationOTPController(RegistrationOTPView view) {
        this.otpView = view;
        this.otpView.nextNavigation(new NextNav());
        this.otpView.backNavigation(new BackNav());
    }
    
    public RegistrationOTPController(RegistrationOTPView view, String email, String fullName, String restaurantName, String phoneNumber, String userType) {
        this(view);
        this.userEmail = email;
        this.fullName = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.userType = userType.toUpperCase();
    }
    
    public RegistrationOTPController(RegistrationOTPView view, String email, String fullName, String restaurantName, String phoneNumber, String userType, String additionalData) {
        this(view, email, fullName, restaurantName, phoneNumber, userType);
        this.additionalData = additionalData;
    }
    
    public void open() {
        this.otpView.setVisible(true);
    }
    
    public void close() {
        this.otpView.dispose();
    }
    
    public void setGeneratedOtp(String otp) {
        this.generatedOtp = otp;
    }
    
    private boolean verifyOtp() {
        String enteredOtp = otpView.getVerificationcode().getText().trim();
        
        if (enteredOtp.isEmpty()) {
            JOptionPane.showMessageDialog(otpView, "Please enter the verification code", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (enteredOtp.length() != 6 || !enteredOtp.matches("\\d{6}")) {
            JOptionPane.showMessageDialog(otpView, "Please enter a valid 6-digit verification code.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    class NextNav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (verifyOtp()) {
                JOptionPane.showMessageDialog(otpView, 
                    "OTP verified successfully! Please create your username and password to complete registration.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                RegisterUsernamePasswordView usernamePasswordView = new RegisterUsernamePasswordView();
                
                switch (userType) {
                    case "OWNER":
                        OwnerData ownerData = new OwnerData(fullName, restaurantName, phoneNumber, additionalData != null ? additionalData : "", userEmail);
                        
                        RegisterUsernamePasswordController ownerController = new RegisterUsernamePasswordController(usernamePasswordView, ownerData);
                        ownerController.open();
                        break;
                        
                    case "STAFF":
                        StaffData staffData = new StaffData(fullName, restaurantName, phoneNumber, userEmail);
                        
                        RegisterUsernamePasswordController staffController = new RegisterUsernamePasswordController(usernamePasswordView, staffData);
                        staffController.open();
                        break;
                        
                    case "CUSTOMER":
                        CustomerData customerData = new CustomerData(fullName, additionalData != null ? additionalData : "", phoneNumber, userEmail);
                        
                        RegisterUsernamePasswordController customerController = new RegisterUsernamePasswordController(usernamePasswordView, customerData);
                        customerController.open();
                        break;
                }
                close();
            }
        }
    }
    
    class BackNav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(otpView, 
                "Are you sure you want to go back? Your registration progress will be lost.", 
                "Confirmation", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                switch (userType) {
                    case "OWNER":
                        RegisterOwnerView registerOwnerView = new RegisterOwnerView();
                        RegisterOwnerController registerOwnerController = new RegisterOwnerController(registerOwnerView);
                        registerOwnerController.open();
                        break;
                    case "STAFF":
                        RegisterStaffView registerStaffView = new RegisterStaffView();
                        RegisterStaffController registerStaffController = new RegisterStaffController(registerStaffView);
                        registerStaffController.open();
                        break;
                    case "CUSTOMER":
                        RegisterCustomerView registerCustomerView = new RegisterCustomerView();
                        RegisterCustomerController registerCustomerController = new RegisterCustomerController(registerCustomerView);
                        registerCustomerController.open();
                        break;
                    default:
                        RegisterStaffView defaultStaffView = new RegisterStaffView();
                        RegisterStaffController defaultStaffController = new RegisterStaffController(defaultStaffView);
                        defaultStaffController.open();
                        break;
                }
                close();
            }
        }
    }
}