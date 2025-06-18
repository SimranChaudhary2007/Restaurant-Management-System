/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.model.MenuData;
    

/**
 *
 * @author pradeepta 3434
 */
public class AdminMenuController {
    private AdminMenuView adminMenuView;
    private MenuDao menuDao;
    
    private List<MenuData> allMenu;
    private List<MenuData> filteredMenu;
    private MenuData currentMenuItem; // For tracking selected item during updates
    
    public AdminMenuController(AdminMenuView view){
        this.adminMenuView = view;
        this.menuDao = new MenuDao();
        this.allMenu = new ArrayList<>();
        this.filteredMenu = new ArrayList<>();
        
        initializeEventListeners();
        setupNavigationListeners();
        loadMenuItems();
    }
    
    private void initializeEventListeners() {
        // Add button - for adding new items
        adminMenuView.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdatePopup(true); // true = add mode
            }
        });
        
        // Update button - for editing existing items
        adminMenuView.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdatePopup.open();
            }
        });
        
        // Delete button - for removing items
        adminMenuView.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMenuItem == null) {
                    JOptionPane.showMessageDialog(adminMenuView, 
                        "Please select a menu item first!", 
                        "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                int choice = JOptionPane.showConfirmDialog(adminMenuView,
                    "Are you sure you want to delete '" + currentMenuItem.getItemName() + "'?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                
                if (choice == JOptionPane.YES_OPTION) {
                    handleDeleteItem();
                }
            }
        });
    }
    
    private void setupNavigationListeners() {
        this.adminMenuView.hotBeveragesNavigation(
            new hotBeveragesNav(
                adminMenuView.getCoffeeIcon(), 
                adminMenuView.getMenuTabbedPane()
            )
        );
        this.adminMenuView.coldBeveragesNavigation(
            new coldBeveragesNav(
                adminMenuView.getDrinksIcon(), 
                adminMenuView.getMenuTabbedPane()
            )
        );
        this.adminMenuView.momoNavigation(
            new momoNav(
                adminMenuView.getMomoIcon(), 
                adminMenuView.getMenuTabbedPane()
            )
        );
        
        this.adminMenuView.pizzaNavigation(
            new pizzaNav(
                adminMenuView.getPizzaIcon(), 
                adminMenuView.getMenuTabbedPane()
            )
        );
        
        this.adminMenuView.burgerNavigation(
            new burgerNav(
                adminMenuView.getBurgerIcon(), // Fixed: should be getBurgerIcon, not getPizzaIcon
                adminMenuView.getMenuTabbedPane()
            )
        );
        
        this.adminMenuView.spaghettiNavigation(
            new spaghettiNav(
                adminMenuView.getSpaghettiIcon(), 
                adminMenuView.getMenuTabbedPane()
            )
        );
    }
    
    private void showUpdatePopup(boolean isAddMode) {
        String title = isAddMode ? "Add New Menu Item" : "Update Menu Item";
        JDialog popup = new JDialog(adminMenuView, title, true);
        popup.setSize(500, 550);
        popup.setLocationRelativeTo(adminMenuView);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setResizable(true);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(241, 237, 238));
        
        // Title label
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        titleLabel.setForeground(new Color(227, 143, 11));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(241, 237, 238));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Item Name
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(nameField, gbc);
        
        // Item Price
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        JLabel priceLabel = new JLabel("Price (Rs.):");
        priceLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        formPanel.add(priceLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField priceField = new JTextField(20);
        priceField.setFont(new Font("Arial", Font.PLAIN, 14));
        priceField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        formPanel.add(priceField, gbc);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        formPanel.add(categoryLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] categories = {"Coffee", "Drinks", "Momo", "Pizza", "Burger", "Ramen", "Chowmin"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryCombo.setBorder(BorderFactory.createLineBorder(new Color(227, 143, 11), 2));
        formPanel.add(categoryCombo, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        formPanel.add(descLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(250, 80));
        formPanel.add(descScroll, gbc);
        
        // Image section
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        formPanel.add(imageLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imagePanel.setBackground(new Color(241, 237, 238));
        
        JLabel imagePreview = new JLabel("No image selected");
        imagePreview.setPreferredSize(new Dimension(100, 100));
        imagePreview.setBorder(BorderFactory.createLineBorder(new Color(227, 143, 11), 2));
        imagePreview.setHorizontalAlignment(JLabel.CENTER);
        
        JButton selectImageBtn = new JButton("Select Image");
        selectImageBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        selectImageBtn.setBackground(new Color(227, 143, 11));
        selectImageBtn.setForeground(Color.WHITE);
        selectImageBtn.setFocusPainted(false);
        
        // Image selection variables
        final String[] selectedImagePath = {null};
        
        selectImageBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
            
            int result = fileChooser.showOpenDialog(popup);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath[0] = selectedFile.getAbsolutePath();
                
                // Show image preview
                ImageIcon icon = new ImageIcon(selectedImagePath[0]);
                ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(
                    100, 100, java.awt.Image.SCALE_SMOOTH));
                imagePreview.setIcon(scaledIcon);
                imagePreview.setText("");
            }
        });
        
        imagePanel.add(imagePreview);
        imagePanel.add(selectImageBtn);
        formPanel.add(imagePanel, gbc);
        
        // Pre-populate fields if updating existing item
        if (!isAddMode && currentMenuItem != null) {
            nameField.setText(currentMenuItem.getItemName());
            priceField.setText(String.valueOf(currentMenuItem.getItemPrice()));
            categoryCombo.setSelectedItem(currentMenuItem.getItemCategory());
            descArea.setText(currentMenuItem.getItemDescription());
            
            // Load existing image if available
            if (currentMenuItem.getItemImage() != null && currentMenuItem.getItemImage().length > 0) {
                // Since itemImage is byte[], we'll need to handle it differently
                // For now, we'll just show "Existing image" text
                imagePreview.setText("Existing image");
            }
        }
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(241, 237, 238));
        
        JButton saveButton = new JButton(isAddMode ? "Add Item" : "Update Item");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(227, 143, 11));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(120, 35));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(128, 128, 128));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(120, 35));
        
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String price = priceField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            String description = descArea.getText().trim();
            
            if (isAddMode) {
                handleAddItem(name, price, category, description, selectedImagePath[0]);
            } else {
                handleUpdateItem(name, price, category, description, selectedImagePath[0]);
            }
            popup.dispose();
        });
        
        cancelButton.addActionListener(e -> popup.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        popup.add(mainPanel);
        popup.setVisible(true);
    }
    
    private void handleAddItem(String name, String price, String category, String description, String imagePath) {
        // Validate input
        if (!validateInput(name, price)) {
            return;
        }
        
        try {
            byte[] imageBytes = null;
            if (imagePath != null && !imagePath.isEmpty()) {
                imageBytes = readImageToBytes(imagePath);
            }
            
            // Fixed: Use proper constructor with default values for rating and reviews
            MenuData newItem = new MenuData(
                imageBytes,
                name,
                category,
                Double.parseDouble(price),
                description,
                "0.0", // Default rating
                "0"    // Default reviews count
            );
            
            boolean success = menuDao.addMenuItem(newItem);
            
            if (success) {
                JOptionPane.showMessageDialog(adminMenuView, 
                    "Menu item added successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadMenuItems(); // Refresh the display
            } else {
                JOptionPane.showMessageDialog(adminMenuView, 
                    "Failed to add menu item. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminMenuView, 
                "Error adding menu item: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateItem(String name, String price, String category, String description, String imagePath) {
        // Validate input
        if (!validateInput(name, price)) {
            return;
        }
        
        try {
            currentMenuItem.setItemName(name);
            currentMenuItem.setItemPrice(Double.parseDouble(price));
            currentMenuItem.setItemCategory(category);
            currentMenuItem.setItemDescription(description);
            
            if (imagePath != null && !imagePath.isEmpty()) {
                byte[] imageBytes = readImageToBytes(imagePath);
                currentMenuItem.setItemImage(imageBytes);
            }
            
            boolean success = menuDao.updateMenuItem(currentMenuItem);
            
            if (success) {
                JOptionPane.showMessageDialog(adminMenuView, 
                    "Menu item updated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadMenuItems(); // Refresh the display
            } else {
                JOptionPane.showMessageDialog(adminMenuView, 
                    "Failed to update menu item. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminMenuView, 
                "Error updating menu item: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleDeleteItem() {
        try {
            boolean success = menuDao.deleteMenuItem(currentMenuItem.getItemId());
            
            if (success) {
                JOptionPane.showMessageDialog(adminMenuView, 
                    "Menu item deleted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                currentMenuItem = null; // Clear selection
                loadMenuItems(); // Refresh the display
            } else {
                JOptionPane.showMessageDialog(adminMenuView, 
                    "Failed to delete menu item. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminMenuView, 
                "Error deleting menu item: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper method to read image file to byte array
    private byte[] readImageToBytes(String imagePath) throws IOException {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return null;
        }
        return Files.readAllBytes(imageFile.toPath());
    }
    
    private boolean validateInput(String name, String price) {
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(adminMenuView, "Please enter item name!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (price.isEmpty()) {
            JOptionPane.showMessageDialog(adminMenuView, "Please enter item price!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double priceValue = Double.parseDouble(price);
            if (priceValue <= 0) {
                JOptionPane.showMessageDialog(adminMenuView, "Price must be greater than 0!", 
                                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(adminMenuView, "Please enter a valid price!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public void setCurrentMenuItem(MenuData item) {
        this.currentMenuItem = item;
    }
    
    public void open(){
        this.adminMenuView.setVisible(true);
    }
    
    public void close(){
        this.adminMenuView.dispose();
    }
    
    // Navigation listener classes
    class hotBeveragesNav implements MouseListener{
        
        private JLabel coffeeIcon;
        private JTabbedPane menuTabbedPane;
        
        public hotBeveragesNav(JLabel label, JTabbedPane menuTabbedPane) {
            this.coffeeIcon = label;
            this.menuTabbedPane = menuTabbedPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            menuTabbedPane.setSelectedIndex(0);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            coffeeIcon.setForeground(Color.WHITE);
            coffeeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            coffeeIcon.setForeground(Color.LIGHT_GRAY);
            coffeeIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class coldBeveragesNav implements MouseListener{
        
        private JLabel drinksIcon;
        private JTabbedPane menuTabbedPane;
        
        public coldBeveragesNav(JLabel label, JTabbedPane menuTabbedPane) {
            this.drinksIcon = label;
            this.menuTabbedPane = menuTabbedPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            menuTabbedPane.setSelectedIndex(1);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            drinksIcon.setForeground(Color.WHITE);
            drinksIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            drinksIcon.setForeground(Color.LIGHT_GRAY);
            drinksIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class momoNav implements MouseListener{
        
        private JLabel momoIcon;
        private JTabbedPane menuTabbedPane;
        
        public momoNav(JLabel label, JTabbedPane menuTabbedPane) {
            this.momoIcon = label;
            this.menuTabbedPane = menuTabbedPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            menuTabbedPane.setSelectedIndex(2);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            momoIcon.setForeground(Color.WHITE);
            momoIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            momoIcon.setForeground(Color.LIGHT_GRAY);
            momoIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class pizzaNav implements MouseListener{
        
        private JLabel pizzaIcon;
        private JTabbedPane menuTabbedPane;
        
        public pizzaNav(JLabel label, JTabbedPane menuTabbedPane) {
            this.pizzaIcon = label;
            this.menuTabbedPane = menuTabbedPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            menuTabbedPane.setSelectedIndex(3);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            pizzaIcon.setForeground(Color.WHITE);
            pizzaIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            pizzaIcon.setForeground(Color.LIGHT_GRAY);
            pizzaIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class burgerNav implements MouseListener{
        
        private JLabel burgerIcon;
        private JTabbedPane menuTabbedPane;
        
        public burgerNav(JLabel label, JTabbedPane menuTabbedPane) {
            this.burgerIcon = label;
            this.menuTabbedPane = menuTabbedPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            menuTabbedPane.setSelectedIndex(4);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            burgerIcon.setForeground(Color.WHITE);
            burgerIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            burgerIcon.setForeground(Color.LIGHT_GRAY);
            burgerIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class spaghettiNav implements MouseListener{
        
        private JLabel spaghettiIcon;
        private JTabbedPane menuTabbedPane;
        
        public spaghettiNav(JLabel label, JTabbedPane menuTabbedPane) {
            this.spaghettiIcon = label;
            this.menuTabbedPane = menuTabbedPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            menuTabbedPane.setSelectedIndex(5);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            spaghettiIcon.setForeground(Color.WHITE);
            spaghettiIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            spaghettiIcon.setForeground(Color.LIGHT_GRAY);
            spaghettiIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private void loadMenuItems() {
        try {
            allMenu = menuDao.getAllMenuWithImages(); // Fixed: Changed from getAllMenuWithImages()
            filteredMenu = new ArrayList<>(allMenu);
            displayAllMenu();
        } catch (Exception e) {
            System.err.println("Error loading menu items: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void displayAllMenu() {
        adminMenuView.displayMenu(allMenu);
    }
    
    private void displayFilteredMenu() {
        adminMenuView.displayMenu(filteredMenu);
    }
    
    public void filterMenuByCategory(String category) {
        filteredMenu.clear();
        for (MenuData item : allMenu) {
            if (item.getItemCategory().equalsIgnoreCase(category)) {
                filteredMenu.add(item);
            }
        }
        displayFilteredMenu();
    }
    
    public void showAllMenu() {
        filteredMenu = new ArrayList<>(allMenu);
        displayAllMenu();
    }
}