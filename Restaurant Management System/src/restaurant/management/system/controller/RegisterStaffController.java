/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;
import restaurant.management.system.view.RegisterStaffView;

/**
 *
 * @author acer
 */
public class RegisterStaffController {
    private  RegisterStaffView registerstaffView = new RegisterStaffView();
    public RegisterStaffController(RegisterStaffView registerstaffView){
        this.registerstaffView = registerstaffView;
    }
    public void open(){
        this.registerstaffView.setVisible(true);
    }
    public void close(){
        this.registerstaffView.dispose();
    }
    
    
}
