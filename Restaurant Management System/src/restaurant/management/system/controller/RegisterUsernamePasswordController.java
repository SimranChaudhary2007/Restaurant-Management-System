package restaurant.management.system.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.OwnerData;
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
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView,OwnerData ownerData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.ownerData = ownerData;
        registerusernamepasswordView.registerOwner(new RegisterOwner());
        
    }
    public void open(){
        this.registerusernamepasswordView.setVisible(true);
    }
    public void close(){
        this.registerusernamepasswordView.dispose();
    }
    
    class RegisterOwner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String username = registerusernamepasswordView.getUsernameTextField().getText();
            String password = String.valueOf(registerusernamepasswordView.getPasswordField().getPassword());
            String confirmpassword = String.valueOf(registerusernamepasswordView.getConfirmPasswordField().getPassword()); 
            
          
            ownerData.setusername(username);
            ownerData.setPassword(password);
                
            boolean success = new OwnerDao().register(ownerData);
            if (success){
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered successfully");
            } else {
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered failed");
            }
        }    
    }
}
