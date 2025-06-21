/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.UIElements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import restaurant.management.system.controller.StaffInfoController;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.dao.StaffDao;

/**
 *
 * @author acer
 */
public class StaffCardPanel extends PanelShadow {
    private StaffData staffData;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel positionLabel;
    private JLabel salaryLabel;
    private JLabel startDateLabel;
    private JButton editButton;
    private JButton deleteButton;
    private StaffInfoController staffInfoController;
    
    public StaffCardPanel(StaffData staff, StaffInfoController staffInfoController) {
        this.staffData = staff;
        this.staffInfoController = staffInfoController;
        initializeComponents();
        setupLayout();
        populateData();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(239, 204, 150));
        setFocusable(false);
        setRequestFocusEnabled(false);
        setRoundTopLeft(15);
        setRoundTopRight(15);
        setRoundBottomLeft(15);
        setRoundBottomRight(15);
        setShadowSize(8);
        setShadowOpacity(0.5f);
        setShadowColor(new Color(0, 0, 0, 100));

        setPreferredSize(new Dimension(350, 400));
        setMaximumSize(new Dimension(350, 400));
        setMinimumSize(new Dimension(350, 400));
       
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(null);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBackground(new Color(180, 180, 180));
        imageLabel.setOpaque(true);
        
        if (staffData.getProfilePicture() != null && staffData.getProfilePicture().length > 0) {
            ImageIcon icon = new ImageIcon(staffData.getProfilePicture());
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            // set default icon
        }
        
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
        nameLabel.setForeground(new Color(0,0,0));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        positionLabel = new JLabel();
        positionLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        positionLabel.setForeground(new Color(0,0,0));
        positionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        salaryLabel = new JLabel();
        salaryLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        salaryLabel.setForeground(new Color(0,0,0));
        salaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        startDateLabel = new JLabel();
        startDateLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        startDateLabel.setForeground(new Color(0,0,0));
        startDateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        editButton = new JButton("Edit");
        editButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        editButton.setBackground(new Color(239,167,9));
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setPreferredSize(new Dimension(80, 30));
        
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(80, 30));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(239, 204, 150));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Image + Name (Top Panel)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.add(imageLabel);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(nameLabel);

        // Info (Middle Panel)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(positionLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(salaryLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(startDateLabel);

        // Buttons (Bottom Panel)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add all to contentPanel
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
}

    
    private void setupEventHandlers() {
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editButton.setBackground(new Color(255,153,0));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                editButton.setBackground(new Color(239,167,9));
            }
        });
        
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteButton.setBackground(new Color(200, 35, 51));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                deleteButton.setBackground(new Color(220, 53, 69));
            }
        });
        
        editButton.addActionListener(e -> {
            JTextField positionField = new JTextField(staffData.getPosition() != null ? staffData.getPosition() : "");
            BigDecimal salary = staffData.getSalary();
            JTextField salaryField = new JTextField((salary != null && salary.doubleValue() > 0) ? String.valueOf(salary) : "");
            JPanel panel = new JPanel(new java.awt.GridLayout(2, 2, 10, 10));
            panel.add(new JLabel("Position:"));
            panel.add(positionField);
            panel.add(new JLabel("Salary:"));
            panel.add(salaryField);
            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Staff Info", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String newPosition = positionField.getText().trim();
                String salaryStr = salaryField.getText().trim();
                double newSalary = 0;
                try {
                    newSalary = Double.parseDouble(salaryStr);
                    if (newPosition.isEmpty()) throw new Exception("Position cannot be empty");
                    boolean success = new StaffDao().updateStaffPositionAndSalary(staffData.getId(), newPosition, newSalary);
                    if (success) {
                        staffData.setPosition(newPosition);
                        staffData.setSalary(newSalary);
                        populateData();
                        JOptionPane.showMessageDialog(this, "Staff updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        if (staffInfoController != null) staffInfoController.refreshStaffDisplay();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update staff.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this staff?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = new StaffDao().deleteStaffById(staffData.getId());
                if (success) {
                    JOptionPane.showMessageDialog(this, "Staff deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    if (staffInfoController != null) staffInfoController.refreshStaffDisplay();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete staff.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    private void populateData() {
        if (staffData != null) {
            // Set staff name
            nameLabel.setText(staffData.getFullName() != null ? 
                             staffData.getFullName() : "Staff Name");
            
            // Set position
            positionLabel.setText("Position: " + (staffData.getPosition() != null ? 
                                 staffData.getPosition() : "Not specified"));
            
            // Set salary
            salaryLabel.setText("Salary: $" + (staffData.getSalary() != null ? 
                               staffData.getSalary().toString() : "0.00"));
            
            // Set start date - Check both Timestamp and LocalDate
            String dateText = "Date not available";
            if (staffData.getCreatedDate() != null) {
                dateText = staffData.getCreatedDate().toLocalDateTime().toLocalDate()
                          .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            } else if (staffData.getCreatedLocalDate() != null) {
                dateText = staffData.getCreatedLocalDate()
                          .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            }
            startDateLabel.setText("Started: " + dateText);
            
            // Set staff image if available
            byte[] imageData = staffData.getProfilePicture();
            if (imageData != null && imageData.length > 0) {
                try {
                    ImageIcon originalIcon = new ImageIcon(imageData);
                    if (originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0) {
                        Image scaledImage = originalIcon.getImage().getScaledInstance(
                            80, 80, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);
                        imageLabel.setIcon(scaledIcon);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading staff image: " + e.getMessage());
                }
            } else {
                // Clear any existing icon and show default background
                imageLabel.setIcon(null);
                imageLabel.setText("No Image");
                imageLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
                imageLabel.setForeground(new Color(100, 100, 100));
            }
        }
    }
    
    public StaffData getStaffData() {
        return staffData;
    }
    
    // Method to refresh the panel data (useful when staff data is updated externally)
    public void refreshData() {
        populateData();
        repaint();
    }
}