/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.StaffData;
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
        this.loginView.loginOwner(new LoginOwner());
        this.loginView.loginStaff(new LoginStaff());
        this.loginView.loginCustomer(new LoginCustomer());
        
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
    
    private boolean isPlaceholder(String text, String placeholder) {
        return text.equals(placeholder);
    }
    
    class LoginOwner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmailTextField().getText();
            String password = String.valueOf(loginView.getPasswordField().getPassword());
            if (email.isEmpty()||password.isEmpty()||isPlaceholder(email, "E-mail")||isPlaceholder(password, "Password")){
                JOptionPane.showMessageDialog(loginView, "Fill in all the fields");
            }else {
                OwnerDao ownerDao = new OwnerDao();
                LoginRequest loginRequest = new LoginRequest(email,password);
                OwnerData owner = ownerDao.login(loginRequest);
                if (owner ==null){
                    JOptionPane.showMessageDialog(loginView, "Incorrect username or password.Please try again!","Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(loginView, "Logged in successfully");
                }
            }
        } 
    }
    
    class LoginStaff implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmailTextField().getText();
            String password = String.valueOf(loginView.getPasswordField().getPassword());
            if (email.isEmpty()||password.isEmpty()||isPlaceholder(email, "E-mail")||isPlaceholder(password, "Password")){
                JOptionPane.showMessageDialog(loginView, "Fill in all the fields");
            } else {
                StaffDao staffDao = new StaffDao();
                LoginRequest loginRequest = new LoginRequest(email,password);
                StaffData staff = staffDao.login(loginRequest);
                if (staff == null){
                    JOptionPane.showMessageDialog(loginView, "Incorrect username or password.Please try again!","Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(loginView, "Logged in successfully");
                }
            }
        }
    }
        
    class LoginCustomer implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmailTextField().getText();
            String password = String.valueOf(loginView.getPasswordField().getPassword());
            if (email.isEmpty()||password.isEmpty()||isPlaceholder(email, "E-mail")||isPlaceholder(password, "Password")){
                JOptionPane.showMessageDialog(loginView, "Fill in all the fields");
            }else {
                CustomerDao customerDao = new CustomerDao();
                LoginRequest loginRequest = new LoginRequest(email,password);
                CustomerData customer = customerDao.login(loginRequest);
                if (customer ==null){
                    JOptionPane.showMessageDialog(loginView, "Incorrect username or password.Please try again!","Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(loginView, "Logged in successfully");
                }
            }
        }
        
    }
}
