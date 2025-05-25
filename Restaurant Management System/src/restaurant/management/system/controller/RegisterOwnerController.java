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
    private  RegisterOwnerView registerownerstaffView = new RegisterOwnerView();
    public RegisterOwnerController(RegisterOwnerView registerownerstaffView){
        this.registerownerstaffView = registerownerstaffView;
    }
    public void open(){
        this.registerownerstaffView.setVisible(true);
    }
    public void close(){
        this.registerownerstaffView.dispose();
    }
    
}
