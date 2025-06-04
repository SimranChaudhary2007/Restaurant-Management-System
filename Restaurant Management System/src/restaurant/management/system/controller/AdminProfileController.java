/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.AdminProfileView;

/**
 *
 * @author acer
 */
public class AdminProfileController {
    private AdminProfileView adminProfileView = new AdminProfileView();
    public AdminProfileController(AdminProfileView view){
        this.adminProfileView  = view;    
    }
    public void open(){
        this.adminProfileView .setVisible(true);
    }
    public void close(){
        this.adminProfileView .dispose();
    }
    
}
