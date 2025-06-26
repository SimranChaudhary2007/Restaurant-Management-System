/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.view.AdminAccountManagementView;
import restaurant.management.system.view.CustomerHomeView;
import restaurant.management.system.view.CustomerOrderView;
import restaurant.management.system.view.CustomerProfileView;
import restaurant.management.system.view.LoginView;


/**
 *
 * @author pradeepta 3434
 */
public class CustomerProfileController {
    private CustomerProfileView customerProfileView = new CustomerProfileView();
    private CustomerData currentCustomerData;
    private int currentCustomerId;
    private CustomerDao customerDao = new CustomerDao();
    
    private String originalFullName = "";
    private String originalAddress = "";
    private String originalPhoneNumber = "";
    private String originalEmail = "";
    
    public CustomerProfileController(CustomerProfileView view, CustomerData customerData){
        this.customerProfileView = view; 
        this.currentCustomerData = customerData;
        this.currentCustomerId = customerData.getId();
        this.customerProfileView.homeNavigation(new HomeNav(customerProfileView.getHomelabel()));
        this.customerProfileView.orderNavigation(new OrderNav (customerProfileView.getOrderlabel()));
        this.customerProfileView.billsNavigation(new BillsNav (customerProfileView.getBillslabel()));
        this.customerProfileView.logoutNavigation(new LogoutNav(customerProfileView.getLogoutlabel()));
        this.customerProfileView.uploadProfileImageButton(new UploadProfielImage(customerProfileView.getUploadProfile()));
        this.customerProfileView.accountManagement(new AccounManagement(customerProfileView.getAccManagement()));
        
        this.customerProfileView.setUpdateButtonAction(e -> handleUpdateProfile());
        
        loadData(customerData);
        loadExistingProfilePicture();
    }
        
