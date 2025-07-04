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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
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
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.ReviewDao;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.ReviewData;

/**
 *
 * @author labish
 */
public class MenuCardPanel extends PanelShadow {
    private final MenuData menuData;
    private final int cornerRadius = 25;
    private final int shadowSize = 6;
    private final Color shadowColor = new Color(0, 0, 0, 40);
    
    private int cardWidth = 320;
    private int cardHeight = 300;
    
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel infoLabel;
    private JLabel ratingLabel;
    private JLabel reviewsLabel;
    private JLabel priceLabel;
    
    private ReviewDao reviewDao;
    private String currentOwnerEmail;
    private String currentOwnerName;

    public MenuCardPanel(MenuData menu) {
        this.menuData = menu;
        this.reviewDao = new ReviewDao();
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(cardWidth, cardHeight));
        setMaximumSize(new Dimension(cardWidth, cardHeight));
        setMinimumSize(new Dimension(cardWidth, cardHeight));
        initComponents();
        setupLayout();
        populateData();
        setupEventHandlers();
    }
    
    public MenuCardPanel(MenuData menu, int width, int height) {
        this.menuData = menu;
        this.cardWidth = width;
        this.cardHeight = height;
        this.reviewDao = new ReviewDao();
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(cardWidth, cardHeight));
        setMaximumSize(new Dimension(cardWidth, cardHeight));
        setMinimumSize(new Dimension(cardWidth, cardHeight));
        initComponents();
        setupLayout();
        populateData();
        setupEventHandlers();
    }
    
    public void setCurrentOwner(OwnerData owner) {
        if (owner != null) {
            this.currentOwnerEmail = owner.getEmail();
            this.currentOwnerName = owner.getFullName();
        } else {
            // Fallback values if owner is not logged in
            this.currentOwnerEmail = "admin@example.com";
            this.currentOwnerName = "Admin";
        }
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
    
    private void updateLabelSizes() {
        if (nameLabel != null) {
            nameLabel.setPreferredSize(new Dimension(cardWidth - 20, 30));
        }
    }
    
    private void initComponents() {
        setBackground(new Color(239,204,150));
        
        // Image label
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(180, 100));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        // Text labels
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(new Color(60, 40, 20));
        
        infoLabel = new JLabel("ⓘ info");
        infoLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        infoLabel.setForeground(new Color(100, 80, 60));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setToolTipText(menuData != null ? menuData.getItemDescription() : "");
        
        ratingLabel = new JLabel();
        ratingLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22)); // Changed to match customer
        ratingLabel.setForeground(new Color(255, 165, 0));
        
        reviewsLabel = new JLabel();
        reviewsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        reviewsLabel.setForeground(new Color(100, 80, 60));
        reviewsLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reviewsLabel.setToolTipText("Click to view reviews");
        
        priceLabel = new JLabel();
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(new Color(60, 40, 20));
    }
    
    private void setupEventHandlers() {
        // Add click listener to reviews label
        reviewsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showReviewsDialog();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                reviewsLabel.setForeground(new Color(255,51,0)); // Darker on hover
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                reviewsLabel.setForeground(new Color(100, 80, 60)); // Original color
            }
        });
        
        // Add hover effect for info label
        infoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                infoLabel.setForeground(new Color(255,51,0));
                infoLabel.setToolTipText(menuData != null ? menuData.getItemDescription() : "");
            }
                        
            @Override
            public void mouseExited(MouseEvent e) {
                infoLabel.setForeground(new Color(100, 80, 60)); // Original color
            }
        });
    }
    
    private void showReviewsDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog reviewDialog = new JDialog(parentFrame, "Reviews - " + menuData.getItemName(), true);
        reviewDialog.setBackground(new Color(239,204,150));
        reviewDialog.setSize(600, 500);
        reviewDialog.setLocationRelativeTo(this);
        reviewDialog.setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(239,204,150));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(241,237,238));
        JLabel titleLabel = new JLabel("Reviews for " + menuData.getItemName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        
        // Reviews display panel
        JPanel reviewsDisplayPanel = new JPanel();
        reviewsDisplayPanel.setBackground(new Color(241,237,238));
        reviewsDisplayPanel.setLayout(new BoxLayout(reviewsDisplayPanel, BoxLayout.Y_AXIS));
        reviewsDisplayPanel.setBorder(BorderFactory.createTitledBorder("Customer Reviews"));
        
        // Load existing reviews from database
        loadReviewsFromDatabase(reviewsDisplayPanel);
        
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsDisplayPanel);
        reviewsScrollPane.setPreferredSize(new Dimension(550, 350));
        reviewsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(reviewsScrollPane, BorderLayout.CENTER);
        
        reviewDialog.add(mainPanel);
        reviewDialog.setVisible(true);
    }
    
    private void loadReviewsFromDatabase(JPanel reviewsPanel) {
        reviewsPanel.removeAll(); // Clear existing reviews
        
        List<ReviewData> reviews = reviewDao.getReviewsByItemId(menuData.getItemId());
        
        if (reviews.isEmpty()) {
            JLabel noReviewsLabel = new JLabel("No reviews yet.");
            noReviewsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noReviewsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            reviewsPanel.add(noReviewsLabel);
            return;
        }
        
        for (ReviewData review : reviews) {
            JPanel reviewItem = createReviewItem(review);
            reviewsPanel.add(reviewItem);
            reviewsPanel.add(Box.createVerticalStrut(10));
        }
        
        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }
    
    private JPanel createReviewItem(ReviewData review) {
        JPanel reviewPanel = new JPanel(new BorderLayout());
        reviewPanel.setBorder(BorderFactory.createEtchedBorder());
        reviewPanel.setBackground(Color.WHITE);
        
        // Customer info panel
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(review.getCustomerName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Rating stars
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stars.append(i < review.getRating() ? "★" : "☆");
        }
        JLabel ratingStars = new JLabel(stars.toString());
        ratingStars.setForeground(new Color(255, 165, 0));
        ratingStars.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
        
        // Date label
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        JLabel dateLabel = new JLabel(dateFormat.format(review.getCreatedAt()));
        dateLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        dateLabel.setForeground(Color.GRAY);
        
        customerPanel.add(nameLabel);
        customerPanel.add(Box.createHorizontalStrut(10));
        customerPanel.add(ratingStars);
        customerPanel.add(Box.createHorizontalStrut(10));
        customerPanel.add(dateLabel);
        
        // Add delete button for admin
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        deleteButton.setBackground(new Color(220, 50, 50));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setPreferredSize(new Dimension(80, 25));
        
        deleteButton.addActionListener(e -> deleteReview(review));
        
        customerPanel.add(Box.createHorizontalStrut(20));
        customerPanel.add(deleteButton);
        
        // Comment label
        JLabel commentLabel = new JLabel("<html><p style='width:400px'>" + review.getComment() + "</p></html>");
        commentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        commentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        reviewPanel.add(customerPanel, BorderLayout.NORTH);
        reviewPanel.add(commentLabel, BorderLayout.CENTER);
        
        return reviewPanel;
    }
    
    private void deleteReview(ReviewData review) {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this review?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            if (reviewDao.deleteReview(review.getReviewId(),review.getCustomerEmail())) {
                // Update the reviews display on the card
                updateReviewsDisplay();
                
                JOptionPane.showMessageDialog(this, 
                    "Review deleted successfully!", 
                    "Review Deleted", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the reviews dialog
                SwingUtilities.getWindowAncestor(this).dispose();
                showReviewsDialog();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to delete review. Please try again.", 
                    "Delete Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateReviewsDisplay() {
        // Get updated statistics from database
        double avgRating = reviewDao.getAverageRating(menuData.getItemId());
        int reviewCount = reviewDao.getReviewCount(menuData.getItemId());
        ratingLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
        
        if (reviewCount == 0) {
            reviewsLabel.setText("(No reviews)");
            ratingLabel.setText("☆☆☆☆☆");
        } else {
            reviewsLabel.setText("(" + reviewCount + " review" + (reviewCount == 1 ? "" : "s") + ")");
            
            // Display average rating as stars
            int displayRating = (int) Math.round(avgRating);
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                stars.append(i < displayRating ? "★" : "☆");
            }
            ratingLabel.setText(stars.toString());
        }
        
        // Update menu data for consistency
        menuData.setRating(avgRating);
        menuData.setReviews(reviewCount + " review" + (reviewCount == 1 ? "" : "s"));
        
        // Force repaint
        revalidate();
        repaint();
    }
    
    private void setupLayout() {
        this.removeAll();
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15 + shadowSize, 15 + shadowSize));

        // Image panel (centered, like customer)
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(imagePanel);
        contentPanel.add(Box.createVerticalStrut(8));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(LEFT_ALIGNMENT);

        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(4));

        // Wrap infoLabel in a FlowLayout panel for icon/text alignment
        JPanel infoWithIcon = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        infoWithIcon.setOpaque(false);
        infoLabel.setAlignmentX(LEFT_ALIGNMENT);
        infoWithIcon.add(infoLabel);
        infoPanel.add(infoWithIcon);
        infoPanel.add(Box.createVerticalStrut(4));

        ratingLabel.setAlignmentX(LEFT_ALIGNMENT);
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createVerticalStrut(2));

        reviewsLabel.setAlignmentX(LEFT_ALIGNMENT);
        infoPanel.add(reviewsLabel);
        infoPanel.add(Box.createVerticalStrut(2));

        priceLabel.setAlignmentX(LEFT_ALIGNMENT);
        infoPanel.add(priceLabel);

        contentPanel.add(infoPanel);
        add(contentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void populateData() {
        if (menuData == null) return;
        
        String itemName = menuData.getItemName();
        nameLabel.setText("<html><div style='width:" + (cardWidth - 60) + "px'>" + itemName + "</div></html>");
        infoLabel.setText("\u24D8 info");
        infoLabel.setToolTipText(menuData.getItemDescription());
        
        // Rating stars (using Segoe UI Symbol font)
        int rating = (int) Math.round(menuData.getRating());
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stars.append(i < rating ? "\u2605" : "\u2606");
        }
        ratingLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
        ratingLabel.setText(stars.toString());
        
        reviewsLabel.setText("(" + menuData.getReviews() + ")");
        priceLabel.setText(String.format("Rs. %.2f", menuData.getItemPrice()));
        
        // Set image
        if (menuData.getItemImage() != null && menuData.getItemImage().length > 0) {
            ImageIcon icon = new ImageIcon(menuData.getItemImage());
            Image img = icon.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
            imageLabel.setText("");
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("No Image");
        }
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
        
        if (menuData != null) {
        String itemName = menuData.getItemName();
        nameLabel.setText("<html><div style='width:" + (cardWidth - 60) + "px'>" + itemName + "</div></html>");
    }
        
        // Force layout update
        updateLabelSizes();
        
        revalidate();
        repaint();
    }
    
    public MenuData getMenuData() {
        return menuData;
    }
}
