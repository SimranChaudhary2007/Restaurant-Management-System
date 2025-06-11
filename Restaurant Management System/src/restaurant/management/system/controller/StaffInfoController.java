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
    private StaffInfoView StaffInfoView = new StaffInfoView();
    public StaffInfoController(StaffInfoView view){
        this.StaffInfoView  = view;  
    }
    public void open(){
        this.StaffInfoView .setVisible(true);
    }
    public void close(){
        this.StaffInfoView .dispose();
    }
}
