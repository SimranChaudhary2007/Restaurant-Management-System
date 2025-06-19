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
        
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        infoLabel.setForeground(new Color(100, 70, 50));
        
        ratingLabel = new JLabel();
        ratingLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        ratingLabel.setForeground(new Color(100, 70, 50));
        
        reviewsLabel = new JLabel();
        reviewsLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        reviewsLabel.setForeground(new Color(100, 70, 50));
        
        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
        priceLabel.setForeground(new Color(227, 143, 11));
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
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(infoLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(reviewsLabel);
        infoPanel.add(Box.createVerticalGlue());
        
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(priceLabel);

        contentPanel.add(imagePanel, BorderLayout.WEST);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void populateData() {
        if (menuData != null) {
            nameLabel.setText(menuData.getItemName());
            infoLabel.setText(menuData.getItemDescription());
            ratingLabel.setText("Rating: " + menuData.getRating());
            reviewsLabel.setText("Reviews: " + menuData.getReviews());
            priceLabel.setText(String.format("Rs. %.2f", menuData.getItemPrice()));
            
            // Image loading
            if (menuData.getItemImage() != null && menuData.getItemImage().length > 0) {
                ImageIcon icon = new ImageIcon(menuData.getItemImage());
                Image scaled = icon.getImage().getScaledInstance(250, 160, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("No Image");
            }
        }
    }
    
    public MenuData getRestaurantData() {
        return menuData;
    }
}        
