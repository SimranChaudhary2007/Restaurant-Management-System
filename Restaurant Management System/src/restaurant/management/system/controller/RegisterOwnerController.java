/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.RegisterOwnerView;

/**
 *
 * @author ACER
 */
public class RegisterOwnerController {
    private  RegisterOwnerView registerownerView = new RegisterOwnerView();
    public RegisterOwnerController(RegisterOwnerView registerownerView){
        this.registerownerView = registerownerView;
    }
    public void open(){
        this.registerownerView.setVisible(true);
    }
    public void close(){
        this.registerownerView.dispose();
    }
    
}
