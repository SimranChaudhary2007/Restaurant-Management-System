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
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.SuggestionDao;
import restaurant.management.system.model.RestaurantData;
import restaurant.management.system.model.SuggestionData;

/**
 *
 * @author acer
 */
public class RestaurantCardPanel extends PanelShadow {
    private RestaurantData restaurantData;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel phoneLabel;
    private JLabel ownerLabel;
    private JButton suggestionButton;
    private SuggestionDao suggestionDao;
    private int customerId;
    private String customerName;
    
    public RestaurantCardPanel(RestaurantData restaurant, int customerId, String customerName) {
        this.restaurantData = restaurant;
        this.customerId = customerId;
        this.customerName = customerName;
        this.suggestionDao = new SuggestionDao();
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

        setPreferredSize(new Dimension(1150, 220));
        setMaximumSize(new Dimension(1150, 220));
        setMinimumSize(new Dimension(1150, 220));
       
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(250, 160));
        imageLabel.setBorder(null);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBackground(new Color(239, 204, 150));
        imageLabel.setOpaque(false);
        
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
        nameLabel.setForeground(new Color(80, 50, 30));
        
        addressLabel = new JLabel();
        addressLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        addressLabel.setForeground(new Color(100, 70, 50));
        
        phoneLabel = new JLabel();
        phoneLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        phoneLabel.setForeground(new Color(100, 70, 50));
        
        ownerLabel = new JLabel();
        ownerLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        ownerLabel.setForeground(new Color(100, 70, 50));
        
        suggestionButton = new JButton("Suggest");
        suggestionButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        suggestionButton.setBackground(new Color(239,167,9));
        suggestionButton.setForeground(Color.WHITE);
        suggestionButton.setFocusPainted(false);
        suggestionButton.setBorderPainted(false);
        suggestionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        suggestionButton.setPreferredSize(new Dimension(105, 30));
    }
    
    private void setupLayout() {
        setLayout(new OverlayLayout(this));
        setBackground(new Color(239, 204, 150));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(239, 204, 150));
        contentPanel.setOpaque(false);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(239, 204, 150));
        imagePanel.setOpaque(false);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setPreferredSize(new Dimension(280, 170));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(239, 204, 150));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(addressLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(phoneLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(ownerLabel);
        infoPanel.add(Box.createVerticalGlue());

        contentPanel.add(imagePanel, BorderLayout.WEST);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        buttonPanel.add(suggestionButton);

        add(contentPanel);
        add(buttonPanel);
    }
    
    private void setupEventHandlers() {
        suggestionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                suggestionButton.setBackground(new Color(255,153,0));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                suggestionButton.setBackground(new Color(239,167,9));
            }
        });
        
        suggestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSuggestionDialog();
            }
        });
    }
    
    private void showSuggestionDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        
        JDialog dialog = new JDialog(parentFrame, "Add Suggestion - " + restaurantData.getRestaurantName(), true);
        dialog.setSize(500, 400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parentFrame);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));
        
        JLabel titleLabel = new JLabel("Share your suggestion for " + restaurantData.getRestaurantName());
        titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        titleLabel.setForeground(new Color(80, 50, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subtitleLabel = new JLabel("Help us improve our service!");
        subtitleLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 70, 50));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(Box.createVerticalStrut(5), BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(245, 245, 245));
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel textLabel = new JLabel("Your Suggestion:");
        textLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        textLabel.setForeground(new Color(80, 50, 30));
        
        JTextArea suggestionTextArea = new JTextArea(8, 30);
        suggestionTextArea.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        suggestionTextArea.setBackground(Color.WHITE);
        suggestionTextArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        suggestionTextArea.setLineWrap(true);
        suggestionTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(suggestionTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        textPanel.add(textLabel, BorderLayout.NORTH);
        textPanel.add(Box.createVerticalStrut(8), BorderLayout.CENTER);
        textPanel.add(scrollPane, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        cancelButton.setBackground(new Color(150, 150, 150));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(80, 35));
        
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        submitButton.setBackground(new Color(34, 139, 34));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setPreferredSize(new Dimension(80, 35));
        
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(new Color(120, 120, 120));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(new Color(150, 150, 150));
            }
        });
        
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                submitButton.setBackground(new Color(0, 120, 0));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                submitButton.setBackground(new Color(34, 139, 34));
            }
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        submitButton.addActionListener(e -> {
            String suggestionText = suggestionTextArea.getText().trim();
            if (suggestionText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter your suggestion!", 
                    "Empty Suggestion", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (submitSuggestion(suggestionText)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Thank you for your suggestion!\nIt has been sent to the restaurant owner.", 
                    "Suggestion Submitted", 
                    JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, 
                    "Failed to submit suggestion. Please try again.", 
                    "Submission Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private boolean submitSuggestion(String suggestionText) {
        try {
            SuggestionData suggestion = new SuggestionData();
            
            // Set customer details
            suggestion.setCustomerId(customerId);
            suggestion.setCustomerName(customerName);
            
            // Set restaurant owner details
            suggestion.setOwnerId(restaurantData.getOwnerId());
            suggestion.setRestaurantName(restaurantData.getRestaurantName());
            
            // Set suggestion details
            suggestion.setSuggestionText(suggestionText);
            suggestion.setCreatedAt(LocalDateTime.now());
            suggestion.setRead(false);
            
            return suggestionDao.addSuggestion(suggestion);
            
        } catch (Exception e) {
            System.err.println("Error submitting suggestion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void populateData() {
        if (restaurantData != null) {
            // Set restaurant name
            nameLabel.setText(restaurantData.getRestaurantName() != null ? 
                            restaurantData.getRestaurantName() : "Restaurant Name");
            
            // Set address
            addressLabel.setText(restaurantData.getAddress() != null ? 
                               restaurantData.getAddress() : "Location not specified");
            
            // Set phone number
            phoneLabel.setText((restaurantData.getPhoneNumber() != null ? 
                             restaurantData.getPhoneNumber() : "Phone not available"));
            
            // Set owner name
            ownerLabel.setText("Owner: " + (restaurantData.getOwnerName() != null ? 
                             restaurantData.getOwnerName() : "Owner not specified"));
            
            // Set restaurant image
            byte[] imageData = restaurantData.getRestaurantImage();
            if (imageData != null && imageData.length > 0) {
                try {
                    ImageIcon originalIcon = new ImageIcon(imageData);
                    if (originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0) {
                        Image scaledImage = originalIcon.getImage().getScaledInstance(
                            250, 160, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);
                        imageLabel.setIcon(scaledIcon);
                        imageLabel.setText("");
                    } else {
                        imageLabel.setText("Invalid Image");
                        imageLabel.setIcon(null);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading image: " + e.getMessage());
                    imageLabel.setText("Image Error");
                    imageLabel.setIcon(null);
                }
            } else {
                imageLabel.setText("No Image");
                imageLabel.setIcon(null);
            }
        }
    }
    
    public RestaurantData getRestaurantData() {
        return restaurantData;
    }
}
