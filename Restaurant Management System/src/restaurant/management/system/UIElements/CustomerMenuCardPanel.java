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
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import restaurant.management.system.dao.ReviewDao;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.model.ReviewData;
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
    
    // Database access
    private ReviewDao reviewDao;
    
    // Customer information (you might want to get this from a session or login system)
    private String currentCustomerEmail = "customer@example.com"; // Default - should be set from your login system
    private String currentCustomerName = "Current Customer"; // Default - should be set from your login system
    
    public CustomerMenuCardPanel(MenuData menu) {
        this.menuData = menu;
        this.reviewDao = new ReviewDao();
        setOpaque(false);
        setLayout(new BorderLayout());
        initComponents();
        setupLayout();
        populateData();
        setupEventHandlers();
    }
    
    public CustomerMenuCardPanel(MenuData menu, int width, int height) {
        this.menuData = menu;
        this.cardWidth = width;
        this.cardHeight = height;
        this.reviewDao = new ReviewDao();
        setOpaque(false);
        setLayout(new BorderLayout());
        initComponents();
        setupLayout();
        populateData();
        setupEventHandlers();
    }
    
    // Method to set current customer info (call this after login)
    public void setCurrentCustomer(String email, String name) {
        this.currentCustomerEmail = email;
        this.currentCustomerName = name;
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
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
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
        // Make reviews label clickable
        reviewsLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reviewsLabel.setToolTipText("Click to view and add reviews");
        
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

        // Add to cart Button
        addToCartButton = new JButton("+"
                +"to Cart");
        addToCartButton.setFont(new Font("Segoe UI", Font.BOLD, 8));
        addToCartButton.setBackground(new Color(227, 143, 11));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setFocusPainted(false);
        addToCartButton.setBorderPainted(false);
        addToCartButton.setPreferredSize(new Dimension(40, 30));
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
                reviewsLabel.setForeground(new Color(80, 60, 40)); // Darker on hover
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                reviewsLabel.setForeground(new Color(100, 80, 60)); // Original color
            }
        });
    }
    
    private void showReviewsDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog reviewDialog = new JDialog(parentFrame, "Reviews - " + menuData.getItemName(), true);
        reviewDialog.setSize(600, 500);
        reviewDialog.setLocationRelativeTo(this);
        reviewDialog.setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Reviews for " + menuData.getItemName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        
        // Reviews display panel
        JPanel reviewsDisplayPanel = new JPanel();
        reviewsDisplayPanel.setLayout(new BoxLayout(reviewsDisplayPanel, BoxLayout.Y_AXIS));
        reviewsDisplayPanel.setBorder(BorderFactory.createTitledBorder("Customer Reviews"));
        
        // Load existing reviews from database
        loadReviewsFromDatabase(reviewsDisplayPanel);
        
        JScrollPane reviewsScrollPane = new JScrollPane(reviewsDisplayPanel);
        reviewsScrollPane.setPreferredSize(new Dimension(550, 250));
        reviewsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add review panel
        JPanel addReviewPanel = createAddReviewPanel(reviewDialog, reviewsDisplayPanel);
        
        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(reviewsScrollPane, BorderLayout.CENTER);
        mainPanel.add(addReviewPanel, BorderLayout.SOUTH);
        
        reviewDialog.add(mainPanel);
        reviewDialog.setVisible(true);
    }
    
    private void loadReviewsFromDatabase(JPanel reviewsPanel) {
        reviewsPanel.removeAll(); // Clear existing reviews
        
        List<ReviewData> reviews = reviewDao.getReviewsByItemId(menuData.getItemId());
        
        if (reviews.isEmpty()) {
            JLabel noReviewsLabel = new JLabel("No reviews yet. Be the first to review!");
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
        ratingStars.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
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
        
        // Add edit/delete buttons if this is the current customer's review
        if (review.getCustomerEmail().equals(currentCustomerEmail)) {
            JButton editButton = new JButton("Edit");
            editButton.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            editButton.setBackground(new Color(50, 150, 250));
            editButton.setForeground(Color.WHITE);
            editButton.setFocusPainted(false);
            editButton.setBorderPainted(false);
            editButton.setPreferredSize(new Dimension(50, 25));
            
            JButton deleteButton = new JButton("Delete");
            deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            deleteButton.setBackground(new Color(220, 50, 50));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFocusPainted(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setPreferredSize(new Dimension(60, 25));
            
            editButton.addActionListener(e -> editReview(review));
            deleteButton.addActionListener(e -> deleteReview(review));
            
            customerPanel.add(Box.createHorizontalStrut(20));
            customerPanel.add(editButton);
            customerPanel.add(Box.createHorizontalStrut(5));
            customerPanel.add(deleteButton);
        }
        
        // Comment label
        JLabel commentLabel = new JLabel("<html><p style='width:400px'>" + review.getComment() + "</p></html>");
        commentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        commentLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        reviewPanel.add(customerPanel, BorderLayout.NORTH);
        reviewPanel.add(commentLabel, BorderLayout.CENTER);
        
        return reviewPanel;
    }
    
    private void editReview(ReviewData review) {
        JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Edit Review", true);
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Rating panel
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.add(new JLabel("Your Rating: "));
        
        // Star rating buttons
        JButton[] starButtons = new JButton[5];
        final int[] selectedRating = {review.getRating()};
        
        for (int i = 0; i < 5; i++) {
            final int starIndex = i;
            starButtons[i] = new JButton(i < review.getRating() ? "★" : "☆");
            starButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            starButtons[i].setForeground(i < review.getRating() ? new Color(255, 165, 0) : new Color(200, 200, 200));
            starButtons[i].setBackground(Color.WHITE);
            starButtons[i].setBorderPainted(false);
            starButtons[i].setFocusPainted(false);
            starButtons[i].setPreferredSize(new Dimension(30, 30));
            
            starButtons[i].addActionListener(e -> {
                selectedRating[0] = starIndex + 1;
                // Update star display
                for (int j = 0; j < 5; j++) {
                    if (j <= starIndex) {
                        starButtons[j].setText("★");
                        starButtons[j].setForeground(new Color(255, 165, 0));
                    } else {
                        starButtons[j].setText("☆");
                        starButtons[j].setForeground(new Color(200, 200, 200));
                    }
                }
            });
            
            ratingPanel.add(starButtons[i]);
        }
        
        // Comment panel
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.add(new JLabel("Your Comment:"), BorderLayout.NORTH);
        
        JTextArea commentArea = new JTextArea(review.getComment(), 5, 30);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane commentScrollPane = new JScrollPane(commentArea);
        commentPanel.add(commentScrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton updateButton = new JButton("Update Review");
        updateButton.setBackground(new Color(227, 143, 11));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(150, 150, 150));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        
        updateButton.addActionListener(e -> {
            if (selectedRating[0] > 0 && !commentArea.getText().trim().isEmpty()) {
                // Update the review
                review.setRating(selectedRating[0]);
                review.setComment(commentArea.getText().trim());
                
                if (reviewDao.updateReview(review)) {
                    // Update the reviews display on the card
                    updateReviewsDisplay();
                    
                    JOptionPane.showMessageDialog(editDialog, 
                        "Review updated successfully!", 
                        "Review Updated", 
                        JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                    
                    // Refresh the parent reviews dialog
                    SwingUtilities.getWindowAncestor(editDialog).dispose();
                    showReviewsDialog();
                } else {
                    JOptionPane.showMessageDialog(editDialog, 
                        "Failed to update review. Please try again.", 
                        "Update Failed", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(editDialog, 
                    "Please provide both rating and comment.", 
                    "Incomplete Review", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> editDialog.dispose());
        
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        
        // Assemble dialog
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(ratingPanel, BorderLayout.NORTH);
        topPanel.add(commentPanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        editDialog.add(mainPanel);
        editDialog.setVisible(true);
    }
    
    private void deleteReview(ReviewData review) {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete your review?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            if (reviewDao.deleteReview(review.getReviewId(), review.getCustomerEmail())) {
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
    
    private JPanel createAddReviewPanel(JDialog parentDialog, JPanel reviewsDisplayPanel) {
        JPanel addReviewPanel = new JPanel(new BorderLayout());
        addReviewPanel.setBorder(BorderFactory.createTitledBorder("Add Your Review"));
        
        // Check if customer has already reviewed this item
        boolean hasReviewed = reviewDao.hasCustomerReviewed(menuData.getItemId(), currentCustomerEmail);
        
        if (hasReviewed) {
            JLabel alreadyReviewedLabel = new JLabel("You have already reviewed this item. You can edit or delete your review above.");
            alreadyReviewedLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            alreadyReviewedLabel.setHorizontalAlignment(SwingConstants.CENTER);
            alreadyReviewedLabel.setForeground(Color.GRAY);
            addReviewPanel.add(alreadyReviewedLabel, BorderLayout.CENTER);
            return addReviewPanel;
        }
        
        // Rating panel
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.add(new JLabel("Your Rating: "));
        
        // Star rating buttons
        JButton[] starButtons = new JButton[5];
        final int[] selectedRating = {0};
        
        for (int i = 0; i < 5; i++) {
            final int starIndex = i;
            starButtons[i] = new JButton("☆");
            starButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            starButtons[i].setForeground(new Color(200, 200, 200));
            starButtons[i].setBackground(Color.WHITE);
            starButtons[i].setBorderPainted(false);
            starButtons[i].setFocusPainted(false);
            starButtons[i].setPreferredSize(new Dimension(30, 30));
            
            starButtons[i].addActionListener(e -> {
                selectedRating[0] = starIndex + 1;
                // Update star display
                for (int j = 0; j < 5; j++) {
                    if (j <= starIndex) {
                        starButtons[j].setText("★");
                        starButtons[j].setForeground(new Color(255, 165, 0));
                    } else {
                        starButtons[j].setText("☆");
                        starButtons[j].setForeground(new Color(200, 200, 200));
                    }
                }
            });
            
            ratingPanel.add(starButtons[i]);
        }
        
        // Comment panel
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.add(new JLabel("Your Comment:"), BorderLayout.NORTH);
        
        JTextArea commentArea = new JTextArea(3, 30);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBorder(BorderFactory.createLoweredBevelBorder());
        JScrollPane commentScrollPane = new JScrollPane(commentArea);
        commentPanel.add(commentScrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit Review");
        submitButton.setBackground(new Color(227, 143, 11));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(150, 150, 150));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        
        submitButton.addActionListener(e -> {
            if (selectedRating[0] > 0 && !commentArea.getText().trim().isEmpty()) {
                // Create new review
                ReviewData newReview = new ReviewData(
                    menuData.getItemId(),
                    currentCustomerName,
                    currentCustomerEmail,
                    selectedRating[0],
                    commentArea.getText().trim()
                );
                
                if (reviewDao.addReview(newReview)) {
                    // Update the reviews display on the card
                    updateReviewsDisplay();
                    
                    // Refresh the reviews display in the dialog
                    loadReviewsFromDatabase(reviewsDisplayPanel);
                    
                    // Show confirmation and close dialog
                    JOptionPane.showMessageDialog(parentDialog, 
                        "Thank you for your review!\nRating: " + selectedRating[0] + " stars",
                        "Review Submitted", 
                        JOptionPane.INFORMATION_MESSAGE);
                    parentDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(parentDialog, 
                        "Failed to submit review. You may have already reviewed this item.", 
                        "Submission Failed", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(parentDialog, 
                    "Please provide both rating and comment.", 
                    "Incomplete Review", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> parentDialog.dispose());
        
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        
        // Assemble add review panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(ratingPanel, BorderLayout.NORTH);
        topPanel.add(commentPanel, BorderLayout.CENTER);
        
        addReviewPanel.add(topPanel, BorderLayout.CENTER);
        addReviewPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        return addReviewPanel;
    }
    
    private void updateReviewsDisplay() {
        // Get updated statistics from database
        double avgRating = reviewDao.getAverageRating(menuData.getItemId());
        int reviewCount = reviewDao.getReviewCount(menuData.getItemId());
        
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
        actionPanel.add(addToCartButton);
        
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
        
        // Update reviews display
        updateReviewsDisplay();
        
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

    public void setQuantity(int quantity) {
        if (quantitySpinner != null) {
            quantitySpinner.setValue(Math.max(0, quantity));
        }
    }

    public int getQuantity() {
        if (quantitySpinner != null) {
            return (Integer) quantitySpinner.getValue();
        }
        return 0;
    }
    
    public void resetCard() {
        setQuantity(0);
    }

    public void setCardEnabled(boolean enabled) {
        if (quantitySpinner != null) {
            quantitySpinner.setEnabled(enabled);
        }
        if (addToCartButton != null) {
            addToCartButton.setEnabled(enabled);
        }
    }
}
