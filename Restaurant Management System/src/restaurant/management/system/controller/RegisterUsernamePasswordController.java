package restaurant.management.system.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.StaffData;
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
        registerusernamepasswordView.registerOwner(new RegisterOwner());
    }
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView,StaffData staffData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.staffData = staffData;
        registerusernamepasswordView.registerStaff(new RegisterStaff());
    }
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView, CustomerData customerData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.customerData = customerData;
        registerusernamepasswordView.registerCustomer(new RegisterCustomer());
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
            
            if(username.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
            }else if(!password.equals(confirmpassword)){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "Password not matched.Please try again!");
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
            
            if(username.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
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
