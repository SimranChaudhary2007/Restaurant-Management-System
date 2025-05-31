/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }
    public void open(){
        this.registerOwnerView.setVisible(true);

    }
    public void close(){
        this.registerOwnerView.dispose();
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
    
    class RegisterOwner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerOwnerView.getFullNameTextField().getText();
            String restaurantName = registerOwnerView.getRestaurantNameTextField().getText();
            String phoneNumber = registerOwnerView.getPhoneNumberTextField().getText();
            String restaurantAddress = registerOwnerView.getRestaurantAddressTextField().getText();
            String email = registerOwnerView.getEmailTextField().getText();
            
            OwnerData details = new OwnerData(fullName,restaurantName,phoneNumber,restaurantAddress,email);
            RegisterUsernamePasswordView registerUsernamePasswordView = new RegisterUsernamePasswordView();
            RegisterUsernamePasswordController registerUsernamePasswordController = new RegisterUsernamePasswordController(registerUsernamePasswordView, details);
            registerUsernamePasswordController.open();
        }
    }
}
