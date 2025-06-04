/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.AdminMenuView;

/**
 *
 * @author pradeepta 3434
 */
public class AdminMenuController {
    private AdminMenuView adminMenuView = new AdminMenuView();
    public AdminMenuController(AdminMenuView view){
        this.adminMenuView  = view;    
    }
    public void open(){
        this.adminMenuView .setVisible(true);
    }
    public void close(){
        this.adminMenuView .dispose();
    }
}
