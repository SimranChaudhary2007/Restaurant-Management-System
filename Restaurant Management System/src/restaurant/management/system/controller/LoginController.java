/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.RegisterAsView;

/**
 *
 * @author acer
 */
public class LoginController {
    private LoginView loginView = new LoginView();
    public LoginController(LoginView view){
        this.loginView = view;    
        this.loginView.signUpNavigation(new SignupNav(loginView.getSignUplabel()));
    }
    public void open(){
        this.loginView.setVisible(true);
    }
    public void close(){
        this.loginView.dispose();
    }
    
    class SignupNav implements MouseListener{
        
        private JLabel signuplabel;
        
        public SignupNav(JLabel label){
            this.signuplabel = label;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            RegisterAsView registerAsView = new RegisterAsView();
            RegisterAsController registerAsController= new RegisterAsController(registerAsView);
            registerAsController.open();
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
            signuplabel.setForeground(Color.BLUE);
            signuplabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            signuplabel.setForeground(Color.BLACK);
            signuplabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
    }
}
