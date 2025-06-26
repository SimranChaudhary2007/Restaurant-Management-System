/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import restaurant.management.system.dao.NoticeDao;
import restaurant.management.system.dao.SuggestionDao;
import restaurant.management.system.model.NoticeData;
import restaurant.management.system.model.StaffRequestData;
import restaurant.management.system.model.SuggestionData;
import restaurant.management.system.view.LoginView;
import restaurant.management.system.view.StaffHomeView;
import restaurant.management.system.view.StaffProfileView;

/**
 *
 * @author ACER
 */
public class StaffHomeController {
    private StaffHomeView staffHomeView;
    private int currentStaffId;
    private int currentOwnerId;
    private SuggestionDao suggestionDao;
    private NoticeDao noticeDao;
    
    public StaffHomeController(StaffHomeView view, int staffId, int ownerId) {
        this.staffHomeView = view;
        this.currentStaffId = staffId;
        this.currentOwnerId = ownerId;
        this.suggestionDao = new SuggestionDao();
        this.noticeDao = new NoticeDao();
        
        this.staffHomeView.suggestionNavigation(new SuggestionNav());
        this.staffHomeView.noticeNavigation(new NoticeNav());
        this.staffHomeView.profileNavigation(new ProfileNav(staffHomeView.getProfilelabel()));
        this.staffHomeView.menuNavigation(new MenuNav(staffHomeView.getMenulabel()));
        this.staffHomeView.orderNavigation(new OrderNav(staffHomeView.getOrderlabel()));
        this.staffHomeView.logoutNavigation(new LogoutNav(staffHomeView.getLogoutlabel()));
        this.staffHomeView.requestButtonNavigation(new RequestNav());
    }
    
    public void open() {
        this.staffHomeView.setVisible(true);
        // Refresh cancelled orders display when opening
        refreshCancelledOrdersDisplay();
    }
    
    public void close() {
        this.staffHomeView.dispose();
    }
    
    // Method to refresh cancelled orders display
    public void refreshCancelledOrdersDisplay() {
        if (staffHomeView != null) {
            staffHomeView.refreshCancelledOrders();
        }
    }
    
    class ProfileNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public ProfileNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            StaffProfileView staffProfileView = new StaffProfileView();
            StaffProfileController staffProfileController= new StaffProfileController(staffProfileView, currentStaffId);
            staffProfileController.open();
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
            JFrame staffHomeView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            staffHomeView.dispose();

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

