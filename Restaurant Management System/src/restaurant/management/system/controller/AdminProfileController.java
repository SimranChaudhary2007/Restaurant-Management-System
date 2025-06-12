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
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.view.AdminAccountManagementView;
import restaurant.management.system.view.AdminHomeView;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.view.AdminProfileView;
import restaurant.management.system.view.LoginView;

/**
 *
 * @author acer
 */
public class AdminProfileController {
    private AdminProfileView adminProfileView = new AdminProfileView();
    private int currentOwnerId;
    private OwnerDao ownerDao = new OwnerDao();
    
    public AdminProfileController(AdminProfileView view, int ownerId){
        this.adminProfileView = view; 
        this.currentOwnerId = ownerId;
        this.adminProfileView.homeNavigation(new HomeNav(adminProfileView.getProfilelabel()));
        this.adminProfileView.menuNavigation(new MenuNav (adminProfileView.getMenulabel()));
        this.adminProfileView.orderNavigation(new OrderNav (adminProfileView.getOrderlabel()));
        this.adminProfileView.logoutNavigation(new LogoutNav(adminProfileView.getLogoutlabel()));
        this.adminProfileView.uploadProfileImageButton(new UploadProfielImage(adminProfileView.getUploadProfile()));
        this.adminProfileView.uploadRestroImageButton(new UploadRestaurantImage(adminProfileView.getUploadRestaurant()));
        this.adminProfileView.accountManagement(new AccounManagement(adminProfileView.getAccManagement()));
        
        loadExistingProfilePicture();
        loadExistingRestaurantPicture();
        
    }
    
    class HomeNav implements MouseListener{
        
        private JLabel homelabel;
        
        public HomeNav(JLabel label) {
            this.homelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminHomeView adminHomeView = new AdminHomeView();
            AdminHomeController adminHomeController= new AdminHomeController(adminHomeView, currentOwnerId);
            adminHomeController.open();
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
    
    class MenuNav implements MouseListener{
        
        private JLabel menulabel;
        
        public MenuNav(JLabel label) {
            this.menulabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminMenuView adminMenuView = new AdminMenuView();
            AdminMenuController adminMenuController= new AdminMenuController(adminMenuView);
            adminMenuController.open();
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
            menulabel.setForeground(Color.white);
            menulabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            menulabel.setForeground(Color.black);
            menulabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class OrderNav implements MouseListener{
        
        private JLabel orderlabel;
        
        public OrderNav(JLabel label) {
            this.orderlabel = label;
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
            orderlabel.setForeground(Color.white);
            orderlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            orderlabel.setForeground(Color.black);
            orderlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
            JFrame adminHomeView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            adminHomeView.dispose();

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

    public void setCurrentOwnerId(int ownerId) {
        this.currentOwnerId = ownerId;
        loadExistingProfilePicture();
        loadExistingRestaurantPicture();
    }
    private void loadExistingProfilePicture() {
        if (currentOwnerId != -1) {
            try {
                byte[] existingProfilePicture = ownerDao.getProfilePicture(currentOwnerId);
                if (existingProfilePicture != null && existingProfilePicture.length > 0) {
                    displayProfileImageInView(existingProfilePicture);
                } else {
                    adminProfileView.setDefaultProfileImage();
                }
            } catch (Exception e) {
            }
        }
    }
    private void loadExistingRestaurantPicture() {
        if (currentOwnerId != -1) {
            try {
                byte[] existingRestaurantPicture = ownerDao.getRestaurantPicture(currentOwnerId);
                if (existingRestaurantPicture != null && existingRestaurantPicture.length > 0) {
                    displayRestaurantImageInView(existingRestaurantPicture);
                } else {
                    adminProfileView.setDefaultRestaurantImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                
                adminProfileView.displayProfileImage(imageData);
               
            }
        } catch (Exception ex) {
    
        }
    }
    private void displayRestaurantImageInView(byte[] imageData) {
        try {
            if (imageData != null && imageData.length > 0) {
                ImageIcon originalIcon = new ImageIcon(imageData);
                
                int labelWidth = 250;
                int labelHeight = 160;
                
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                    labelWidth, labelHeight, Image.SCALE_SMOOTH);
                
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                
                adminProfileView.displayRestaurantImage(imageData);
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void open(){
        this.adminProfileView .setVisible(true);
    }
    public void close(){
        this.adminProfileView .dispose();
    }
    
    //Profile Picture
    class UploadProfielImage implements MouseListener{
        
        private JLabel insertProfileIcon;
        
        public UploadProfielImage(JLabel label){
            this.insertProfileIcon = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentOwnerId == -1) {
                JOptionPane.showMessageDialog(adminProfileView, 
                    "Error: Owner ID not set. Please login again.", 
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
            
            int result = fileChooser.showOpenDialog(adminProfileView);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                if (file != null && file.exists() && file.isFile()) {
                    try {
                        long fileSize = file.length();
                        if (fileSize > 5 * 1024 * 1024) {
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "File size too large. Please select an image smaller than 5MB.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        byte[] imageData = Files.readAllBytes(file.toPath());
                        
                        
                        OwnerDao ownerDao = new OwnerDao();
                        boolean success = ownerDao.updateProfilePicture(currentOwnerId, imageData);
                        
                        if (success) {
                            displayImageInView(imageData);
                            adminProfileView.selectedProfileFile(file);
                            
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "Profile picture updated successfully!", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "Failed to save profile picture to database.", 
                                "Database Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(adminProfileView, 
                            "Error reading file: " + ex.getMessage(), 
                            "File Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(adminProfileView, 
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
                    
                    adminProfileView.displayProfileImage(imageData);
                    
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

    //Restaurant picture
    class UploadRestaurantImage implements MouseListener{
        
        private JLabel insertRestroIcon;
        
        public UploadRestaurantImage(JLabel label){
            this.insertRestroIcon = label;
        } 

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentOwnerId == -1) {
                JOptionPane.showMessageDialog(adminProfileView, 
                    "Error: Owner ID not set. Please login again.", 
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
            
            int result = fileChooser.showOpenDialog(adminProfileView);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                if (file != null && file.exists() && file.isFile()) {
                    try {
                        long fileSize = file.length();
                        if (fileSize > 20 * 1024 * 1024) {
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "File size too large. Please select an image smaller than 20MB.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
 
                        byte[] imageData = Files.readAllBytes(file.toPath());
                        
                        OwnerDao ownerDao = new OwnerDao();
                        boolean success = ownerDao.updateRestaurantPicture(currentOwnerId, imageData);
                        
                        if (success) {
                            displayRestaurantImageInView(imageData);
                            adminProfileView.selectedRestaurantFile(file);
                            
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "Restaurant picture updated successfully!", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(adminProfileView, 
                                "Failed to save restaurant picture to database.", 
                                "Database Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(adminProfileView, 
                            "Error reading file: " + ex.getMessage(), 
                            "File Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(adminProfileView, 
                        "Invalid file selected or file does not exist.", 
                        "File Error", 
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
            insertRestroIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            insertRestroIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    //Profile update
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
}      

