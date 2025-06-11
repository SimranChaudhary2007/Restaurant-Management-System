/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.view.AdminAccountManagementView;
import restaurant.management.system.view.CustomerProfileView;


/**
 *
 * @author pradeepta 3434
 */
public class CustomerProfileController {
    private CustomerProfileView customerProfileView = new CustomerProfileView();
    private int currentCustomerId;
    private CustomerDao customerDao = new CustomerDao();
    
    public CustomerProfileController(CustomerProfileView view, int customerId){
        this.customerProfileView = view; 
        this.currentCustomerId = customerId;
        this.customerProfileView.uploadProfileImageButton(new UploadProfielImage(customerProfileView.getUploadProfile()));
        this.customerProfileView.accountManagement(new AccounManagement(customerProfileView.getAccManagement()));
        
        loadExistingProfilePicture();
        
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
        } catch (Exception ex) {
    
        }
    }
    
    public void open(){
        this.customerProfileView .setVisible(true);
    }
    public void close(){
        this.customerProfileView .dispose();
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
            } catch (Exception ex) {
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
            AdminAccountManagementController adminAccountManagementController= new AdminAccountManagementController(adminAccountManagementView);
            adminAccountManagementController.open();
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

    

