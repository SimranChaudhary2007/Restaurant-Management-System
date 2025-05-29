/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurant.management.system.model.StaffData;
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
        registerStaffView.registerStaff(new RegisterStaff());
    }
    public void open(){
        this.registerStaffView.setVisible(true);
    }
    public void close(){
        this.registerStaffView.dispose();
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
