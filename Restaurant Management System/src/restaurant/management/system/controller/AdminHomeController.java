/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.dao.StaffRequestDao;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.model.StaffRequestData;
import restaurant.management.system.view.AdminHomeView;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.view.AdminProfileView;
import restaurant.management.system.view.LoginView;

/**
 *
 * @author pradeepta 3434
 */
public class AdminHomeController {
    private AdminHomeView adminHomeView = new AdminHomeView();
    private int currentOwnerId;
    private StaffRequestDao staffRequestDao;
    private StaffDao staffDao;

    public AdminHomeController(AdminHomeView view, int ownerId){
        this.adminHomeView = view;
        this.currentOwnerId = ownerId;
        this.staffRequestDao = new StaffRequestDao();
        this.staffDao = new StaffDao();
        
        this.adminHomeView.profileNavigation(new ProfileNav(adminHomeView.getProfilelabel()));
        this.adminHomeView.menuNavigation(new MenuNav (adminHomeView.getMenulabel()));
        this.adminHomeView.orderNavigation(new OrderNav (adminHomeView.getOrderlabel()));
        this.adminHomeView.logoutNavigation(new LogoutNav(adminHomeView.getLogoutlabel()));
        
        this.adminHomeView.getStaffButton().addActionListener(e -> 
        adminHomeView.getJTabbedPane().setSelectedIndex(AdminHomeView.STAFF_TAB_INDEX));

        this.adminHomeView.getCustomerButton().addActionListener(e -> 
        adminHomeView.getJTabbedPane().setSelectedIndex(AdminHomeView.CUSTOMER_TAB_INDEX));
        
        loadStaffRequests();
    }
    
    private void loadStaffRequests() {
        try {
            List<StaffRequestData> requests = staffRequestDao.getAllPendingRequests();
            adminHomeView.displayStaffRequests(requests);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminHomeView, 
                "Error loading staff requests: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void handleStaffRequest(StaffRequestData request, boolean approved, String adminEmail) {
        try {
            if (approved) {
                if (staffRequestDao.approveRequest(request.getRequestId(), adminEmail)) {
                    // Create staff account after approval
                    if (createStaffAccountFromRequest(request)) {
                        showSuccess("Staff approved! Temporary credentials sent to " + request.getEmail());
                    } else {
                        throw new Exception("Failed to create staff account");
                    }
                } else {
                    throw new Exception("Failed to approve request");
                }
            } else {
                if (staffRequestDao.rejectRequest(request.getRequestId(), adminEmail)) {
                    showSuccess("Request rejected");
                } else {
                    throw new Exception("Failed to reject request");
                }
            }
            loadStaffRequests();
        } catch (Exception e) {
            showError("Error processing request: " + e.getMessage());
        }
    }

    private boolean createStaffAccountFromRequest(StaffRequestData request) {
        try {
            // Generate temporary credentials
            String tempUsername = generateUsername(request.getEmail());
            String tempPassword = generateTemporaryPassword();

            // Create staff data object
            StaffData newStaff = new StaffData(
                request.getFullName(),
                request.getRestaurantName(),
                request.getPhoneNumber(),
                request.getEmail()
            );
            newStaff.setUsername(tempUsername);
            newStaff.setPassword(tempPassword);
            newStaff.setAccountStatus("ACTIVE");

            // Save to database
            return staffDao.register(newStaff);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateUsername(String email) {
        return email.split("@")[0] + "_" + System.currentTimeMillis() % 1000;
    }

    private String generateTemporaryPassword() {
        return "Temp@" + (int)(Math.random() * 10000);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(adminHomeView, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(adminHomeView, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void open(){
        this.adminHomeView .setVisible(true);
    }
    public void close(){
        this.adminHomeView .dispose();
    }

    class ProfileNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public ProfileNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminProfileView adminProfileView = new AdminProfileView();
            AdminProfileController adminProfileController= new AdminProfileController(adminProfileView, currentOwnerId);
            adminProfileController.open();
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
            profilelabel.setForeground(Color.white);
            profilelabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            profilelabel.setForeground(Color.black);
            profilelabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
}