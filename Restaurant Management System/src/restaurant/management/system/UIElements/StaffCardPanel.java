package restaurant.management.system.UIElements;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import restaurant.management.system.controller.StaffInfoController;
import restaurant.management.system.model.StaffData;
import restaurant.management.system.dao.StaffDao;

public class StaffCardPanel extends PanelShadow {
    private static final Color CARD_BG = new Color(239, 204, 150);
    private static final Color EDIT_BTN_BG = new Color(239, 167, 9);
    private static final Color DELETE_BTN_BG = new Color(220, 53, 69);
    private static final Color HOVER_EDIT_BG = new Color(255, 153, 0);
    private static final Color HOVER_DELETE_BG = new Color(200, 35, 51);
    private static final Font NAME_FONT = new Font("Mongolian Baiti", Font.BOLD, 20);
    private static final Font LABEL_FONT = new Font("Mongolian Baiti", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Mongolian Baiti", Font.BOLD, 14);

    private final StaffData staffData;
    private final StaffInfoController controller;
    
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel positionLabel;
    private JLabel salaryLabel;
    private JLabel startDateLabel;
    private JButton editButton;
    private JButton deleteButton;

    public StaffCardPanel(StaffData staff, StaffInfoController controller) {
        this.staffData = staff;
        this.controller = controller;
        initPanel();
        initComponents();
        setupLayout();
        populateData();
        setupEventHandlers();
    }

    private void initPanel() {
        setLayout(new BorderLayout());
        setBackground(CARD_BG);
        setFocusable(false);
        setRequestFocusEnabled(false);
        setRoundTopLeft(15);
        setRoundTopRight(15);
        setRoundBottomLeft(15);
        setRoundBottomRight(15);
        setShadowSize(8);
        setShadowOpacity(0.5f);
        setShadowColor(new Color(0, 0, 0, 100));
        setPreferredSize(new Dimension(350, 450));
    }

    private void initComponents() {
        // Image Label - Centered
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(120, 120)); // Larger size
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBackground(new Color(180, 180, 180));
        imageLabel.setOpaque(true);
        
        // Name Label
        nameLabel = new JLabel();
        nameLabel.setFont(NAME_FONT);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Info Labels
        positionLabel = createInfoLabel();
        salaryLabel = createInfoLabel();
        startDateLabel = createInfoLabel();
        
        // Buttons
        editButton = createButton("Edit", EDIT_BTN_BG, HOVER_EDIT_BG);
        deleteButton = createButton("Delete", DELETE_BTN_BG, HOVER_DELETE_BG);
    }

    private JLabel createInfoLabel() {
        JLabel label = new JLabel();
        label.setFont(LABEL_FONT);
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center aligned
        return label;
    }

    private JButton createButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 30)); // Wider buttons
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void setupLayout() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Add vertical space at the top
        contentPanel.add(Box.createVerticalStrut(20));

        // Image Panel (centered)
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setOpaque(false);
        imagePanel.add(imageLabel);
        contentPanel.add(imagePanel);

        // Add space between image and name
        contentPanel.add(Box.createVerticalStrut(15));

        // Name Label (centered)
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setOpaque(false);
        namePanel.add(nameLabel);
        contentPanel.add(namePanel);

        // Add space between name and info
        contentPanel.add(Box.createVerticalStrut(30)); // More space here

        // Info Panel (centered)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(positionLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(salaryLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(startDateLabel);
        contentPanel.add(infoPanel);

        // Add flexible space before buttons
        contentPanel.add(Box.createVerticalGlue());

        // Button Panel (centered)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void populateData() {
        if (staffData != null) {
            nameLabel.setText(staffData.getFullName() != null ? 
                           staffData.getFullName() : "Staff Name");
            
            positionLabel.setText("Position: " + (staffData.getPosition() != null ? 
                               staffData.getPosition() : "Not specified"));
            
            salaryLabel.setText("Salary: $" + (staffData.getSalary() != null ? 
                             staffData.getSalary().toString() : "0.00"));
            
            String dateText = "Date not available";
            if (staffData.getCreatedDate() != null) {
                dateText = staffData.getCreatedDate().toLocalDateTime().toLocalDate()
                          .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            } else if (staffData.getCreatedLocalDate() != null) {
                dateText = staffData.getCreatedLocalDate()
                          .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            }
            startDateLabel.setText("Started: " + dateText);
            
            setProfileImage();
        }
    }

    private void setProfileImage() {
        byte[] imageData = staffData.getProfilePicture();
        if (imageData != null && imageData.length > 0) {
            try {
                ImageIcon originalIcon = new ImageIcon(imageData);
                if (originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0) {
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                        120, 120, Image.SCALE_SMOOTH); // Larger image size
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLabel.setText("");
                }
            } catch (Exception e) {
                System.err.println("Error loading staff image: " + e.getMessage());
                setDefaultImage();
            }
        } else {
            setDefaultImage();
        }
    }

    private void setDefaultImage() {
        imageLabel.setIcon(null);
        imageLabel.setText("No Image");
        imageLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        imageLabel.setForeground(new Color(100, 100, 100));
    }


    private void setupEventHandlers() {
        editButton.addActionListener(e -> handleEditAction());
        deleteButton.addActionListener(e -> handleDeleteAction());
    }

    private void handleEditAction() {
        JTextField positionField = new JTextField(staffData.getPosition() != null ? 
                                               staffData.getPosition() : "");
        BigDecimal salary = staffData.getSalary();
        JTextField salaryField = new JTextField((salary != null && salary.doubleValue() > 0) ? 
                                             String.valueOf(salary) : "");

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Position:"));
        panel.add(positionField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Staff Info", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String newPosition = positionField.getText().trim();
                double newSalary = Double.parseDouble(salaryField.getText().trim());
                
                if (newPosition.isEmpty()) {
                    throw new Exception("Position cannot be empty");
                }

                boolean success = new StaffDao().updateStaffPositionAndSalary(
                    staffData.getId(), newPosition, newSalary);

                if (success) {
                    staffData.setPosition(newPosition);
                    staffData.setSalary(newSalary);
                    populateData();
                    showMessage("Staff updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    if (controller != null) controller.refreshStaffDisplay();
                } else {
                    showMessage("Failed to update staff.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                showMessage("Invalid salary format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                showMessage("Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDeleteAction() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this staff?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = new StaffDao().deleteStaffById(staffData.getId());
            if (success) {
                showMessage("Staff deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (controller != null) controller.refreshStaffDisplay();
            } else {
                showMessage("Failed to delete staff.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public StaffData getStaffData() {
        return staffData;
    }

    public void refreshData() {
        populateData();
        repaint();
    }
}