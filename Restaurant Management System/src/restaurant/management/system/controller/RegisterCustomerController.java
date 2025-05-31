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
        setPlaceholder(registerCustomerView.getEmailTextField(), "Email");
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
    class RegisterCustomer implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerCustomerView.getFullNameTextField().getText();
            String address = registerCustomerView.getAddressField().getText();
            String phoneNumber = registerCustomerView.getPhoneNumberTextField().getText();
            String email = registerCustomerView.getEmailTextField().getText();
            
            CustomerData details = new CustomerData(fullName, address, phoneNumber, email);
            RegisterUsernamePasswordView registerUsernamePasswordView= new RegisterUsernamePasswordView();
            RegisterUsernamePasswordController registerUsernamePasswordController = new RegisterUsernamePasswordController(registerUsernamePasswordView, details);
            registerUsernamePasswordController.open();
        }
        
    }
}
