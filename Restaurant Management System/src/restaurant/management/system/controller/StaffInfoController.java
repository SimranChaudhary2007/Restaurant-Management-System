/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.view.StaffInfoView;

/**
 *
 * @author samee
 */
public class StaffInfoController {
    private StaffInfoView staffInfoView = new StaffInfoView();
    public StaffInfoController(StaffInfoView view){
        this.staffInfoView  = view;  
    }
    public void open(){
        this.staffInfoView .setVisible(true);
    }
    public void close(){
        this.staffInfoView .dispose();
    }
}
