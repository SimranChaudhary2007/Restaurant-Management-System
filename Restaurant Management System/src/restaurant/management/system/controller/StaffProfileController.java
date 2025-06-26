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
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.StaffProfileView;
import restaurant.management.system.view.StaffHomeView;

/**
 *
 * @author pradeepta 3434
 */
public class StaffProfileController {
    private StaffProfileView staffProfileView = new StaffProfileView();
    private int currentStaffId;
    private int currentOwnerId;
    private StaffDao staffDao = new StaffDao();
    
    private String originalFullName = "";
    private String originalRestaurantName = "";
    private String originalPhoneNumber = "";
    private String originalEmail = "";
    
    
    public StaffProfileController(StaffProfileView view, int staffId){
        this.staffProfileView = view; 
        this.currentStaffId = staffId;
        
        this.staffProfileView.homeNavigation(new HomeNav(staffProfileView.getHomelabel()));
        this.staffProfileView.menuNavigation(new MenuNav(staffProfileView.getMenulabel()));
        this.staffProfileView.orderNavigation(new OrderNav(staffProfileView.getOrderlabel()));
        this.staffProfileView.logoutNavigation(new LogoutNav(staffProfileView.getLogoutlabel()));
        this.staffProfileView.accountManagement(new AccounManagement(staffProfileView.getAccManagement()));

        this.staffProfileView.uploadProfileImageButton(new UploadProfielImage(staffProfileView.getUploadProfile()));
        this.staffProfileView.setUpdateButtonAction(e -> handleUpdateProfile());
        
        loadStaffData();  
        loadExistingProfilePicture();
    }
    
