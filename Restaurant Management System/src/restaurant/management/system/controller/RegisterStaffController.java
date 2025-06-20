/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import restaurant.management.system.view.RegisterAsView;
import restaurant.management.system.view.RegisterStaffView;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.dao.StaffRequestDao;
import restaurant.management.system.model.StaffRequestData;
import restaurant.management.system.view.RegistrationOTPView;

/**
 *
 * @author acer
 */
public class RegisterStaffController {
    private  RegisterStaffView registerStaffView = new RegisterStaffView();
    private StaffRequestDao staffRequestDao;
    public RegisterStaffController(RegisterStaffView registerStaffView){
        this.registerStaffView = registerStaffView;
        this.staffRequestDao = new StaffRequestDao();
        this.registerStaffView.registerStaff(new RegisterStaff());
        this.registerStaffView.mainpage(new Mainpage());
        
        setPlaceholder(registerStaffView.getFullNameTextField(), "Full Name");
        setPlaceholder(registerStaffView.getRestaurantNameTextField(), "Restaurant Name");
        setPlaceholder(registerStaffView.getPhoneNumberTextField(), "Phone Number");
        setPlaceholder(registerStaffView.getEmailTextField(), "E-mail");
    }
    public void open(){
        this.registerStaffView.setVisible(true);
    }
    public void close(){
        this.registerStaffView.dispose();
    }
    
    private void setPlaceholder(javax.swing.JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(Color.GRAY);

    textField.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().trim().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    });
}

    class Mainpage implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterAsView registerAsView = new RegisterAsView();
            RegisterAsController registerAsController = new RegisterAsController(registerAsView);
            registerAsController.open();
            close();
        }
        
    }
    private boolean isPlaceholder(String text, String placeholder) {
        return text.equals(placeholder);
    }
    class RegisterStaff implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = registerStaffView.getFullNameTextField().getText();
            String restaurantName = registerStaffView.getRestaurantNameTextField().getText();
            String phoneNumber = registerStaffView.getPhoneNumberTextField().getText();
            String email = registerStaffView.getEmailTextField().getText();
            
            
            // VALIDATION
            if (fullName.isEmpty() || restaurantName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() ||
                isPlaceholder(fullName, "Full Name") || isPlaceholder(restaurantName, "Restaurant Name") ||
                isPlaceholder(phoneNumber, "Phone Number")  ||
                isPlaceholder(email, "E-mail")) {

                JOptionPane.showMessageDialog(registerStaffView, "All fields must be filled.");
                return;
            }
            if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email)) {
                JOptionPane.showMessageDialog(registerStaffView, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("^\\d{7,15}$", phoneNumber)) {
                JOptionPane.showMessageDialog(registerStaffView, "Please enter a valid phone number (7 to 15 digits).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            StaffDao staffDao = new StaffDao();
            if (staffDao.isEmailRegistered(email)) {
                JOptionPane.showMessageDialog(registerStaffView, "Email already exists in our system. Please use a different email.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (staffRequestDao.hasExistingPendingRequest(email)) {
                JOptionPane.showMessageDialog(registerStaffView, 
                    "A registration request for this email is already pending admin approval.\nPlease wait for the admin to process your request.", 
                    "Request Pending", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            StaffRequestData staffRequestData = new StaffRequestData(fullName, restaurantName, phoneNumber, email);
            
            if (staffRequestDao.addPendingRequest(staffRequestData)){
                String subject = "Registration Request Submitted - Pending Approval";
                String body = "Hello " + fullName + ",\n\n" +
                             "Thank you for your interest in joining Sajilo Serve Restaurant Management System as a Staff member.\n\n" +
                             "Your registration request has been submitted successfully and is now pending admin approval.\n\n" +
                             "Registration Details:\n" +
                             "- Full Name: " + fullName + "\n" +
                             "- Restaurant: " + restaurantName + "\n" +
                             "- Email: " + email + "\n" +
                             "- Phone: " + phoneNumber + "\n\n" +
                             "You will receive an email notification once your request has been reviewed by our admin team.\n" +
                             "This process typically takes 1-2 business days.\n\n" +
                             "If you have any questions, please contact our support team.\n\n" +
                             "Best regards,\nSajilo Serve Team";
                
                boolean mailSent = restaurant.management.system.controller.mail.SMTPSMailSender.sendMail(email, subject, body);
                
                if (mailSent) {
                    JOptionPane.showMessageDialog(registerStaffView, 
                        "Registration request submitted successfully!\n\n" +
                        "Your request is now pending admin approval.\n" +
                        "You will receive an email notification once it's processed.\n\n" +
                        "A confirmation email has been sent to: " + email, 
                        "Request Submitted", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(registerStaffView, 
                        "Registration request submitted successfully!\n\n" +
                        "Your request is now pending admin approval.\n" +
                        "However, we couldn't send the confirmation email.\n\n" +
                        "You will still receive notification once your request is processed.", 
                        "Request Submitted", JOptionPane.WARNING_MESSAGE);
                }
                
                clearForm();
                RegisterAsView registerAsView = new RegisterAsView();
                RegisterAsController registerAsController = new RegisterAsController(registerAsView);
                registerAsController.open();
                close();
                
            } else {
                JOptionPane.showMessageDialog(registerStaffView, 
                    "Failed to submit registration request. Please try again later.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void clearForm() {
            registerStaffView.getFullNameTextField().setText("Full Name");
            registerStaffView.getFullNameTextField().setForeground(Color.GRAY);
            
            registerStaffView.getRestaurantNameTextField().setText("Restaurant Name");
            registerStaffView.getRestaurantNameTextField().setForeground(Color.GRAY);
            
            registerStaffView.getPhoneNumberTextField().setText("Phone Number");
            registerStaffView.getPhoneNumberTextField().setForeground(Color.GRAY);
            
            registerStaffView.getEmailTextField().setText("E-mail");
            registerStaffView.getEmailTextField().setForeground(Color.GRAY);
        }
    }
}