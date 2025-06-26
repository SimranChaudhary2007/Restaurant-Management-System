/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import restaurant.management.system.controller.mail.SMTPSMailSender;
import restaurant.management.system.dao.NoticeDao;
import restaurant.management.system.dao.StaffDao;
import restaurant.management.system.dao.StaffRequestDao;
import restaurant.management.system.dao.SuggestionDao;
import restaurant.management.system.model.NoticeData;
import restaurant.management.system.model.StaffRequestData;
import restaurant.management.system.model.SuggestionData;
import restaurant.management.system.view.AdminAnalysisView;
import restaurant.management.system.view.AdminHomeView;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.view.AdminOrdersView;
import restaurant.management.system.view.AdminProfileView;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.StaffInfoView;

/**
 *
 * @author pradeepta 3434
 */
public class AdminHomeController {
    private AdminHomeView adminHomeView = new AdminHomeView();
    private int currentOwnerId;
    private StaffRequestDao staffRequestDao;
    private StaffDao staffDao;
    private SuggestionDao suggestionDao;
    private NoticeDao noticeDao;

    public AdminHomeController(AdminHomeView view, int ownerId){
        this.adminHomeView = view;
        this.currentOwnerId = ownerId;
        this.staffRequestDao = new StaffRequestDao();
        this.staffDao = new StaffDao();
        this.suggestionDao = new SuggestionDao();
        this.noticeDao = new NoticeDao();
        
        this.adminHomeView.analysisNavigation(new AnalysisNav());
        this.adminHomeView.staffInfoNavigation(new StaffInfoNav());
        this.adminHomeView.suggestionNavigation(new SuggestionButtonListener());
        this.adminHomeView.noticeNavigation(new NoticeNav());
        this.adminHomeView.profileNavigation(new ProfileNav(adminHomeView.getProfilelabel()));
        this.adminHomeView.menuNavigation(new MenuNav (adminHomeView.getMenulabel()));
        this.adminHomeView.orderNavigation(new OrderNav (adminHomeView.getOrderlabel()));
        this.adminHomeView.logoutNavigation(new LogoutNav(adminHomeView.getLogoutlabel()));
        
        this.adminHomeView.getStaffButton().addActionListener(e -> 
        adminHomeView.getJTabbedPane().setSelectedIndex(AdminHomeView.STAFF_TAB_INDEX));

        this.adminHomeView.getCustomerButton().addActionListener(e -> 
        adminHomeView.getJTabbedPane().setSelectedIndex(AdminHomeView.CUSTOMER_TAB_INDEX));
        this.adminHomeView.setApproveListener(e -> {
            SwingUtilities.invokeLater(() -> {
                StaffRequestData request = (StaffRequestData) e.getSource();
                handleStaffApproval(request, true);
            });
        });

        this.adminHomeView.setRejectListener(e -> {
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
                boolean requestApproved = staffRequestDao.approveRequest(request.getRequestId(), "admin");
                if (!requestApproved) {
                    throw new Exception("Failed to approve request in database");
                }

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
                boolean requestRejected = staffRequestDao.rejectRequest(request.getRequestId(), "admin");
                if (requestRejected) {
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

    class StaffInfoNav implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            StaffInfoView staffInfoView = new StaffInfoView();
            StaffInfoController staffInfoController = new StaffInfoController(staffInfoView, currentOwnerId);
            staffInfoController.open();
            close();
        }
        
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
        AdminMenuController adminMenuController = new AdminMenuController(adminMenuView, currentOwnerId);
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
            AdminOrdersView adminOrdersView = new AdminOrdersView();
            AdminOrdersController adminOrdersController = new AdminOrdersController(adminOrdersView, currentOwnerId);
            adminOrdersController.open();
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
        
    class SuggestionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                 List<SuggestionData> suggestions = suggestionDao.getSuggestionsByOwner(currentOwnerId);
                
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());
                mainPanel.setBackground(new Color(241, 237, 238));
                
                JPanel headerPanel = new JPanel();
                headerPanel.setBackground(new Color(227, 143, 11));
                headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                JLabel titleLabel = new JLabel("Customer Suggestions");
                titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
                titleLabel.setForeground(Color.WHITE);
                headerPanel.add(titleLabel);
                mainPanel.add(headerPanel, BorderLayout.NORTH);
                
                JPanel cardsPanel = new JPanel();
                cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
                cardsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                cardsPanel.setBackground(new Color(241, 237, 238));
                
                if (suggestions.isEmpty()) {
                    JLabel noSuggestionLabel = new JLabel("No suggestions found.");
                    noSuggestionLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 18));
                    noSuggestionLabel.setForeground(new Color(100, 100, 100));
                    noSuggestionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    cardsPanel.add(Box.createVerticalGlue());
                    cardsPanel.add(noSuggestionLabel);
                    cardsPanel.add(Box.createVerticalGlue());
                } else {
                    for (SuggestionData suggestion : suggestions) {
                        JPanel cardPanel = new JPanel();
                        cardPanel.setLayout(new BorderLayout());
                        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                        cardPanel.setBackground(Color.WHITE);
                        cardPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
                        
                        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
                        infoPanel.setBackground(Color.WHITE);
                        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                        
                        JLabel customerLabel = new JLabel("Customer: " + suggestion.getCustomerName());
                        customerLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 16));
                        customerLabel.setForeground(new Color(70, 70, 70));
                        
