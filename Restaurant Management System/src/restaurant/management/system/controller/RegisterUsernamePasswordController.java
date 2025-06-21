package restaurant.management.system.controller;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import restaurant.management.system.UIElements.PasswordField;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.dao.StaffRequestDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.model.StaffRequestData;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.RegisterUsernamePasswordView;

/**
 *
 * @author acer
 */
public class RegisterUsernamePasswordController {
    private  RegisterUsernamePasswordView registerusernamepasswordView = new RegisterUsernamePasswordView();
    private OwnerData ownerData;
    private StaffData staffData;
    private CustomerData customerData;
    
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView,OwnerData ownerData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.ownerData = ownerData;
        this.registerusernamepasswordView.registerOwner(new RegisterOwner());
        this.registerusernamepasswordView.cancelAction(new CancelAction());
        
        setUsernamePlaceholder(registerusernamepasswordView.getUsernameTextField(), "Username");
        setPasswordPlaceholder(registerusernamepasswordView.getPasswordField(), "Password");
        setConfirmPasswordPlaceholder(registerusernamepasswordView.getConfirmPasswordField(), "Confirm Password");
        
    }
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView,StaffData staffData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.staffData = staffData;
        this.registerusernamepasswordView.registerStaff(new RegisterStaff());
        this.registerusernamepasswordView.cancelAction(new CancelAction());
        
        setUsernamePlaceholder(registerusernamepasswordView.getUsernameTextField(), "Username");
        setPasswordPlaceholder(registerusernamepasswordView.getPasswordField(), "Password");
        setConfirmPasswordPlaceholder(registerusernamepasswordView.getConfirmPasswordField(), "Confirm Password");
    }
    public RegisterUsernamePasswordController(RegisterUsernamePasswordView registerusernamepasswordView, CustomerData customerData){
        this.registerusernamepasswordView = registerusernamepasswordView;
        this.customerData = customerData;
        this.registerusernamepasswordView.registerCustomer(new RegisterCustomer());
        this.registerusernamepasswordView.cancelAction(new CancelAction());
        
        setUsernamePlaceholder(registerusernamepasswordView.getUsernameTextField(), "Username");
        setPasswordPlaceholder(registerusernamepasswordView.getPasswordField(), "Password");
        setConfirmPasswordPlaceholder(registerusernamepasswordView.getConfirmPasswordField(), "Confirm Password");
    }
    public void open(){
        this.registerusernamepasswordView.setVisible(true);
    }
    public void close(){
        this.registerusernamepasswordView.dispose();
    }
    private void setUsernamePlaceholder(javax.swing.JTextField usenameField, String placeholder) {
    usenameField.setText(placeholder);
    usenameField.setForeground(Color.GRAY);

    usenameField.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (usenameField.getText().equals(placeholder)) {
                usenameField.setText("");
                usenameField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (usenameField.getText().trim().isEmpty()) {
                usenameField.setText(placeholder);
                usenameField.setForeground(Color.GRAY);
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
    
        private void setConfirmPasswordPlaceholder(JPasswordField confirmPasswordField, String placeholder) {
        confirmPasswordField.setText(placeholder);
        confirmPasswordField.setForeground(Color.GRAY);
        confirmPasswordField.setEchoChar((char) 0);

        confirmPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(confirmPasswordField.getPassword()).equals(placeholder)) {
                    confirmPasswordField.setText("");
                    confirmPasswordField.setForeground(Color.BLACK);
                    confirmPasswordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(confirmPasswordField.getPassword()).isEmpty()) {
                    confirmPasswordField.setText(placeholder);
                    confirmPasswordField.setForeground(Color.GRAY);
                    confirmPasswordField.setEchoChar((char) 0);
                }
            }
        });
    }
    class CancelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(registerusernamepasswordView,
                "Are you sure you want to cancel? Your registration progress will be lost.", 
                "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    class RegisterOwner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String username = registerusernamepasswordView.getUsernameTextField().getText();
            String password = String.valueOf(registerusernamepasswordView.getPasswordField().getPassword());
            String confirmpassword = String.valueOf(registerusernamepasswordView.getConfirmPasswordField().getPassword()); 
            
            if(username.isEmpty() || username.equals("Username") ||password.isEmpty() || password.equals("Password") ||confirmpassword.isEmpty() || confirmpassword.equals("Confirm Password")){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
                return;
            }
            if(!password.equals(confirmpassword)){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "Password not matched.Please try again!");
                return;
            }
            if (password.length() < 6) {
            JOptionPane.showMessageDialog(registerusernamepasswordView, "Password must be at least 6 characters long!");
            return;
            }
          
            ownerData.setUsername(username);
            ownerData.setPassword(password);
                
            boolean success = new OwnerDao().register(ownerData);
            if (success){
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered successfully. Please Login to continue!");
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
                close();
            } else {
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered failed. Please try again!");
            }
        }    
    }
    
    class RegisterStaff implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerusernamepasswordView.getUsernameTextField().getText();
            String password = String.valueOf(registerusernamepasswordView.getPasswordField().getPassword());
            String confirmpassword = String.valueOf(registerusernamepasswordView.getConfirmPasswordField().getPassword());
            
            if(username.isEmpty() || username.equals("Username") ||password.isEmpty() || password.equals("Password") ||confirmpassword.isEmpty() || confirmpassword.equals("Confirm Password")){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
                return;
            }
            if(!password.equals(confirmpassword)){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "Password not matched.Please try again!");
                return;
            }
            if (password.length() < 6) {
            JOptionPane.showMessageDialog(registerusernamepasswordView, "Password must be at least 6 characters long!");
            return;
            }
          
            staffData.setUsername(username);
            staffData.setPassword(password);
            
            StaffRequestDao staffRequestDao = new StaffRequestDao();
            StaffRequestData staffRequestData = new StaffRequestData(
                staffData.getFullName(),
                staffData.getRestaurantName(),
                staffData.getPhoneNumber(),
                staffData.getEmail(),
                staffData.getUsername(),
                staffData.getPassword(),
                staffData.getOwnerId()
            );
            
            boolean pendingAdded = staffRequestDao.addPendingRequest(staffRequestData);

            if (pendingAdded) {
                JOptionPane.showMessageDialog(registerusernamepasswordView,
                    "Registration request submitted successfully!\n\n" +
                    "Your request is now pending admin approval.\n" +
                    "You will receive an email notification once it's processed.",
                    "Request Submitted", JOptionPane.INFORMATION_MESSAGE);
                
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
                close();
            } else {
                JOptionPane.showMessageDialog(registerusernamepasswordView,
                    "Failed to submit registration request. Please try again later.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }    
    }
    
    class RegisterCustomer implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerusernamepasswordView.getUsernameTextField().getText();
            String password = String.valueOf(registerusernamepasswordView.getPasswordField().getPassword());
            String confirmpassword = String.valueOf(registerusernamepasswordView.getConfirmPasswordField().getPassword());
            
            if(username.isEmpty() || username.equals("Username") ||password.isEmpty() || password.equals("Password") ||confirmpassword.isEmpty() || confirmpassword.equals("Confirm Password")) {
                JOptionPane.showMessageDialog(registerusernamepasswordView, "All fields are required!");
                return;
            }
            if(!password.equals(confirmpassword)){
                JOptionPane.showMessageDialog(registerusernamepasswordView, "Password not matched.Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 6) {
            JOptionPane.showMessageDialog(registerusernamepasswordView, "Password must be at least 6 characters long!");
            return;
            }
            
            customerData.setUsername(username);
            customerData.setPassword(password);
            
            boolean success = new CustomerDao().register(customerData);
            if (success){
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered successfully. Please Login to continue!");
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginController.open();
                close();
            } else {
                JOptionPane.showMessageDialog(registerusernamepasswordView,"Registered failed. Please try again!");
            }
            
        }
        
    }
}
