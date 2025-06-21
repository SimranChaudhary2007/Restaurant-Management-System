/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import restaurant.management.system.dao.NoticeDao;
import restaurant.management.system.model.NoticeData;
import restaurant.management.system.view.StaffHomeView;

/**
 *
 * @author ACER
 */
public class StaffHomeController {
    private StaffHomeView staffHomeView;
    private int currentStaffId;
    private int currentOwnerId;
    private NoticeDao noticeDao;
    
    public StaffHomeController(StaffHomeView view, int staffId, int ownerId) {
        this.staffHomeView = view;
        this.currentStaffId = staffId;
        this.currentOwnerId = ownerId;
        this.noticeDao = new NoticeDao();
        
        // Set up notice navigation listener
        this.staffHomeView.noticeNavigation(new NoticeNav());
    }
    
    public void open() {
        this.staffHomeView.setVisible(true);
    }
    
    public void close() {
        this.staffHomeView.dispose();
    }
    
    class NoticeNav implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showNoticeDialog();
        }
    }
    
    private void showNoticeDialog() {
        try {
            // Get only active notices for the current owner
            List<NoticeData> notices = noticeDao.getActiveNoticesByOwner(currentOwnerId);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(new Color(241, 237, 238));
            
            // Header Panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(227, 143, 11));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            JLabel titleLabel = new JLabel("Restaurant Notices");
            titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            headerPanel.add(titleLabel);
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            
            // Notices Panel
            JPanel noticesPanel = new JPanel();
            noticesPanel.setLayout(new BoxLayout(noticesPanel, BoxLayout.Y_AXIS));
            noticesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            noticesPanel.setBackground(new Color(241, 237, 238));
            
            if (notices.isEmpty()) {
                // Show "No notices available" message inside the dialog
                JPanel noNoticePanel = new JPanel();
                noNoticePanel.setBackground(new Color(241, 237, 238));
                noNoticePanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
                
                JLabel noNoticeLabel = new JLabel("No notices available at this time.");
                noNoticeLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
                noNoticeLabel.setForeground(new Color(100, 100, 100));
                noNoticeLabel.setHorizontalAlignment(JLabel.CENTER);
                
                noNoticePanel.add(noNoticeLabel);
                noticesPanel.add(noNoticePanel);
            } else {
                // Add notice cards
                for (NoticeData notice : notices) {
                    JPanel noticeCard = createNoticeCard(notice);
                    noticesPanel.add(noticeCard);
                    noticesPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                }
            }
            
            JScrollPane scrollPane = new JScrollPane(noticesPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(new Color(241, 237, 238));
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            
            // Create and show dialog
            JOptionPane optionPane = new JOptionPane(mainPanel, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.DEFAULT_OPTION, 
                null, 
                new Object[]{"Close"}, 
                "Close");
            
            JDialog dialog = optionPane.createDialog(staffHomeView, "Restaurant Notices");
            dialog.setIconImage(new ImageIcon(getClass().getResource("/ImagePicker/Logo.png")).getImage());
            dialog.setModal(true);
            dialog.setSize(600, 500);
            dialog.setMinimumSize(new Dimension(550, 400));
            dialog.setLocationRelativeTo(staffHomeView);
            dialog.setVisible(true);
            
        } catch (Exception ex) {
            // Show error message popup
            JOptionPane.showMessageDialog(
                staffHomeView, 
                "Error loading notices: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private JPanel createNoticeCard(NoticeData notice) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        // Header with title and priority
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(notice.getTitle());
        titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 70, 70));
        
        JLabel priorityLabel = new JLabel(notice.getPriority());
        priorityLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
        priorityLabel.setOpaque(true);
        priorityLabel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        
        // Set priority colors
        switch (notice.getPriority()) {
            case "HIGH":
                priorityLabel.setBackground(new Color(220, 53, 69));
                priorityLabel.setForeground(Color.WHITE);
                break;
            case "MEDIUM":
                priorityLabel.setBackground(new Color(255, 193, 7));
                priorityLabel.setForeground(Color.BLACK);
                break;
            case "LOW":
                priorityLabel.setBackground(new Color(40, 167, 69));
                priorityLabel.setForeground(Color.WHITE);
                break;
        }
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(priorityLabel, BorderLayout.EAST);
        cardPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content area
        JTextArea contentArea = new JTextArea(notice.getContent());
        contentArea.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        contentArea.setForeground(new Color(70, 70, 70));
        
        cardPanel.add(contentArea, BorderLayout.CENTER);
        
        // Footer with date and author
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        JLabel dateLabel = new JLabel("Posted: " + notice.getCreatedAt().toString());
        dateLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
        dateLabel.setForeground(new Color(150, 150, 150));
        
        JLabel authorLabel = new JLabel("By: Management");
        authorLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
        authorLabel.setForeground(new Color(150, 150, 150));
        
        footerPanel.add(dateLabel, BorderLayout.WEST);
        footerPanel.add(authorLabel, BorderLayout.EAST);
        cardPanel.add(footerPanel, BorderLayout.SOUTH);
        
        return cardPanel;
    }
}