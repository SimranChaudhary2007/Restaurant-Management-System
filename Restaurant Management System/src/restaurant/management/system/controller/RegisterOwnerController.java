/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.view.RegisterOwnerView;
import restaurant.management.system.view.RegisterUsernamePasswordView;

/**
 *
 * @author ACER
 */
public class RegisterOwnerController {
    private RegisterOwnerView registerOwnerView = new RegisterOwnerView();
    public RegisterOwnerController(RegisterOwnerView registerownerView){
        this.registerOwnerView = registerownerView;
        registerownerView.registerOwner(new RegisterOwner());
    }
    public void open(){
        this.registerOwnerView.setVisible(true);

    }
    public void close(){
        this.registerOwnerView.dispose();
    }
    
    class RegisterOwner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerOwnerView.getFullNameTextField().getText();
            String restaurantName = registerOwnerView.getRestaurantNameTextField().getText();
            String phoneNumber = registerOwnerView.getPhoneTextField().getText();
            String address = registerOwnerView.getAddressTextField().getText();
            String email = registerOwnerView.getEmailTextField().getText();
            
            OwnerData details = new OwnerData(fullName,restaurantName,phoneNumber,address,email);
            RegisterUsernamePasswordView registerUsernamePasswordView= new RegisterUsernamePasswordView();
            RegisterUsernamePasswordController registerUsernamePasswordController= new RegisterUsernamePasswordController(registerUsernamePasswordView,details);
            registerUsernamePasswordController.open();
        }
    }
}