    private void loadStaffData() {
        if (currentStaffId != -1) {
            StaffData staff = staffDao.getStaffById(currentStaffId);
            if (staff != null) {
                originalFullName = staff.getFullName() != null ? staff.getFullName() : "";
                originalRestaurantName = staff.getRestaurantName() != null ? staff.getRestaurantName() : "";
                originalPhoneNumber = staff.getPhoneNumber() != null ? staff.getPhoneNumber() : "";
                originalEmail = staff.getEmail() != null ? staff.getEmail() : "";
                
                staffProfileView.getNameTextField().setText(originalFullName);
                staffProfileView.getRestaurantNameTextField().setText(originalRestaurantName);
                staffProfileView.getPhoneNumberTextField().setText(originalPhoneNumber);
                staffProfileView.getEmailAddressTextField().setText(originalEmail);
                staffProfileView.getStaffName().setText(originalFullName);
                staffProfileView.getAdminResturantName().setText(originalRestaurantName);
                String greeting = "" + originalFullName + " Working on " ;
                staffProfileView.getStaffName1().setText(greeting);
            } else {
                JOptionPane.showMessageDialog(staffProfileView, 
                    "Unable to load staff data. Please try logging in again.", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
            }
       }
    }
    
    private void handleUpdateProfile() {
        if (currentStaffId == -1) {
            JOptionPane.showMessageDialog(staffProfileView, 
                "Error: Staff ID not set. Please login again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String fullName = staffProfileView.getNameTextField().getText().trim();
        String restaurantName = staffProfileView.getRestaurantNameTextField().getText().trim();
        String phoneNumber = staffProfileView.getPhoneNumberTextField().getText().trim();
        String email = staffProfileView.getEmailAddressTextField().getText().trim();
        
        if (fullName.isEmpty() || restaurantName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(staffProfileView, 
                "Please fill in all fields", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean hasChanges = !fullName.equals(originalFullName) ||
                           !restaurantName.equals(originalRestaurantName) ||
                           !phoneNumber.equals(originalPhoneNumber) ||
                           !email.equals(originalEmail);
        
        if (!hasChanges) {
            JOptionPane.showMessageDialog(staffProfileView, 
                "No changes made to update.", 
                "Message", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        boolean success = staffDao.updateStaffProfile(currentStaffId, fullName, restaurantName, phoneNumber, email);
        
        if (success) {
            originalFullName = fullName;
            originalRestaurantName = restaurantName;
            originalPhoneNumber = phoneNumber;
            originalEmail = email;
            String greeting = "" + fullName + " Working on ";
            staffProfileView.getStaffName1().setText(greeting);
            
            
            staffProfileView.getAdminResturantName().setText(restaurantName);
            staffProfileView.getStaffName().setText(fullName);
            JOptionPane.showMessageDialog(staffProfileView, 
                "Profile updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(staffProfileView, 
                "Failed to update profile. Please try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
 
    public void setCurrentStaffId(int staffId) {
        this.currentStaffId = staffId;
        loadStaffData();
        loadExistingProfilePicture();
    }
    
    private void loadExistingProfilePicture() {
        if (currentStaffId != -1) {
            try {
                byte[] existingProfilePicture = staffDao.getProfilePicture(currentStaffId);
                if (existingProfilePicture != null && existingProfilePicture.length > 0) {
                    displayProfileImageInView(existingProfilePicture);
                } else {
                    staffProfileView.setDefaultProfileImage();
                }
            } catch (Exception e) {
                staffProfileView.setDefaultProfileImage();
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
                
                staffProfileView.displayProfileImage(imageData);
               
            }
        } catch (Exception ex) {
    
        }
    }
    
    
    public void open(){
        this.staffProfileView .setVisible(true);
    }
    public void close(){
        this.staffProfileView .dispose();
    }
    
    class HomeNav implements MouseListener{
        
        private JLabel homelabel;
        
        public HomeNav(JLabel label) {
            this.homelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            StaffHomeView staffHomeView = new StaffHomeView();
            StaffHomeController staffHomeController= new StaffHomeController(staffHomeView, currentStaffId, currentOwnerId);
            staffHomeController.open();
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
//            StaffMenuView staffMenuView = new StaffMenuView();
//            StaffMenuController staffMenuController= new StaffMenuController(staffMenuView);
//            staffMenuController.open();
//            close();
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
            restaurant.management.system.view.StaffOrdersView staffOrdersView = new restaurant.management.system.view.StaffOrdersView();
            restaurant.management.system.controller.StaffOrdersController staffOrdersController = new restaurant.management.system.controller.StaffOrdersController(staffOrdersView, currentStaffId);
            staffOrdersController.open();
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
            JFrame staffProfileView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            staffProfileView.dispose();

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

    class AccounManagement implements MouseListener{
        
        private JLabel accMageIcon;
        
        public AccounManagement(JLabel label) {
            this.accMageIcon = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
//            AdminAccountManagementView adminAccountManagementView = new AdminAccountManagementView();
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
    
    //Profile Picture
    class UploadProfielImage implements MouseListener{
        
        private JLabel insertProfileIcon;
        
        public UploadProfielImage(JLabel label){
            this.insertProfileIcon = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentStaffId == -1) {
                JOptionPane.showMessageDialog(staffProfileView, 
                    "Error: Staff ID not set. Please login again.", 
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
            
            int result = fileChooser.showOpenDialog(staffProfileView);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                if (file != null && file.exists() && file.isFile()) {
                    try {
                        long fileSize = file.length();
                        if (fileSize > 5 * 1024 * 1024) {
                            JOptionPane.showMessageDialog(staffProfileView, 
                                "File size too large. Please select an image smaller than 5MB.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        byte[] imageData = Files.readAllBytes(file.toPath());
                        
                        
                        StaffDao staffDao = new StaffDao();
                        boolean success = staffDao.updateProfilePicture(currentStaffId, imageData);
                        
                        if (success) {
                            displayImageInView(imageData);
                            staffProfileView.selectedProfileFile(file);
                            
                            JOptionPane.showMessageDialog(staffProfileView, 
                                "Profile picture updated successfully!", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(staffProfileView, 
                                "Failed to save profile picture to database.", 
                                "Database Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(staffProfileView, 
                            "Error reading file: " + ex.getMessage(), 
                            "File Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(staffProfileView, 
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
                    
                    staffProfileView.displayProfileImage(imageData);
                    
                } 
            } catch (Exception ex) {
            }
        }
        public void openStaffProfile(int staffId) {
            StaffProfileView view = new StaffProfileView();
            StaffProfileController controller = new StaffProfileController(view, staffId);
            controller.open();
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
}