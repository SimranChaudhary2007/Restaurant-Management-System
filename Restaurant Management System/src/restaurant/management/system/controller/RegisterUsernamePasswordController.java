package restaurant.management.system.controller;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.view.RegisterCustomerView;
import restaurant.management.system.view.RegisterOwnerView;
import restaurant.management.system.view.RegisterStaffView;
import restaurant.management.system.view.RegisterUsernamePasswordView;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author acer
 */
public class RegisterUsernamePasswordController {
    private  RegisterUsernamePasswordView registerusernamepasswordView = new RegisterUsernamePasswordView();
    private OwnerData ownerData;
    private StaffData staffData;
    private CustomerData customerData;
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView,OwnerData ownerData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.ownerData = ownerData;
        this.registerusernamepasswordView.registerOwner(new RegisterOwner());
        this.registerusernamepasswordView.backNavigation(new BackNav());
        setUsernamePlaceholder(registerusernamepasswordView.getUsernameTextField(), "Username");
        setPasswordPlaceholder(registerusernamepasswordView.getPasswordField(), "Password");
        setConfirmPasswordPlaceholder(registerusernamepasswordView.getConfirmPasswordField(), "Confirm Password");
        
    }
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView,StaffData staffData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.staffData = staffData;
        this.registerusernamepasswordView.registerStaff(new RegisterStaff());
        this.registerusernamepasswordView.backNavigation(new BackNav());
    }
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView, CustomerData customerData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.customerData = customerData;
        this.registerusernamepasswordView.registerCustomer(new RegisterCustomer());
        this.registerusernamepasswordView.backNavigation(new BackNav());
    }
    public void open(){
        this.registerusernamepasswordView.setVisible(true);
    }
    public void close(){
        this.registerusernamepasswordView.dispose();
    }
    private void setUsernamePlaceholder(javax.swing.JTextField usenameField, String placeholder) {
    usenameField.setText(placeholder);
    usenameField.setForeground(Color.GRAY);

    usenameField.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (usenameField.getText().equals(placeholder)) {
                usenameField.setText("");
                usenameField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (usenameField.getText().trim().isEmpty()) {
                usenameField.setText(placeholder);
                usenameField.setForeground(Color.GRAY);
            }
        }
    });
}
    private void setPasswordPlaceholder(JPasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
    }
    
        private void setConfirmPasswordPlaceholder(JPasswordField confirmPasswordField, String placeholder) {
        confirmPasswordField.setText(placeholder);
        confirmPasswordField.setForeground(Color.GRAY);
        confirmPasswordField.setEchoChar((char) 0);

        confirmPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(confirmPasswordField.getPassword()).equals(placeholder)) {
                    confirmPasswordField.setText("");
                    confirmPasswordField.setForeground(Color.BLACK);
                    confirmPasswordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(confirmPasswordField.getPassword()).isEmpty()) {
                    confirmPasswordField.setText(placeholder);
                    confirmPasswordField.setForeground(Color.GRAY);
                    confirmPasswordField.setEchoChar((char) 0);
                }
            }
        });
    }
    class BackNav implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            if(ownerData != null){
                RegisterOwnerView registerOwnerView = new RegisterOwnerView();
                registerOwnerView.getFullNameTextField().setText(ownerData.getFullName());
                registerOwnerView.getRestaurantNameTextField().setText(ownerData.getRestaurantName());
                registerOwnerView.getRestaurantAddressTextField().setText(ownerData.getRestaurantAddress());
                registerOwnerView.getPhoneNumberTextField().setText(ownerData.getPhoneNumber());
                registerOwnerView.getEmailTextField().setText(ownerData.getEmail());
    
            }else if (staffData != null){
                RegisterStaffView registerStaffView = new RegisterStaffView();
                registerStaffView.getFullNameTextField().setText(staffData.getFullName());
                registerStaffView.getRestaurantNameTextField().setText(staffData.getRestaurantName());
                registerStaffView.getPhoneNumberTextField().setText(staffData.getPhoneNumber());
                registerStaffView.getEmailTextField().setText(staffData.getEmail());
                
            }else if (customerData != null){
                RegisterCustomerView registerCustomerView = new RegisterCustomerView();
                registerCustomerView.getFullNameTextField().setText(customerData.getFullName());
                registerCustomerView.getAddressField().setText(customerData.getAddress());
                registerCustomerView.getPhoneNumberTextField().setText(customerData.getPhoneNumber());
                registerCustomerView.getEmailTextField().setText(customerData.getEmail());
                
            }
        }
        
    }
    
    class RegisterOwner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String username = registerusernamepasswordView.getUsernameTextField().getText();
            String password = String.valueOf(registerusernamepasswordView.getPasswordField().getPassword());
            String confirmpassword = String.valueOf(registerusernamepasswordView.getConfirmPasswordField().getPassword()); 
            
            if(username.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
                return;
            }
            if(!password.equals(confirmpassword)){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "Password not matched.Please try again!");
                return;
            }
          
            ownerData.setUsername(username);
            ownerData.setPassword(password);
                
            boolean success = new OwnerDao().register(ownerData);
            if (success){
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered successfully");
            } else {
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered failed");
            }
        }    
    }
    
    class RegisterStaff implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerusernamepasswordView.getUsernameTextField().getText();
            String password = String.valueOf(registerusernamepasswordView.getPasswordField().getPassword());
            String confirmpassword = String.valueOf(registerusernamepasswordView.getConfirmPasswordField().getPassword());
            
            if(username.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
            }else if(!password.equals(confirmpassword)){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "Password not matched.Please try again!");
            }
          
            staffData.setUsername(username);
            staffData.setPassword(password);
                
            boolean success = new StaffDao().register(staffData);
            if (success){
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered successfully");
            } else {
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered failed");
            }
        }    
    }
    
    class RegisterCustomer implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerusernamepasswordView.getUsernameTextField().getText();
            String password = String.valueOf(registerusernamepasswordView.getPasswordField().getPassword());
            String confirmpassword = String.valueOf(registerusernamepasswordView.getConfirmPasswordField().getPassword());
            
            if(username.isEmpty() || username.equals("Username") ||password.isEmpty() || password.equals("Password") ||confirmpassword.isEmpty() || confirmpassword.equals("Confirm Password")) {
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
                return;
            }else if(!password.equals(confirmpassword)){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "Password not matched.Please try again!");
            }
            
            customerData.setUsername(username);
            customerData.setPassword(password);
            
            boolean success = new CustomerDao().register(customerData);
            if (success){
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered successfully");
            } else {
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered failed");
            }
            
        }
        
    }
}
