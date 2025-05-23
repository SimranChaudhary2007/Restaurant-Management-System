/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package restaurant.management.system;

import restaurant.management.system.view.RegisterAsView;
//import restaurant.management.system.controller.RegisterAsController;


/**
 *
 * @author labis
 */
public class RestaurantManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RegisterAsView view = new RegisterAsView();
//        RegisterAsController controller = new RegisterAsController(view);
        view.setVisible(true);
    }
    
}
