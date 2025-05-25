/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.RegisterCustomerView;

/**
 *
 * @author ACER
 */
public class RegisterCustomerController {
    private  RegisterCustomerView registercustomerView = new RegisterCustomerView();
    public RegisterCustomerController(RegisterCustomerView registercustomerView){
        this.registercustomerView = registercustomerView;
    }
    public void open(){
        this.registercustomerView.setVisible(true);
    }
    public void close(){
        this.registercustomerView.dispose();
    }
}
