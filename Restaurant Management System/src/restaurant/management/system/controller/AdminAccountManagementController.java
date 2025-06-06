package restaurant.management.system.controller;

import restaurant.management.system.view.AdminAccountManagementView;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pradeepta 3434
 */
public class AdminAccountManagementController {
    private AdminAccountManagementView adminAccountManagementView = new AdminAccountManagementView();
    public AdminAccountManagementController(AdminAccountManagementView view){
        this.adminAccountManagementView  = view;  
    }
    public void open(){
        this.adminAccountManagementView .setVisible(true);
    }
    public void close(){
        this.adminAccountManagementView .dispose();
    }
}
