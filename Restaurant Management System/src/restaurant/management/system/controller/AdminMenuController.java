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
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.MenuData;

/**
 *
 * @author pradeepta 3434
 */
public class AdminMenuController {
    private AdminMenuView adminMenuView;
    
    public AdminMenuController(AdminMenuView view){
        this.adminMenuView = view;
        initializeEventListeners();
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
            adminMenuView.getPizzaIcon(), 
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
    
    private void initializeEventListeners() {
        // Update button - for editing existing items
        adminMenuView.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdatePopup(false); // false = update mode
            }
        });
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
//        if (!isAddMode && currentMenuItem != null) {
//            nameField.setText(currentMenuItem.getItemName());
//            priceField.setText(String.valueOf(currentMenuItem.getItemPrice()));
//            categoryCombo.setSelectedItem(currentMenuItem.getItemType());
//            descArea.setText(currentMenuItem.getItemDescription());
//            
//            // Load existing image if available
//            if (currentMenuItem.getImagePath() != null && !currentMenuItem.getImagePath().isEmpty()) {
//                selectedImagePath[0] = currentMenuItem.getImagePath();
//                try {
//                    ImageIcon icon = new ImageIcon(selectedImagePath[0]);
//                    ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(
//                        100, 100, java.awt.Image.SCALE_SMOOTH));
//                    imagePreview.setIcon(scaledIcon);
//                    imagePreview.setText("");
//                } catch (Exception ex) {
//                    imagePreview.setText("Image not found");
//                }
//            }
//        }
    }
    
    private void handleUpdateItem(String name, String price, String category, String description) {
        // Validate input
        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(adminMenuView, "Please enter item name!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (price.trim().isEmpty()) {
            JOptionPane.showMessageDialog(adminMenuView, "Please enter item price!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double priceValue = Double.parseDouble(price);
            if (priceValue <= 0) {
                JOptionPane.showMessageDialog(adminMenuView, "Price must be greater than 0!", 
                                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(adminMenuView, "Please enter a valid price!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Here you would typically call your service/DAO layer to update the item
        // For now, just show a success message
        JOptionPane.showMessageDialog(adminMenuView, 
            "Menu item updated successfully!\n" +
            "Name: " + name + "\n" +
            "Price: Rs. " + price + "\n" +
            "Category: " + category + "\n" +
            "Description: " + description,
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // TODO: Add actual database update logic here
        // menuService.updateMenuItem(name, price, category, description);
    }
    
    public void open(){
        this.adminMenuView.setVisible(true);
    }
    
    public void close(){
        this.adminMenuView.dispose();
    }
    
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
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            coffeeIcon.setForeground(Color.white);
            coffeeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            coffeeIcon.setForeground(Color.white);
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
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            drinksIcon.setForeground(Color.white);
            drinksIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            drinksIcon.setForeground(Color.white);
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
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            momoIcon.setForeground(Color.white);
            momoIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            momoIcon.setForeground(Color.white);
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
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            pizzaIcon.setForeground(Color.white);
            pizzaIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            pizzaIcon.setForeground(Color.white);
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
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            burgerIcon.setForeground(Color.white);
            burgerIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            burgerIcon.setForeground(Color.white);
            burgerIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    class spaghettiNav implements MouseListener{
        
        private JLabel chowmincon;
        private JTabbedPane menuTabbedPane;
        
        public spaghettiNav(JLabel label, JTabbedPane menuTabbedPane) {
            this.chowmincon = label;
            this.menuTabbedPane = menuTabbedPane;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            menuTabbedPane.setSelectedIndex(4);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            chowmincon.setForeground(Color.white);
            chowmincon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            chowmincon.setForeground(Color.white);
            chowmincon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private void loadRestaurants() {
        try {
            MenuDao menuDao = new MenuDao();
            allMenu = menuDao.getAllMenuWithImages();
            filteredMenu = new ArrayList<>(allMenu);
            displayAllMenu();
        } catch (Exception e) {
        }
    }
    
    private void displayAllMenu() {
        adminMenuView.displayMenu(allMenu);
    }
    
    private void displayFilteredMenu() {
        adminMenuView.displayMenu(filteredMenu);
    }
    
}
