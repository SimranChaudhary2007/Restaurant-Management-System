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
import restaurant.management.system.model.StaffData;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.RegisterStaffView;
import restaurant.management.system.view.RegisterUsernamePasswordView;

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
        setPlaceholder(registerStaffView.getEmailTextField(), "Email");
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
    
    class RegisterStaff implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerStaffView.getFullNameTextField().getText();
            String restaurantName = registerStaffView.getRestaurantNameTextField().getText();
            String phoneNumber = registerStaffView.getPhoneNumberTextField().getText();
            String email = registerStaffView.getEmailTextField().getText();
            
            StaffData details = new StaffData(fullName, restaurantName, phoneNumber, email);
            RegisterUsernamePasswordView registerUsernamePasswordView= new RegisterUsernamePasswordView();
            RegisterUsernamePasswordController registerUsernamePasswordController = new RegisterUsernamePasswordController(registerUsernamePasswordView, details);
            registerUsernamePasswordController.open();
        }
    }
}