    class SuggestionNav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showSuggestionDialog();
        }
    }

    private void showSuggestionDialog() {
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
                JPanel noSuggestionPanel = new JPanel();
                noSuggestionPanel.setBackground(new Color(241, 237, 238));
                noSuggestionPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

                JLabel noSuggestionLabel = new JLabel("<html><center>No customer suggestions available at this time.<br><br>");
                noSuggestionLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
                noSuggestionLabel.setForeground(new Color(100, 100, 100));
                noSuggestionLabel.setHorizontalAlignment(JLabel.CENTER);

                noSuggestionPanel.add(noSuggestionLabel);
                cardsPanel.add(noSuggestionPanel);
            } else {
                for (SuggestionData suggestion : suggestions) {
                    JPanel cardPanel = createSuggestionCard(suggestion);
                    cardsPanel.add(cardPanel);
                    cardsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                }
            }

            JScrollPane scrollPane = new JScrollPane(cardsPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(new Color(241, 237, 238));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            JOptionPane optionPane = new JOptionPane(mainPanel, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.DEFAULT_OPTION, 
                null, 
                new Object[]{"Close"}, 
                "Close");

            JDialog dialog = optionPane.createDialog(staffHomeView, "Customer Suggestions");
            try {
                dialog.setIconImage(new ImageIcon(getClass().getResource("/ImagePicker/Logo.png")).getImage());
            } catch (Exception ex) {
            }
            dialog.setModal(true);
            dialog.setSize(650, 550);
            dialog.setMinimumSize(new Dimension(600, 450));
            dialog.setLocationRelativeTo(staffHomeView);
            dialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(staffHomeView, 
                "Error loading suggestions: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createSuggestionCard(SuggestionData suggestion) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel customerLabel = new JLabel("Customer: " + suggestion.getCustomerName());
        customerLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 16));
        customerLabel.setForeground(new Color(70, 70, 70));

        JLabel restaurantLabel = new JLabel("Restaurant: " + suggestion.getRestaurantName());
        restaurantLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
        restaurantLabel.setForeground(new Color(100, 100, 100));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(customerLabel);
        infoPanel.add(restaurantLabel);

        headerPanel.add(infoPanel, BorderLayout.WEST);
        cardPanel.add(headerPanel, BorderLayout.NORTH);

        JTextArea suggestionText = new JTextArea(suggestion.getSuggestionText());
        suggestionText.setLineWrap(true);
        suggestionText.setWrapStyleWord(true);
        suggestionText.setEditable(false);
        suggestionText.setBackground(Color.WHITE);
        suggestionText.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
        suggestionText.setForeground(new Color(70, 70, 70));
        suggestionText.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        suggestionText.setRows(3);

        JScrollPane textScrollPane = new JScrollPane(suggestionText);
        textScrollPane.setBorder(BorderFactory.createEmptyBorder());
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textScrollPane.setBackground(Color.WHITE);
        textScrollPane.getViewport().setBackground(Color.WHITE);

        cardPanel.add(textScrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        String dateStr = suggestion.getCreatedAt() != null ? suggestion.getCreatedAt().toString() : "Unknown date";
        JLabel timeLabel = new JLabel("Received: " + dateStr.substring(0, Math.min(19, dateStr.length())));
        timeLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 12));
        timeLabel.setForeground(new Color(102, 102, 102));

        footerPanel.add(timeLabel, BorderLayout.WEST);
        cardPanel.add(footerPanel, BorderLayout.SOUTH);

        return cardPanel;
    }
    
    class NoticeNav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showNoticeDialog();
        }
    }
    
    private void showNoticeDialog() {
        try {
            List<NoticeData> notices = noticeDao.getNoticesForStaff(currentOwnerId);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(new Color(241, 237, 238));
            
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(227, 143, 11));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            JLabel titleLabel = new JLabel("Restaurant Notices");
            titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            headerPanel.add(titleLabel);
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            
            JPanel noticesPanel = new JPanel();
            noticesPanel.setLayout(new BoxLayout(noticesPanel, BoxLayout.Y_AXIS));
            noticesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            noticesPanel.setBackground(new Color(241, 237, 238));
            
            if (notices.isEmpty()) {
                JPanel noNoticePanel = new JPanel();
                noNoticePanel.setBackground(new Color(241, 237, 238));
                noNoticePanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
                
                JLabel noNoticeLabel = new JLabel("<html><center>No notices available at this time.<br><br>");
                noNoticeLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
                noNoticeLabel.setForeground(new Color(100, 100, 100));
                noNoticeLabel.setHorizontalAlignment(JLabel.CENTER);
                
                noNoticePanel.add(noNoticeLabel);
                noticesPanel.add(noNoticePanel);
            } else {
                for (NoticeData notice : notices) {
                    JPanel noticeCard = createNoticeCard(notice);
                    noticesPanel.add(noticeCard);
                    noticesPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                }
            }
            
            JScrollPane scrollPane = new JScrollPane(noticesPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(new Color(241, 237, 238));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            
            JOptionPane optionPane = new JOptionPane(mainPanel, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.DEFAULT_OPTION, 
                null, 
                new Object[]{"Close"}, 
                "Close");
            
            JDialog dialog = optionPane.createDialog(staffHomeView, "Restaurant Notices");
            try {
                dialog.setIconImage(new ImageIcon(getClass().getResource("/ImagePicker/Logo.png")).getImage());
            } catch (Exception ex) {
            }
            dialog.setModal(true);
            dialog.setSize(650, 550);
            dialog.setMinimumSize(new Dimension(600, 450));
            dialog.setLocationRelativeTo(staffHomeView);
            dialog.setVisible(true);
            
        } catch (Exception ex) {
        }
    }
    
    private JPanel createNoticeCard(NoticeData notice) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(notice.getTitle());
        titleLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 70, 70));
        
        JLabel priorityLabel = new JLabel(notice.getPriority());
        priorityLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 12));
        priorityLabel.setOpaque(true);
        priorityLabel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        
        switch (notice.getPriority().toUpperCase()) {
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
            default:
                priorityLabel.setBackground(new Color(108, 117, 125));
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
        contentArea.setForeground(new Color(70, 70, 70));
        contentArea.setRows(3);
        
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentScrollPane.setBackground(Color.WHITE);
        contentScrollPane.getViewport().setBackground(Color.WHITE);
        
        cardPanel.add(contentScrollPane, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        String dateStr = notice.getCreatedAt() != null ? notice.getCreatedAt().toString() : "Unknown date";
        JLabel dateLabel = new JLabel("Posted: " + dateStr.substring(0, Math.min(19, dateStr.length())));
        dateLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 12));
        dateLabel.setForeground(new Color(102,102,102));
        
        String author = notice.getCreatedBy() != null ? notice.getCreatedBy() : "Management";
        JLabel authorLabel = new JLabel("By: " + author);
        authorLabel.setFont(new Font("Microsoft JhengHei UI", Font.ITALIC, 12));
        authorLabel.setForeground(new Color(102,102,102));
        
        footerPanel.add(dateLabel, BorderLayout.WEST);
        footerPanel.add(authorLabel, BorderLayout.EAST);
        cardPanel.add(footerPanel, BorderLayout.SOUTH);
        
        return cardPanel;
    }

    class RequestNav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showRequestDialog();
        }
    }

    private void showRequestDialog() {
        JDialog dialog = new JDialog(staffHomeView, "Submit Request", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(staffHomeView);
        dialog.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(227, 143, 11));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel titleLabel = new JLabel("Submit a Request (Leave/Other)");
        titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(241, 237, 238));

        JLabel typeLabel = new JLabel("Request Type:");
        typeLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        contentPanel.add(typeLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        String[] types = {"Leave", "General", "Other"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        contentPanel.add(typeCombo);
        contentPanel.add(Box.createVerticalStrut(15));

        JLabel descLabel = new JLabel("Request Description:");
        descLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createVerticalStrut(5));

        JTextArea requestArea = new JTextArea(6, 30);
        requestArea.setLineWrap(true);
        requestArea.setWrapStyleWord(true);
        requestArea.setFont(new Font("Mongolian Baiti", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(requestArea);
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createVerticalStrut(20));

        JButton submitButton = new JButton("Submit Request");
        submitButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        submitButton.setBackground(new Color(227, 143, 11));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        contentPanel.add(submitButton);

        dialog.add(contentPanel, BorderLayout.CENTER);

        submitButton.addActionListener(ev -> {
        String requestType = (String) typeCombo.getSelectedItem();
        String description = requestArea.getText().trim();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please enter your request.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        restaurant.management.system.dao.StaffDao staffDao = new restaurant.management.system.dao.StaffDao();
        restaurant.management.system.model.StaffData staff = staffDao.getStaffById(currentStaffId);
        StaffRequestData request = new StaffRequestData();
        request.setRequestType(requestType);
        request.setRequestDescription(description);
        request.setOwnerId(currentOwnerId);
        request.setStatus("PENDING");
        request.setRequestDate(new java.sql.Timestamp(System.currentTimeMillis()));

        if (staff != null) {
            request.setFullName(staff.getFullName());
            request.setRestaurantName(staff.getRestaurantName());
            request.setPhoneNumber(staff.getPhoneNumber());

            // QUICK FIX: For non-registration requests, modify email to avoid UNIQUE constraint
            String emailToUse = staff.getEmail();
            if (!"Registration".equalsIgnoreCase(requestType)) {
                emailToUse = requestType.toLowerCase() + "_" + System.currentTimeMillis() + "_" + staff.getEmail();
            }
            request.setEmail(emailToUse);

            request.setUsername(staff.getUsername());
            request.setPassword(staff.getPassword());
        } else {
            request.setFullName("N/A");
            request.setRestaurantName("N/A");
            request.setPhoneNumber("N/A");
            request.setEmail("general_request_" + System.currentTimeMillis() + "@example.com");
            request.setUsername("general_request_" + System.currentTimeMillis());
            request.setPassword("N/A");
        }

        restaurant.management.system.dao.StaffRequestDao dao = new restaurant.management.system.dao.StaffRequestDao();
        boolean success = dao.addPendingRequest(request);

        if (success) {
            JOptionPane.showMessageDialog(dialog, "Request submitted successfully! Pending admin approval.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(dialog, "Failed to submit request. Please check console for details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

        dialog.setVisible(true);
    }
}    