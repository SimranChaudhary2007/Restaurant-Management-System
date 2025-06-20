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
import restaurant.management.system.model.MenuData;

/**
 *
 * @author labish
 */
public class MenuCardPanel extends PanelShadow {
    private MenuData menuData;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel infoLabel;
    private JLabel ratingLabel;
    private JLabel reviewsLabel;
    private JLabel priceLabel;
    
    public MenuCardPanel(MenuData menu) {
        this.menuData = menu;
        initializeComponents();
        setupLayout();
        populateData();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Card background color matching the design
        setBackground(new Color(222, 184, 135)); // Lighter brownish color
        setFocusable(false);
        setRequestFocusEnabled(false);
        setRoundTopLeft(20);
        setRoundTopRight(20);
        setRoundBottomLeft(20);
        setRoundBottomRight(20);
        setShadowSize(10);
        setShadowOpacity(0.3f);
        setShadowColor(new Color(0, 0, 0, 80));

        // Vertical card dimensions
        setPreferredSize(new Dimension(320, 380));
        setMaximumSize(new Dimension(320, 380));
        setMinimumSize(new Dimension(320, 380));
        

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(280, 180));
        imageLabel.setBorder(null);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBackground(new Color(222, 184, 135));
        imageLabel.setOpaque(false);
        
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(new Color(101, 67, 33));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        infoLabel.setForeground(new Color(139, 125, 107));
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        ratingLabel = new JLabel();
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ratingLabel.setForeground(new Color(255, 215, 0)); // Gold color for stars
        ratingLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        reviewsLabel = new JLabel();
        reviewsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        reviewsLabel.setForeground(new Color(139, 125, 107));
        reviewsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        priceLabel.setForeground(new Color(101, 67, 33));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    
    private void setupLayout() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(222, 184, 135));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Image panel at the top
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(222, 184, 135));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setPreferredSize(new Dimension(290, 190));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        // Info panel at the bottom
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(222, 184, 135));
        infoPanel.setBorder(null);

        // Name and info section
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(infoLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        
        // Rating section
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(reviewsLabel);
        infoPanel.add(Box.createVerticalGlue());
        
        // Price at the bottom
        infoPanel.add(priceLabel);

        contentPanel.add(imagePanel, BorderLayout.NORTH);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void populateData() {
        if (menuData != null) {
            nameLabel.setText(menuData.getItemName());
            infoLabel.setText("info"); // Static text as shown in design
            
            // Create star rating display
            int rating = (int) Math.round(menuData.getRating());
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                if (i < rating) {
                    stars.append("★");
                } else {
                    stars.append("☆");
                }
            }
            ratingLabel.setText(stars.toString());
            
            reviewsLabel.setText("Reviews");
            priceLabel.setText(String.format("Rs. %.0f", menuData.getItemPrice()));
            
            // Image loading with rounded corners effect
            if (menuData.getItemImage() != null && menuData.getItemImage().length > 0) {
                ImageIcon icon = new ImageIcon(menuData.getItemImage());
                Image scaled = icon.getImage().getScaledInstance(270, 170, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("No Image");
                imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                imageLabel.setForeground(new Color(139, 125, 107));
            }
        }
    }
    
    public MenuData getRestaurantData() {
        return menuData;
    }
}