    private void loadData(CustomerData customer) {
        if (customer != null) {
            originalFullName = customer.getFullName() != null ? customer.getFullName() : "";
            originalAddress = customer.getAddress() != null ? customer.getAddress() : "";
            originalPhoneNumber = customer.getPhoneNumber() != null ? customer.getPhoneNumber() : "";
            originalEmail = customer.getEmail() != null ? customer.getEmail() : "";

            customerProfileView.getNameTextField().setText(originalFullName);
            customerProfileView.getCustomerAddressTextField().setText(originalAddress);
            customerProfileView.getPhoneNumberTextField().setText(originalPhoneNumber);
            customerProfileView.getEmailAddressTextField().setText(originalEmail);
            customerProfileView.getCustomerName().setText(originalFullName);
            String greeting = "" + originalFullName + ", enjoy your meal " ;
            customerProfileView.getCustomerName1().setText(greeting);
        } else {
            JOptionPane.showMessageDialog(customerProfileView, 
                "Unable to load customer data. Please login again.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
            
    private void handleUpdateProfile() {
        if (currentCustomerId == -1) {
            JOptionPane.showMessageDialog(customerProfileView, 
                "Error: customer ID not set. Please login again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String fullName = customerProfileView.getNameTextField().getText().trim();
        String customerAddress = customerProfileView.getCustomerAddressTextField().getText().trim();
        String phoneNumber = customerProfileView.getPhoneNumberTextField().getText().trim();
        String email = customerProfileView.getEmailAddressTextField().getText().trim();
        
        if (fullName.isEmpty() || customerAddress.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(customerProfileView, 
                "Please fill in all fields", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean hasChanges = !fullName.equals(originalFullName) ||
                           !customerAddress.equals(originalAddress) ||
                           !phoneNumber.equals(originalPhoneNumber) ||
                           !email.equals(originalEmail);
        
        if (!hasChanges) {
            JOptionPane.showMessageDialog(customerProfileView, 
                "No changes made to update.", 
                "Message", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        boolean success = customerDao.updateCustomerProfile(currentCustomerId, fullName, customerAddress, phoneNumber, email);
        
        if (success) {
            originalFullName = fullName;
            originalAddress = customerAddress;
            originalPhoneNumber = phoneNumber;
            originalEmail = email;
            String greeting = " " + fullName + ", enjoy your meal";
            customerProfileView.getCustomerName1().setText(greeting);
            customerProfileView.getCustomerName().setText(fullName);
            JOptionPane.showMessageDialog(customerProfileView, 
                "Profile updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(customerProfileView, 
                "Failed to update profile. Please try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setCurrentCustomerId(int customerId) {
        this.currentCustomerId = customerId;
        loadExistingProfilePicture();
    }
    private void loadExistingProfilePicture() {
        if (currentCustomerId != -1) {
            try {
                byte[] existingProfilePicture = customerDao.getProfilePicture(currentCustomerId);
                if (existingProfilePicture != null && existingProfilePicture.length > 0) {
                    displayProfileImageInView(existingProfilePicture);
                } else {
                    customerProfileView.setDefaultProfileImage();
                }
            } catch (Exception e) {
            }
        }
    }
    
    private void displayProfileImageInView(byte[] imageData) {
        try {
            if (imageData != null && imageData.length > 0) {
                ImageIcon originalIcon = new ImageIcon(imageData);
                
                int labelWidth = 130;
                int labelHeight = 130;
                
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                    labelWidth, labelHeight, Image.SCALE_SMOOTH);
                
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                
                customerProfileView.displayProfileImage(imageData);
               
            }
        } catch (Exception e) {
    
        }
    }
    
    public void open(){
        this.customerProfileView .setVisible(true);
    }
    public void close(){
        this.customerProfileView .dispose();
    }
    
    class HomeNav implements MouseListener{
        
        private JLabel homelabel;
        
        public HomeNav(JLabel label) {
            this.homelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            CustomerHomeView customerHomeView = new CustomerHomeView();
            CustomerHomeController customerHomeController= new CustomerHomeController(customerHomeView,  currentCustomerData);
            customerHomeController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            homelabel.setForeground(Color.white);
            homelabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            homelabel.setForeground(Color.black);
            homelabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    class OrderNav implements MouseListener{
        
        private JLabel orderlabel;
        
        public OrderNav(JLabel label) {
            this.orderlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            CustomerOrderView customerOrderView = new CustomerOrderView();
            CustomerOrderController customerOrderController= new CustomerOrderController(customerOrderView,  currentCustomerId);
            customerOrderController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            orderlabel.setForeground(Color.white);
            orderlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            orderlabel.setForeground(Color.black);
            orderlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class BillsNav implements MouseListener{
        
        private JLabel billlabel;
        
        public BillsNav(JLabel label) {
            this.billlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            billlabel.setForeground(Color.white);
            billlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            billlabel.setForeground(Color.black);
            billlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class LogoutNav implements MouseListener{
        
        private JLabel logoutlabel;
        
        public LogoutNav(JLabel label) {
            this.logoutlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to logout?", "Logout Confirmation",
                JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
            JFrame customerProfileView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            customerProfileView.dispose();

            LoginView loginView = new LoginView();
            LoginController loginController= new LoginController(loginView);
            loginController.open();
            close();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            logoutlabel.setForeground(Color.WHITE);
            logoutlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            logoutlabel.setForeground(Color.BLACK);
            logoutlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
    
    //Profile Picture
    class UploadProfielImage implements MouseListener{
        
        private JLabel insertProfileIcon;
        
        public UploadProfielImage(JLabel label){
            this.insertProfileIcon = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentCustomerId == -1) {
                JOptionPane.showMessageDialog(customerProfileView, 
                    "Error: Customer ID not set. Please login again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)", 
                "jpg", "jpeg", "png", "gif", "bmp");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            
            int result = fileChooser.showOpenDialog(customerProfileView);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                if (file != null && file.exists() && file.isFile()) {
                    try {
                        long fileSize = file.length();
                        if (fileSize > 5 * 1024 * 1024) {
                            JOptionPane.showMessageDialog(customerProfileView, 
                                "File size too large. Please select an image smaller than 5MB.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        byte[] imageData = Files.readAllBytes(file.toPath());
                        
                        
                        CustomerDao customerDao = new CustomerDao();
                        boolean success = customerDao.updateProfilePicture(currentCustomerId, imageData);
                        
                        if (success) {
                            displayImageInView(imageData);
                            customerProfileView.selectedProfileFile(file);
                            
                            JOptionPane.showMessageDialog(customerProfileView, 
                                "Profile picture updated successfully!", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(customerProfileView, 
                                "Failed to save profile picture to database.", 
                                "Database Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(customerProfileView, 
                            "Error reading file: " + ex.getMessage(), 
                            "File Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(customerProfileView, 
                        "Invalid file selected or file does not exist.", 
                        "File Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void displayImageInView(byte[] imageData) {
            try {
                if (imageData != null && imageData.length > 0) {
                    ImageIcon originalIcon = new ImageIcon(imageData);
                    
                    int labelWidth = 130;
                    int labelHeight = 130;
                    
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                        labelWidth, labelHeight, Image.SCALE_SMOOTH);
                    
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    
                    customerProfileView.displayProfileImage(imageData);
                    
                } 
            } catch (Exception e) {
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            insertProfileIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            insertProfileIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class AccounManagement implements MouseListener{
        
        private JLabel accMageIcon;
        
        public AccounManagement(JLabel label) {
            this.accMageIcon = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminAccountManagementView adminAccountManagementView = new AdminAccountManagementView();
//            AdminAccountManagementController adminAccountManagementController= new AdminAccountManagementController(adminAccountManagementView);
//            adminAccountManagementController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            accMageIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            accMageIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}

    

