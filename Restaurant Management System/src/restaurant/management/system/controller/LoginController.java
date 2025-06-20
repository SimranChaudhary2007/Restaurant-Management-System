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
import javax.swing.JTextField;
import restaurant.management.system.UIElements.PasswordField;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.view.AdminHomeView;
import restaurant.management.system.view.CustomerHomeView;
import restaurant.management.system.view.ForgotPasswordOTPView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.StaffProfileView;

/**
 *
 * @author acer
 */
public class LoginController {
    private LoginView loginView = new LoginView();
    public LoginController(LoginView view){
        this.loginView = view;    
        this.loginView.signUpNavigation(new SignupNav(loginView.getSignUplabel()));
        this.loginView.loginUser(new LoginUser());
        this.loginView.forgotPassword(new ForgotPassword(loginView.getForgotPasswordlabel()));
        
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
    
    private void setPasswordPlaceholder(PasswordField passwordField, String placeholder) {
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
    
    class LoginUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmailTextField().getText();
            String password = String.valueOf(loginView.getPasswordField().getPassword());
            
            // Validation
            if (email.isEmpty() || password.isEmpty() || 
                isPlaceholder(email, "E-mail") || isPlaceholder(password, "Password")) {
                JOptionPane.showMessageDialog(loginView, "Fill in all the fields");
                return;
            }
            
            LoginRequest loginRequest = new LoginRequest(email, password);
            
            Object authenticatedUser = authenticateUser(loginRequest);
            
            if (authenticatedUser == null) {
                JOptionPane.showMessageDialog(loginView, 
                    "Incorrect username or password. Please try again!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String userType = getUserType(authenticatedUser);
                JOptionPane.showMessageDialog(loginView, 
                    "Logged in successfully as " + userType);
                
                navigateToUserDashboard(authenticatedUser, userType);
            }
        }
        
        private Object authenticateUser(LoginRequest loginRequest) {
            OwnerDao ownerDao = new OwnerDao();
            OwnerData owner = ownerDao.login(loginRequest);
            if (owner != null) {
                return owner;
            }

            StaffDao staffDao = new StaffDao();
            StaffData staff = staffDao.login(loginRequest);
            if (staff != null) {
                return staff;
            }
 
            CustomerDao customerDao = new CustomerDao();
            CustomerData customer = customerDao.login(loginRequest);
            if (customer != null) {
                return customer;
            }
            
            return null;
        }
        
        private String getUserType(Object user) {
            if (user instanceof OwnerData) {
                return "Owner";
            } else if (user instanceof StaffData) {
                return "Staff";
            } else if (user instanceof CustomerData) {
                return "Customer";
            }
            return "Unknown";
        }
    }    
        
     
    class ForgotPassword implements MouseListener{
            
            private JLabel forgotPasswordLabel;
    
        public ForgotPassword(JLabel label){
            this.forgotPasswordLabel = label;
        }

    @Override
    public void mouseClicked(MouseEvent e) {
        String email = JOptionPane.showInputDialog(loginView, "Enter your email address:");

        if (email == null) {
            return;
        }

        if (email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Email field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(loginView, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OwnerDao ownerDao = new OwnerDao();
        OwnerData user = ownerDao.checkEmail(email);

        if (user == null) {
            JOptionPane.showMessageDialog(loginView, "Email does not exist in our records", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        close();

        ForgotPasswordOTPView forgetPasswordView = new ForgotPasswordOTPView();
        ForgotPasswordOTPController forgetPasswordController = new ForgotPasswordOTPController(forgetPasswordView, email);
        forgetPasswordController.open();
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        forgotPasswordLabel.setForeground(Color.BLACK);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    }
     
    private void navigateToUserDashboard(Object user, String userType) {
            close();
            
            // Navigate based on user type
            switch (userType) {
                case "Owner":
                    // Navigate to Admin Home Page
                    AdminHomeView adminHomeView = new AdminHomeView();
                    AdminHomeController adminHomeController = new AdminHomeController(adminHomeView, ((OwnerData) user).getId());
                    adminHomeController.open();
                    break;
                    
                case "Staff":
                    // Navigate to Staff Home Page
                    StaffProfileView staffProfileView = new StaffProfileView();
                    StaffProfileController staffProfileController = new StaffProfileController(staffProfileView,((StaffData) user).getId());
                    staffProfileController.open();
                    break;
                    
                case "Customer":
                    // Navigate to Customer Home Page
                    CustomerHomeView customerHomeView = new CustomerHomeView();
                    CustomerHomeController customerHomeController = new CustomerHomeController(customerHomeView, (CustomerData) user);
                    customerHomeController.open();
                    break;
                    
                default:
                    JOptionPane.showMessageDialog(loginView, "Unknown user type");
                    break;
            }
        }
    }
