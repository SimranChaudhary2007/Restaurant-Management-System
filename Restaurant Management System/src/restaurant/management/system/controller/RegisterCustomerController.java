/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurant.management.system.model.CustomerData;
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
        registerCustomerView.registerCustomer(new RegisterCustomer());
    }
    public void open(){
        this.registerCustomerView.setVisible(true);
    }
    public void close(){
        this.registerCustomerView.dispose();
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
