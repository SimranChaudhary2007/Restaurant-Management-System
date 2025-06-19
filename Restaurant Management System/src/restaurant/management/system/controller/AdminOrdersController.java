/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;
import restaurant.management.system.view.AdminOrdersView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminOrdersController {
    private AdminOrdersView view;

    public AdminOrdersController(AdminOrdersView view) {
        this.view = view;
        this.view.getPendingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJTabbedPane().setSelectedIndex(0); 
            }
        });
        
        this.view.getRecivedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJTabbedPane().setSelectedIndex(1);
            }
            
        });
    }
}