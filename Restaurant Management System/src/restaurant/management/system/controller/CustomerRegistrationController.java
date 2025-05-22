/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.CustomerRegistrationView;

/**
 *
 * @author ACER
 */
public class CustomerRegistrationController {
    private  CustomerRegistrationView customerregistrationView = new CustomerRegistrationView();
    public CustomerRegistrationController(CustomerRegistrationView customerregistrationView){
        this.customerregistrationView = customerregistrationView;
    }
    public void open(){
        this.customerregistrationView.setVisible(true);
    }
    public void close(){
        this.customerregistrationView.dispose();
    }
    
}
    
    
}