                        JLabel restaurantLabel = new JLabel("Restaurant: " + suggestion.getRestaurantName());
                        restaurantLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
                        restaurantLabel.setForeground(new Color(100, 100, 100));
                        
                        infoPanel.add(customerLabel);
                        infoPanel.add(restaurantLabel);
                        cardPanel.add(infoPanel, BorderLayout.NORTH);
                        
                        JTextArea suggestionText = new JTextArea(suggestion.getSuggestionText());
                        suggestionText.setLineWrap(true);
                        suggestionText.setWrapStyleWord(true);
                        suggestionText.setEditable(false);
                        suggestionText.setBackground(Color.WHITE);
                        suggestionText.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
                        suggestionText.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
                        
                        JScrollPane textScroll = new JScrollPane(suggestionText);
                        textScroll.setBorder(BorderFactory.createEmptyBorder());
                        textScroll.setBackground(Color.WHITE);
                        cardPanel.add(textScroll, BorderLayout.CENTER);
                        
                        JLabel timeLabel = new JLabel("Received: " + suggestion.getCreatedAt().toString());
                        timeLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 12));
                        timeLabel.setForeground(new Color(150, 150, 150));
                        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
                        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
                        cardPanel.add(timeLabel, BorderLayout.SOUTH);
                        
                        cardsPanel.add(cardPanel);
                        cardsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                    }
                }
                
                JScrollPane scrollPane = new JScrollPane(cardsPanel);
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                scrollPane.getViewport().setBackground(new Color(241, 237, 238));
                mainPanel.add(scrollPane, BorderLayout.CENTER);
                
                JOptionPane optionPane = new JOptionPane(mainPanel, 
                    JOptionPane.PLAIN_MESSAGE, 
                    JOptionPane.DEFAULT_OPTION, 
                    null, 
                    new Object[]{"Close"}, 
                    "Close");
                
                JDialog dialog = optionPane.createDialog(adminHomeView, "Customer Suggestions");
                dialog.setIconImage(new ImageIcon(getClass().getResource("/ImagePicker/Logo.png")).getImage());
                dialog.setModal(true);
                dialog.setSize(600, 500);
                dialog.setMinimumSize(new Dimension(550, 400));
                dialog.setLocationRelativeTo(adminHomeView);
                dialog.setVisible(true);
                
                suggestionDao.markSuggestionsAsRead();

            
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(adminHomeView, 
                    "Error loading suggestions: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        
// Notice Navigation Action Listener
    class NoticeNav implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            showNoticeDialog();
        }
    }

    // Main Notice Dialog Method
    private void showNoticeDialog() {
        try {
            List<NoticeData> existingNotices = noticeDao.getAllNoticesByOwner(currentOwnerId);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(241, 237, 238));
            mainPanel.setPreferredSize(new Dimension(700, 600));

            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(new Color(227, 143, 11));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            JLabel titleLabel = new JLabel("Notice Management");
            titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            headerPanel.add(titleLabel, BorderLayout.WEST);

            JButton addNoticeBtn = new JButton("+ Add New Notice");
            addNoticeBtn.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 14));
            addNoticeBtn.setBackground(Color.WHITE);
            addNoticeBtn.setForeground(new Color(227, 143, 11));
            addNoticeBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
            ));
            addNoticeBtn.setFocusPainted(false);
            addNoticeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            headerPanel.add(addNoticeBtn, BorderLayout.EAST);

            mainPanel.add(headerPanel, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(new Color(241, 237, 238));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            if (existingNotices.isEmpty()) {
                JLabel noNoticeLabel = new JLabel("No notices found. Click 'Add New Notice' to create one.");
                noNoticeLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 16));
                noNoticeLabel.setForeground(new Color(100, 100, 100));
                noNoticeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(Box.createVerticalGlue());
                contentPanel.add(noNoticeLabel);
                contentPanel.add(Box.createVerticalGlue());
            } else {
                for (NoticeData notice : existingNotices) {
                    contentPanel.add(createNoticeCard(notice, contentPanel));
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                }
            }

            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(new Color(241, 237, 238));
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            JDialog dialog = new JDialog(adminHomeView, "Notice Management", true);
            dialog.setIconImage(new ImageIcon(getClass().getResource("/ImagePicker/Logo.png")).getImage());
            dialog.setContentPane(mainPanel);
            dialog.setSize(700, 600);
            dialog.setLocationRelativeTo(adminHomeView);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            addNoticeBtn.addActionListener(ev -> {
                showAddNoticeDialog(dialog, contentPanel);
            });

            dialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(adminHomeView,
                "Error loading notices: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Create Notice Card Method
    private JPanel createNoticeCard(NoticeData notice, JPanel contentPanel) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(notice.getTitle());
        titleLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(70, 70, 70));

        JLabel priorityLabel = new JLabel(notice.getPriority());
        priorityLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 12));
        priorityLabel.setOpaque(true);
        priorityLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        switch (notice.getPriority()) {
            case "HIGH":
                priorityLabel.setBackground(new Color(220, 53, 69));
                priorityLabel.setForeground(Color.BLACK);
                break;
            case "MEDIUM":
                priorityLabel.setBackground(new Color(255, 193, 7));
                priorityLabel.setForeground(Color.BLACK);
                break;
            case "LOW":
                priorityLabel.setBackground(new Color(40, 167, 69));
                priorityLabel.setForeground(Color.BLACK);
                break;
        }

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(priorityLabel, BorderLayout.EAST);
        cardPanel.add(headerPanel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(notice.getContent());
        contentArea.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        cardPanel.add(contentArea, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);

        JLabel dateLabel = new JLabel("Created: " + notice.getCreatedAt().toString());
        dateLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 12));
        dateLabel.setForeground(new Color(102,102,102));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        actionPanel.setBackground(Color.WHITE);

        JButton editBtn = createActionButton("Edit", new Color(0, 123, 255));
        JButton deleteBtn = createActionButton("Delete", new Color(220, 53, 69));

        String toggleText = notice.isActive() ? "Deactivate" : "Activate";
        Color toggleColor = notice.isActive() ? new Color(255, 193, 7) : new Color(40, 167, 69);
        JButton toggleBtn = createActionButton(toggleText, toggleColor);

        actionPanel.add(editBtn);
        actionPanel.add(toggleBtn);
        actionPanel.add(deleteBtn);

        footerPanel.add(dateLabel, BorderLayout.WEST);
        footerPanel.add(actionPanel, BorderLayout.EAST);
        cardPanel.add(footerPanel, BorderLayout.SOUTH);

        editBtn.addActionListener(e -> editNotice(notice, contentPanel));
        deleteBtn.addActionListener(e -> deleteNotice(notice, contentPanel));
        toggleBtn.addActionListener(e -> toggleNoticeStatus(notice, contentPanel));

        return cardPanel;
    }

    // Create Action Button Helper Method
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Refresh Notice Content Method
    private void refreshNoticeContent(JPanel contentPanel) {
        try {
            List<NoticeData> existingNotices = noticeDao.getAllNoticesByOwner(currentOwnerId);

            // Clear existing content
            contentPanel.removeAll();

            if (existingNotices.isEmpty()) {
                JLabel noNoticeLabel = new JLabel("No notices found. Click 'Add New Notice' to create one.");
                noNoticeLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 16));
                noNoticeLabel.setForeground(new Color(100, 100, 100));
                noNoticeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(Box.createVerticalGlue());
                contentPanel.add(noNoticeLabel);
                contentPanel.add(Box.createVerticalGlue());
            } else {
                for (NoticeData notice : existingNotices) {
                    contentPanel.add(createNoticeCard(notice, contentPanel));
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                }
            }

            // Refresh the UI
            contentPanel.revalidate();
            contentPanel.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(adminHomeView,
                "Error refreshing notices: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Show Add Notice Dialog
    private void showAddNoticeDialog(JDialog parentDialog, JPanel contentPanel) {
        showNoticeFormDialog(null, "Add New Notice", parentDialog, contentPanel);
    }

    // Edit Notice Method
    private void editNotice(NoticeData notice, JPanel contentPanel) {
        JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(contentPanel);
        showNoticeFormDialog(notice, "Edit Notice", parentDialog, contentPanel);
    }

    // Notice Form Dialog (Add/Edit)
    private void showNoticeFormDialog(NoticeData existingNotice, String title, JDialog parentDialog, JPanel contentPanel) {
        JDialog dialog = new JDialog(parentDialog, title, true);
        dialog.setIconImage(new ImageIcon(getClass().getResource("/ImagePicker/Logo.png")).getImage());
        dialog.setSize(550, 500);
        dialog.setLocationRelativeTo(parentDialog);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(241, 237, 238));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(227, 143, 11));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel(title);
        headerLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("Notice Title *");
        titleLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(70, 70, 70));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        titleField.setPreferredSize(new Dimension(400, 35));
        titleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        titleField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priorityLabel = new JLabel("Priority *");
        priorityLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 14));
        priorityLabel.setForeground(new Color(70, 70, 70));
        priorityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> priorityCombo = new JComboBox<>(new String[]{"HIGH", "MEDIUM", "LOW"});
        priorityCombo.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
        priorityCombo.setMaximumSize(new Dimension(200, 35));
        priorityCombo.setPreferredSize(new Dimension(200, 35));
        priorityCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        priorityCombo.setBackground(Color.WHITE);

        JLabel contentLabel = new JLabel("Notice Content *");
        contentLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 14));
        contentLabel.setForeground(new Color(70, 70, 70));
        contentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea contentArea = new JTextArea(8, 30);
        contentArea.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(Color.WHITE);
        contentArea.setForeground(new Color(70, 70, 70));
        contentArea.setCaretColor(new Color(227, 143, 11));
        contentArea.setSelectionColor(new Color(227, 143, 11, 100));
        contentArea.setMargin(new Insets(10, 10, 10, 10));

        contentArea.setEditable(true);
        contentArea.setFocusable(true);
        contentArea.setRequestFocusEnabled(true);

        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(227, 143, 11), 2));
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        contentScrollPane.setPreferredSize(new Dimension(400, 180));
        contentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(titleLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(titleField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(priorityLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(priorityCombo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        formPanel.add(contentLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(contentScrollPane);

        if (existingNotice != null) {
            titleField.setText(existingNotice.getTitle());
            priorityCombo.setSelectedItem(existingNotice.getPriority());
            contentArea.setText(existingNotice.getContent());
            SwingUtilities.invokeLater(() -> {
                contentArea.setCaretPosition(contentArea.getText().length());
            });
        }

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(new Color(241, 237, 238));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(108, 117, 125));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton saveBtn = new JButton("Save Notice");
        saveBtn.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 14));
        saveBtn.setBackground(new Color(227, 143, 11));
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> dialog.dispose());

        saveBtn.addActionListener(e -> {
            String titleText = titleField.getText().trim();
            String contentText = contentArea.getText().trim();
            String priority = (String) priorityCombo.getSelectedItem();

            if (titleText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter a notice title.", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                titleField.requestFocus();
                return;
            }

            if (contentText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter notice content.", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                contentArea.requestFocus();
                return;
            }

            try {
                boolean success = false;

                if (existingNotice == null) {
                    NoticeData newNotice = new NoticeData(currentOwnerId, titleText, contentText, priority, "admin");
                    success = noticeDao.addNotice(newNotice);

                    if (success) {
                        dialog.dispose();
                        showSuccess("Notice added successfully!");
                        refreshNoticeContent(contentPanel);
                    } else {
                        showError("Failed to add notice. Please try again.");
                    }
                } else {
                    existingNotice.setTitle(titleText);
                    existingNotice.setContent(contentText);
                    existingNotice.setPriority(priority);

                    success = noticeDao.updateNotice(existingNotice);

                    if (success) {
                        dialog.dispose();
                        showSuccess("Notice updated successfully!");
                        refreshNoticeContent(contentPanel);
                    } else {
                        showError("Failed to update notice. Please try again.");
                    }
                }
            } catch (Exception ex) {
                showError("Error saving notice: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        dialog.setContentPane(mainPanel);
        dialog.setResizable(true);
        dialog.setMinimumSize(new Dimension(500, 450));

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (existingNotice == null) {
                        titleField.requestFocus();
                    } else {
                        contentArea.requestFocus();
                    }
                });
            }
        });

        dialog.setVisible(true);
    }

    // Delete Notice Method
    private void deleteNotice(NoticeData notice, JPanel contentPanel) {
        int result = JOptionPane.showConfirmDialog(adminHomeView,
            "Are you sure you want to delete this notice?\n\nTitle: " + notice.getTitle(),
            "Delete Notice", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                if (noticeDao.deleteNotice(notice.getNoticeId())) {
                    showSuccess("Notice deleted successfully!");
                    refreshNoticeContent(contentPanel);
                } else {
                    showError("Failed to delete notice.");
                }
            } catch (Exception ex) {
                showError("Error deleting notice: " + ex.getMessage());
            }
        }
    }

    // Toggle Notice Status Method
    private void toggleNoticeStatus(NoticeData notice, JPanel contentPanel) {
        try {
            boolean success;
            String action;

            if (notice.isActive()) {
                success = noticeDao.deactivateNotice(notice.getNoticeId());
                action = "deactivated";
            } else {
                success = noticeDao.activateNotice(notice.getNoticeId());
                action = "activated";
            }

            if (success) {
                showSuccess("Notice " + action + " successfully!");
                refreshNoticeContent(contentPanel);
            } else {
                showError("Failed to " + action.substring(0, action.length() - 1) + " notice.");
            }
        } catch (Exception ex) {
            showError("Error updating notice status: " + ex.getMessage());
        }
    }
    
    class AnalysisNav implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            AdminAnalysisView adminAnalysisView = new AdminAnalysisView();
            AdminAnalysisController adminAnalysisController = new AdminAnalysisController(adminAnalysisView, currentOwnerId);
            adminAnalysisController.open();
            close();
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
