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
import restaurant.management.system.controller.mail.SMTPSMailSender;
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
        
        // Set up approve/reject listeners for staff requests
        this.adminHomeView.setApproveListener(e -> {
            // Use SwingUtilities.invokeLater to handle UI updates properly
            SwingUtilities.invokeLater(() -> {
                StaffRequestData request = (StaffRequestData) e.getSource();
                handleStaffApproval(request, true);
            });
        });

        this.adminHomeView.setRejectListener(e -> {
            // Use SwingUtilities.invokeLater to handle UI updates properly
            SwingUtilities.invokeLater(() -> {
                StaffRequestData request = (StaffRequestData) e.getSource();
                handleStaffApproval(request, false);
            });
        });

        loadStaffRequests();
    }
    
    private void loadStaffRequests() {
        try {
            List<StaffRequestData> requests = staffRequestDao.getAllPendingRequests(currentOwnerId);
            adminHomeView.displayStaffRequests(requests);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminHomeView, 
                "Error loading staff requests: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleStaffApproval(StaffRequestData request, boolean approved) {
        try {
            if (approved) {
                // First mark the request as approved
                boolean requestApproved = staffRequestDao.approveRequest(request.getRequestId(), "admin");
                if (!requestApproved) {
                    throw new Exception("Failed to approve request in database");
                }

                // Send approval email
                new Thread(() -> {
                    try {
                        String subject = "Your Staff Registration Has Been Approved!";
                        String body = "Hello " + request.getFullName() + ",\n\n" +
                            "Congratulations! Your staff registration has been approved.\n\n" +
                            "Login Credentials:\n" +
                            "Username: " + request.getUsername() + "\n" +
                            "Password: " + request.getPassword() + "\n\n" +
                            "Please change your password after first login.\n\n" +
                            "Best regards,\nRestaurant Management System";

                        SMTPSMailSender.sendMail(request.getEmail(), subject, body);

                        SwingUtilities.invokeLater(() -> {
                            showSuccess("Staff approved successfully! Login credentials sent to " + request.getEmail());
                            adminHomeView.removeStaffRequestCard(request.getRequestId());
                        });
                    } catch (Exception emailEx) {
                        SwingUtilities.invokeLater(() -> {
                            showError("Staff approved but failed to send email: " + emailEx.getMessage());
                            adminHomeView.removeStaffRequestCard(request.getRequestId());
                        });
                    }
                }).start();
            } else {
                // Reject the request
                boolean requestRejected = staffRequestDao.rejectRequest(request.getRequestId(), "admin");
                if (requestRejected) {
                    // Send rejection email
                    new Thread(() -> {
                        try {
                            String subject = "Staff Registration Request - Update";
                            String body = "Hello " + request.getFullName() + ",\n\n" +
                                "Your staff registration request has been reviewed and unfortunately rejected.\n\n" +
                                "Please contact the restaurant administrator for more information or to submit a new request.\n\n" +
                                "Best regards,\nRestaurant Management System";

                            SMTPSMailSender.sendMail(request.getEmail(), subject, body);

                            SwingUtilities.invokeLater(() -> {
                                showSuccess("Request rejected. Notification email sent to " + request.getEmail());
                                adminHomeView.removeStaffRequestCard(request.getRequestId());
                            });
                        } catch (Exception emailEx) {
                            SwingUtilities.invokeLater(() -> {
                                showError("Request rejected but failed to send email: " + emailEx.getMessage());
                                adminHomeView.removeStaffRequestCard(request.getRequestId());
                            });
                        }
                    }).start();
                } else {
                    throw new Exception("Failed to reject request in database");
                }
            }
        } catch (Exception e) {
            showError("Error processing request: " + e.getMessage());
            e.printStackTrace();
        }
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