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
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.RegisterCustomerView;
import restaurant.management.system.view.RegisterUsernamePasswordView;

/**
 *
 * @author ACER
 */
public class RegisterCustomerController {
    private  RegisterCustomerView registerCustomerView = new RegisterCustomerView();
    public RegisterCustomerController(RegisterCustomerView registerCustomerView){
        this.registerCustomerView = registerCustomerView;
        this.registerCustomerView.registerCustomer(new RegisterCustomer());
        this.registerCustomerView.mainpage(new Mainpage());
        
        setPlaceholder(registerCustomerView.getFullNameTextField(), "Full Name");
        setPlaceholder(registerCustomerView.getAddressField(), "Address");
        setPlaceholder(registerCustomerView.getPhoneNumberTextField(), "Phone Number");
        setPlaceholder(registerCustomerView.getEmailTextField(), "E-mail");
    }
    public void open(){
        this.registerCustomerView.setVisible(true);
    }
    public void close(){
        this.registerCustomerView.dispose();
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
    class RegisterCustomer implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerCustomerView.getFullNameTextField().getText();
            String address = registerCustomerView.getAddressField().getText();
            String phoneNumber = registerCustomerView.getPhoneNumberTextField().getText();
            String email = registerCustomerView.getEmailTextField().getText();
            
            
            // VALIDATION
            if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || email.isEmpty() ||
                isPlaceholder(fullName, "Full Name") || 
                isPlaceholder(phoneNumber, "Phone Number") || isPlaceholder(address, " Address") ||
                isPlaceholder(email, "E-mail")) {

                JOptionPane.showMessageDialog(registerCustomerView, "All fields must be filled.");
                return;
            }
            if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email)) {
                JOptionPane.showMessageDialog(registerCustomerView, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("^\\d{7,15}$", phoneNumber)) {
                JOptionPane.showMessageDialog(registerCustomerView, "Please enter a valid phone number (7 to 15 digits).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CustomerData details = new CustomerData(fullName, address, phoneNumber, email);
            RegisterUsernamePasswordView registerUsernamePasswordView= new RegisterUsernamePasswordView();
            RegisterUsernamePasswordController registerUsernamePasswordController = new RegisterUsernamePasswordController(registerUsernamePasswordView, details);
            registerUsernamePasswordController.open();
        }
        
    }
}
