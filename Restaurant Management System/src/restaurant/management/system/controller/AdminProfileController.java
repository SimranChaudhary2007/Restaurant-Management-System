/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.view.AdminAccountManagementView;
import restaurant.management.system.view.AdminProfileView;

/**
 *
 * @author acer
 */
public class AdminProfileController {
    private AdminProfileView adminProfileView = new AdminProfileView();
    private int currentOwnerId;
    public AdminProfileController(AdminProfileView view){
        this.adminProfileView  = view; 
        this.currentOwnerId = -1;
        this.adminProfileView.uploadImageButton(new UploadImage(adminProfileView.getUploadProfile()));
        this.adminProfileView.accountManagement(new AccounManagement(adminProfileView.getAccManagement()));
    }
    public void setCurrentOwnerId(int ownerId) {
        this.currentOwnerId = ownerId;
    }
    public void open(){
        this.adminProfileView .setVisible(true);
    }
    public void close(){
        this.adminProfileView .dispose();
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
            accMageIcon.setForeground(Color.red);
            accMageIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            accMageIcon.setForeground(Color.black);
            accMageIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }    
    class UploadImage implements MouseListener{
        
        private JLabel insertProfileIcon;
        
        public UploadImage(JLabel label){
            this.insertProfileIcon = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            
            // Set file filter for images only
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp"));
            
            int result = fileChooser.showOpenDialog(adminProfileView);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file.exists() && file.isFile()) {
                    try {
                        // Convert file to byte array
                        FileInputStream fis = new FileInputStream(file);
                        byte[] imageData = fis.readAllBytes();
                        fis.close();
                        
                        // Save to database using current owner ID
                        OwnerDao ownerDao = new OwnerDao();
                        boolean success = ownerDao.updateProfilePicture(currentOwnerId, imageData);
                        
                        if (success) {
                            // Display the image in the PanelRound
                            adminProfileView.displayProfileImage(imageData);
                            adminProfileView.selectedFile(file);
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "Profile picture updated successfully!", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "Failed to save profile picture.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(adminProfileView, 
                            "Error reading file: " + ex.getMessage(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(adminProfileView, 
                        "Invalid file selected.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
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
            insertProfileIcon.setForeground(Color.BLUE);
            insertProfileIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            insertProfileIcon.setForeground(Color.BLACK);
            insertProfileIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}  

