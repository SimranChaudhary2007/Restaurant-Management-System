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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import restaurant.management.system.dao.SuggestionDao;
import restaurant.management.system.model.SuggestionData;

/**
 *
 * @author acer
 */
public class SuggestionPanel extends JDialog {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JPanel suggestionsContainer;
    private JLabel titleLabel;
    private JLabel countLabel;
    private JButton refreshButton;
    private JButton markAllReadButton;
    private SuggestionDao suggestionDao;
    private List<SuggestionData> suggestions;
    private Runnable onUpdate; // Callback to update notification badge
    
    public SuggestionPanel(Frame parent, Runnable onUpdate) {
        super(parent, "Customer Suggestions", true);
        this.suggestionDao = new SuggestionDao();
        this.onUpdate = onUpdate;
        initializeComponents();
        setupLayout();
        loadSuggestions();
        setupEventHandlers();
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        setSize(800, 600);
        setResizable(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header components
        titleLabel = new JLabel("Customer Suggestions");
        titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
        titleLabel.setForeground(new Color(80, 50, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        countLabel = new JLabel();
        countLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        countLabel.setForeground(new Color(100, 70, 50));
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Buttons
        refreshButton = createStyledButton("🔄 Refresh", new Color(70, 130, 180));
        markAllReadButton = createStyledButton("✓ Mark All Read", new Color(34, 139, 34));
        
        // Suggestions container
        suggestionsContainer = new JPanel();
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        suggestionsContainer.setBackground(new Color(245, 245, 245));
        
        scrollPane = new JScrollPane(suggestionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 35));
        
        // Hover effect
        Color originalColor = backgroundColor;
        Color hoverColor = backgroundColor.darker();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
//        JPanel titlePanel = new JPanel(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
//        titlePanel.setBackground(new Color(245, 245, 245));
//        titlePanel.add(titleLabel);
//        titlePanel.add(Box.createVerticalStrut(5));
//        titlePanel.add(countLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(refreshButton);
        buttonPanel.add(markAllReadButton);
        
//        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Main content
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void setupEventHandlers() {
        refreshButton.addActionListener(e -> {
            loadSuggestions();
            if (onUpdate != null) onUpdate.run();
        });
        
        markAllReadButton.addActionListener(e -> {
            if (suggestionDao.markSuggestionsAsRead()) {
                loadSuggestions();
                if (onUpdate != null) onUpdate.run();
                JOptionPane.showMessageDialog(this, 
                    "All suggestions marked as read!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to mark suggestions as read.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void loadSuggestions() {
        suggestions = suggestionDao.getAllSuggestions();
        updateSuggestionsDisplay();
        updateCountLabel();
    }
    
    private void updateCountLabel() {
        int total = suggestions.size();
        int unread = (int) suggestions.stream().mapToInt(s -> s.isRead() ? 0 : 1).sum();
        countLabel.setText(String.format("Total: %d suggestions (%d unread)", total, unread));
    }
    
    private void updateSuggestionsDisplay() {
        suggestionsContainer.removeAll();
        
        if (suggestions.isEmpty()) {
            JLabel noSuggestionsLabel = new JLabel("No suggestions yet.");
            noSuggestionsLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
            noSuggestionsLabel.setForeground(new Color(150, 150, 150));
            noSuggestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noSuggestionsLabel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
            suggestionsContainer.add(noSuggestionsLabel);
        } else {
            for (SuggestionData suggestion : suggestions) {
                suggestionsContainer.add(createSuggestionCard(suggestion));
                suggestionsContainer.add(Box.createVerticalStrut(10));
            }
        }
        
        suggestionsContainer.revalidate();
        suggestionsContainer.repaint();
    }
    
    private JPanel createSuggestionCard(SuggestionData suggestion) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(suggestion.isRead() ? Color.WHITE : new Color(255, 248, 220));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(suggestion.isRead() ? 
                new Color(200, 200, 200) : new Color(255, 165, 0), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, card.getPreferredSize().height));
        
        // Header with customer info and timestamp
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(card.getBackground());
        
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        customerPanel.setBackground(card.getBackground());
        
        JLabel customerLabel = new JLabel("👤 " + suggestion.getCustomerName());
        customerLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        customerLabel.setForeground(new Color(80, 50, 30));
        
        JLabel restaurantLabel = new JLabel(" • " + suggestion.getRestaurantName());
        restaurantLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        restaurantLabel.setForeground(new Color(100, 70, 50));
        
        customerPanel.add(customerLabel);
        customerPanel.add(restaurantLabel);
        
        JLabel timestampLabel = new JLabel(suggestion.getCreatedAt().format(
            DateTimeFormatter.ofPattern("MMM dd, yyyy • hh:mm a")));
        timestampLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        timestampLabel.setForeground(new Color(120, 120, 120));
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        statusPanel.setBackground(card.getBackground());
        
        if (!suggestion.isRead()) {
            JLabel newLabel = new JLabel("🔴 NEW");
            newLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 10));
            newLabel.setForeground(Color.RED);
            statusPanel.add(newLabel);
            statusPanel.add(Box.createHorizontalStrut(10));
        }
        statusPanel.add(timestampLabel);
        
        headerPanel.add(customerPanel, BorderLayout.WEST);
        headerPanel.add(statusPanel, BorderLayout.EAST);
        
        // Suggestion text
        JTextArea suggestionTextArea = new JTextArea(suggestion.getSuggestionText());
        suggestionTextArea.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        suggestionTextArea.setForeground(new Color(60, 60, 60));
        suggestionTextArea.setBackground(card.getBackground());
        suggestionTextArea.setEditable(false);
        suggestionTextArea.setLineWrap(true);
        suggestionTextArea.setWrapStyleWord(true);
        suggestionTextArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        actionPanel.setBackground(card.getBackground());
        
        JButton deleteButton = new JButton("🗑️ Delete");
        deleteButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 11));
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(80, 25));
        
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
        
        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this suggestion?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
                if (suggestionDao.deleteSuggestion(suggestion.getSuggestionId())) {
                    loadSuggestions();
                    if (onUpdate != null) onUpdate.run();
                    JOptionPane.showMessageDialog(this,
                        "Suggestion deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to delete suggestion.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        actionPanel.add(deleteButton);
        
        // Assemble card
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(suggestionTextArea, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.SOUTH);
        
        return card;
    }
}