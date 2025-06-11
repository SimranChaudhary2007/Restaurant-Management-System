/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.UIElements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import restaurant.management.system.model.RestaurantData;

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
    
    public RestaurantCardPanel(RestaurantData restaurant) {
        this.restaurantData = restaurant;
        initializeComponents();
        setupLayout();
        populateData();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());

        setBackground(new Color(239, 204, 150));
        setFocusable(true);
        setRequestFocusEnabled(true);
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
    }
    
    private void setupLayout() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(239, 204, 150));
        contentPanel.setBorder(null);
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(239, 204, 150));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setPreferredSize(new Dimension(280, 170));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(239, 204, 150));
        infoPanel.setBorder(null);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));

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

        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void populateData() {
        
        if (restaurantData != null) {
            restaurantData.getRestaurantName();
            
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