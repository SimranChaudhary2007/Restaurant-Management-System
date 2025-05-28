/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.RegisterCustomerView;
import restaurant.management.system.view.RegisterOwnerView;
import restaurant.management.system.view.RegisterStaffView;


public class RegisterAsController {
    private RegisterAsView registerAsView = new RegisterAsView();

    public RegisterAsController(RegisterAsView registerAsView) {
        this.registerAsView = registerAsView;
        this.registerAsView.loginNavigation(new LoginNav(registerAsView.getLoginLabel()));
        this.registerAsView.ownerNavigation(new OwnerNav());
        this.registerAsView.customerNavigation(new StaffNav());
        this.registerAsView.staffNavigation(new CustomerNav());
    }
    void open() {
        this.registerAsView.setVisible(true);
    }

    void close() {
        this.registerAsView.dispose();
    }
    
    class LoginNav implements MouseListener {
        
        private JLabel loginlabel;
        
        public LoginNav(JLabel label){
            this.loginlabel = label;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            loginlabel.setForeground(Color.BLUE);
            loginlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            loginlabel.setForeground(Color.BLACK);
            loginlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    class OwnerNav implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterOwnerView registerOwnerView = new RegisterOwnerView();
            RegisterOwnerController registerOwnerController = new RegisterOwnerController(registerOwnerView);
            registerOwnerController.open();
            close();
        }
    }
    
    class StaffNav implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterStaffView registerStaffView = new RegisterStaffView();
            RegisterStaffController registerStaffController = new RegisterStaffController(registerStaffView);
            registerStaffController.open();
            close();
        }
    }
    
    class CustomerNav implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterCustomerView registerCustomerView = new RegisterCustomerView();
            RegisterCustomerController registerCustomerController = new RegisterCustomerController(registerCustomerView);
            registerCustomerController.open();
            close();
        }
    }
}
