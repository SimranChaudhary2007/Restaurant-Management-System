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
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.RegisterOwnerView;
import restaurant.management.system.view.RegisterUsernamePasswordView;

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
            
            OwnerData details = new OwnerData(fullName,restaurantName,phoneNumber,restaurantAddress,email);
            
            RegisterUsernamePasswordView registerUsernamePasswordView = new RegisterUsernamePasswordView();
            RegisterUsernamePasswordController registerUsernamePasswordController = new RegisterUsernamePasswordController(registerUsernamePasswordView, details);
            registerUsernamePasswordController.open();
        }
    }
}
