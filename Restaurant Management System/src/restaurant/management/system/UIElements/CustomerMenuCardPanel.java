/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.UIElements;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import restaurant.management.system.model.MenuData;
/**
 *
 * @author labis
 */
public class CustomerMenuCardPanel extends JPanel {
    private final MenuData menuData;
    private final int cornerRadius = 25;
    private final int shadowSize = 6;
    private final Color shadowColor = new Color(0, 0, 0, 40);
    
    private int cardWidth = 280;
    private int cardHeight = 250;
    
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel infoLabel;
    private JLabel ratingLabel;
    private JLabel reviewsLabel;
    private JLabel priceLabel;
    private JSpinner quantitySpinner;
    private JButton addToCartButton;
    
    public CustomerMenuCardPanel(MenuData menu) {
        this.menuData = menu;
        setOpaque(false);
        setLayout(new BorderLayout());
        initComponents();
        setupLayout();
        populateData();
    }
    
    
    public CustomerMenuCardPanel(MenuData menu, int width, int height) {
        this.menuData = menu;
        this.cardWidth = width;
        this.cardHeight = height;
        setOpaque(false);
        setLayout(new BorderLayout());
        initComponents();
        setupLayout();
        populateData();
    }
    
     @Override
    public Dimension getPreferredSize() {
        return new Dimension(cardWidth, cardHeight);
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(cardWidth, cardHeight);
    }
    
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(cardWidth, cardHeight);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Draw shadow
        g2d.setColor(shadowColor);
        g2d.fill(new RoundRectangle2D.Double(
            shadowSize, shadowSize, 
            width - shadowSize, height - shadowSize, 
            cornerRadius, cornerRadius
        ));
        
        // Draw card
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(
            0, 0, 
            width - shadowSize, height - shadowSize, 
            cornerRadius, cornerRadius
        ));
        
        g2d.dispose();
    }
    
    private void initComponents() {
        setBackground(new Color(245, 220, 180));
        
        // Image label
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 60));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        // Text labels
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(new Color(60, 40, 20));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        infoLabel.setForeground(new Color(100, 80, 60));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        ratingLabel = new JLabel();
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ratingLabel.setForeground(new Color(255, 165, 0));
        ratingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        reviewsLabel = new JLabel();
        reviewsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        reviewsLabel.setForeground(new Color(100, 80, 60));
        reviewsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(new Color(60, 40, 20));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Quantity spinner
        quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) quantitySpinner.getEditor();
        editor.getTextField().setEditable(false);
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        quantitySpinner.setPreferredSize(new Dimension(50, 25));
        
        
    }
    
    private void setupLayout() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15 + shadowSize, 15 + shadowSize));
        
        // Image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setAlignmentX(CENTER_ALIGNMENT);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        // Add components with proper spacing
        infoPanel.add(Box.createVerticalStrut(1));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(0));
        
        // Info with icon
        JPanel infoWithIcon = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoWithIcon.setOpaque(false);
        JLabel infoIcon = new JLabel("ⓘ ");
        infoIcon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoWithIcon.add(infoIcon);
        infoWithIcon.add(infoLabel);
        infoPanel.add(infoWithIcon);
        infoPanel.add(Box.createVerticalStrut(0));
        
        // Rating
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createVerticalStrut(0));
        
        // Reviews
        infoPanel.add(reviewsLabel);
        infoPanel.add(Box.createVerticalStrut(0));
        
        // Price
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(0));
        
        // Quantity and button
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(new JLabel("Qty: "));
        actionPanel.add(quantitySpinner);
        
        infoPanel.add(actionPanel);
        
        // Add all components to main panel
        contentPanel.add(imagePanel);
        contentPanel.add(infoPanel);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void populateData() {
        if (menuData == null) return;
        
        nameLabel.setText(menuData.getItemName());
        infoLabel.setText(menuData.getItemDescription());
        
        // Rating stars
        int rating = (int) Math.round(menuData.getRating());
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stars.append(i < rating ? "★" : "☆");
        }
        ratingLabel.setText(stars.toString());
        
        reviewsLabel.setText("(" + menuData.getReviews() + " reviews)");
        priceLabel.setText(String.format("Rs. %.2f", menuData.getItemPrice()));
        
        // Load image
        try {
            if (menuData.getItemImage() != null && menuData.getItemImage().length > 0) {
                ImageIcon icon = new ImageIcon(menuData.getItemImage());
                if (icon.getIconWidth() > 0) {
                    Image scaled = icon.getImage().getScaledInstance(150, 60, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaled));
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        
        // Fallback if no image
        imageLabel.setIcon(null);
        imageLabel.setText("No Image");
        imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        imageLabel.setForeground(new Color(100, 80, 60));
    }
    
    public void setCardSize(int width, int height) {
        this.cardWidth = width;
        this.cardHeight = height;
        
        // Update image size proportionally
        int imageWidth = (int) (cardWidth * 0.43);
        int imageHeight = (int) (cardHeight * 0.32);
        imageLabel.setPreferredSize(new Dimension(imageWidth, imageHeight));
        
        // Reload image with new size if available
        if (menuData != null && menuData.getItemImage() != null && menuData.getItemImage().length > 0) {
            try {
                ImageIcon icon = new ImageIcon(menuData.getItemImage());
                if (icon.getIconWidth() > 0) {
                    Image scaled = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaled));
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }
        
        // Force layout update
        revalidate();
        repaint();
    }
    
    public JButton getAddToCartButton() {
        return addToCartButton;
    }
    
    public JSpinner getQuantitySpinner() {
        return quantitySpinner;
    }
    
    public MenuData getMenuData() {
        return menuData;
    }
}
