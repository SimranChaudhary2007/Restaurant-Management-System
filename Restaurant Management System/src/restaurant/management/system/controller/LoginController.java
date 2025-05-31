/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
        
        setEmailPlaceholder(this.loginView.getEmailTextField(), "E-mail");
        setPasswordPlaceholder(this.loginView.getPasswordField(), "Password");
    }
    public void open(){
        this.loginView.setVisible(true);
    }
    public void close(){
        this.loginView.dispose();
    }
    
    private void setEmailPlaceholder(JTextField emailField, String placeholder) {
        emailField.setText(placeholder);
        emailField.setForeground(Color.GRAY);

        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals(placeholder)) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText(placeholder);
                    emailField.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    private void setPasswordPlaceholder(JPasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
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
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}

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
