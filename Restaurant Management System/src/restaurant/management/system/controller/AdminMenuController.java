/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import restaurant.management.system.UIElements.MenuCardPanel;
import restaurant.management.system.view.AdminMenuView;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.view.AdminHomeView;
import restaurant.management.system.view.AdminOrdersView;
import restaurant.management.system.view.AdminProfileView;
import restaurant.management.system.view.LoginView;
    

/**
 *
 * @author labish
 */
public class AdminMenuController {
    private AdminMenuView adminMenuView;
    private MenuDao menuDao;
    private int currentOwnerId;
    
    private List<MenuData> allMenu;
    private List<MenuData> filteredMenu;
    private MenuData currentMenuItem; // For tracking selected item during updates
    
    public AdminMenuController(AdminMenuView view, int ownerId){
        System.out.println("DEBUG: AdminMenuController created with ownerId = " + ownerId);
        this.adminMenuView = view;
        this.menuDao = new MenuDao();
        this.currentOwnerId = ownerId;
        this.allMenu = new ArrayList<>();
        this.filteredMenu = new ArrayList<>();
        this.currentOwnerId = ownerId;
        
        this.adminMenuView.homeNavigation(new HomeNav(adminMenuView.getHomelabel()));
        this.adminMenuView.profileNavigation(new ProfileNav(adminMenuView.getProfilelabel()));
        this.adminMenuView.orderNavigation(new OrderNav(adminMenuView.getOrderlabel()));
        this.adminMenuView.logoutNavigation(new LogoutNav(adminMenuView.getLogoutlabel()));
        
        setupButtonListeners();
        setupNavigationListeners();
        loadMenuItems();
    }
    private void setupButtonListeners() {
    // Connect update button to show menu management popup
    adminMenuView.getUpdateButton().addActionListener(e -> showMenuManagementPopup());
}
    

    
    private void setupNavigationListeners() {
    // Get the tabbed pane from the view
    JTabbedPane tabbedPane = adminMenuView.getMenuTabbedPane();
    
    // Setup listeners for each icon
    addNavigationListener(adminMenuView.getCoffeeIcon(), tabbedPane, 0); // Hot Beverage
    addNavigationListener(adminMenuView.getDrinksIcon(), tabbedPane, 1); // Cold Beverage
    addNavigationListener(adminMenuView.getMomoIcon(), tabbedPane, 2);   // MoMo
    addNavigationListener(adminMenuView.getPizzaIcon(), tabbedPane, 3);  // Pizza
    addNavigationListener(adminMenuView.getBurgerIcon(), tabbedPane, 4); // Burger
    addNavigationListener(adminMenuView.getRamenIcon(), tabbedPane, 5);  // Ramen
    addNavigationListener(adminMenuView.getSpaghettiIcon(), tabbedPane, 6); // Spaghetti
}

private void addNavigationListener(JLabel label, JTabbedPane tabbedPane, int tabIndex) {
    label.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            tabbedPane.setSelectedIndex(tabIndex);
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Optional: Add visual feedback on hover
            label.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            // Optional: Remove visual feedback
            label.setBorder(BorderFactory.createEmptyBorder());
        }
    });
}
    
    public void showMenuManagementPopup() { 
    JDialog popup = new JDialog(adminMenuView, "Menu Management", true);
    popup.setSize(600, 700);
    popup.setLocationRelativeTo(adminMenuView);
    popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
    // Main panel
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    mainPanel.setBackground(new Color(241, 237, 238));
    
    // Title label
    JLabel titleLabel = new JLabel("Menu Item Management", JLabel.CENTER);
    titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    titleLabel.setForeground(new Color(227, 143, 11));
    
    // Form panel
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(241, 237, 238));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST;
    
    // Form fields
    JTextField nameField = new JTextField(20);
    JTextField priceField = new JTextField(20);
    String[] categories = MenuData.CATEGORIES;
    JComboBox<String> categoryCombo = new JComboBox<>(categories);
    JTextArea descArea = new JTextArea(3, 20);
    descArea.setLineWrap(true);
    descArea.setWrapStyleWord(true);
    JScrollPane descScroll = new JScrollPane(descArea);
    
    // Image components
    JLabel imagePreview = new JLabel("No image selected");
    imagePreview.setPreferredSize(new Dimension(120, 120));
    imagePreview.setBorder(BorderFactory.createLineBorder(new Color(227, 143, 11), 2));
    imagePreview.setHorizontalAlignment(JLabel.CENTER);
    
    JButton selectImageBtn = new JButton("Select Image");
    final String[] selectedImagePath = {null};
    
    // Add form components
    addFormField(formPanel, gbc, "Item Name:", nameField, 0);
    addFormField(formPanel, gbc, "Price (Rs.):", priceField, 1);
    addFormField(formPanel, gbc, "Category:", categoryCombo, 2);
    
    // Description
    gbc.gridx = 0; gbc.gridy = 3;
    formPanel.add(createStyledLabel("Description:"), gbc);
    gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
    styleTextArea(descArea);
    formPanel.add(descScroll, gbc);
    
    // Image selection
    gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
    formPanel.add(createStyledLabel("Image:"), gbc);
    
    gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    imagePanel.setBackground(new Color(241, 237, 238));
    
    // Style the select image button
    styleButton(selectImageBtn, new Color(227, 143, 11));
    selectImageBtn.setPreferredSize(new Dimension(100, 30));
    
    selectImageBtn.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(popup);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath[0] = selectedFile.getAbsolutePath();
            
            // Display image preview
            ImageIcon icon = new ImageIcon(selectedImagePath[0]);
            ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(
                120, 120, java.awt.Image.SCALE_SMOOTH));
            imagePreview.setIcon(scaledIcon);
            imagePreview.setText("");
        }
    });
    
    imagePanel.add(imagePreview);
    imagePanel.add(Box.createHorizontalStrut(10));
    imagePanel.add(selectImageBtn);
    formPanel.add(imagePanel, gbc);
    
    // Main action buttons panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
    buttonPanel.setBackground(new Color(241, 237, 238));
    
    // Create the three main buttons
    JButton addButton = new JButton("Add Item");
    JButton editButton = new JButton("Edit Item");
    JButton deleteButton = new JButton("Delete Item");
    JButton cancelButton = new JButton("Cancel");
    
    // Style buttons with different colors
    styleButton(addButton, new Color(34, 139, 34));      // Green for Add
    styleButton(editButton, new Color(227, 143, 11));    // Orange for Edit
    styleButton(deleteButton, new Color(220, 20, 60));   // Red for Delete
    styleButton(cancelButton, new Color(128, 128, 128)); // Gray for Cancel
    
    // Button action listeners
    addButton.addActionListener(e -> {
        String name = nameField.getText().trim();
        String price = priceField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        String description = descArea.getText().trim();
        
        if (validateInput(name, price)) {
            handleAddItem(name, price, category, description, selectedImagePath[0]);
            clearForm(nameField, priceField, categoryCombo, descArea, imagePreview, selectedImagePath);
        }
    });
    
    editButton.addActionListener(e -> {
        if (currentMenuItem == null) {
            JOptionPane.showMessageDialog(popup, 
                "Please select an item to edit by double-clicking on it first!", 
                "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String name = nameField.getText().trim();
        String price = priceField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        String description = descArea.getText().trim();
        
        if (validateInput(name, price)) {
            handleUpdateItem(name, price, category, description, selectedImagePath[0]);
            clearForm(nameField, priceField, categoryCombo, descArea, imagePreview, selectedImagePath);
            currentMenuItem = null; // Clear selection after edit
        }
    });
    
    deleteButton.addActionListener(e -> {
        if (currentMenuItem == null) {
            JOptionPane.showMessageDialog(popup, 
                "Please select an item to delete by double-clicking on it first!", 
                "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(popup,
            "Are you sure you want to delete '" + currentMenuItem.getItemName() + "'?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            handleDeleteItem();
            clearForm(nameField, priceField, categoryCombo, descArea, imagePreview, selectedImagePath);
            currentMenuItem = null; // Clear selection after delete
        }
    });
    
    cancelButton.addActionListener(e -> {
        currentMenuItem = null; // Clear any selection
        popup.dispose();
    });
    
    // Add buttons to panel
    buttonPanel.add(addButton);
    buttonPanel.add(editButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(cancelButton);
    
    // Information panel
    JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    infoPanel.setBackground(new Color(241, 237, 238));
    JLabel infoLabel = new JLabel("<html><center>Double-click on any menu item to load it for editing/deletion</center></html>");
    infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
    infoLabel.setForeground(new Color(100, 100, 100));
    infoPanel.add(infoLabel);
    
    // Add components to main panel
    mainPanel.add(titleLabel, BorderLayout.NORTH);
    
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.setBackground(new Color(241, 237, 238));
    centerPanel.add(formPanel, BorderLayout.CENTER);
    centerPanel.add(infoPanel, BorderLayout.SOUTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);
    
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    popup.add(mainPanel);
    
    // When an item is selected for editing, populate the form
    if (currentMenuItem != null) {
        populateFormWithCurrentItem(nameField, priceField, categoryCombo, descArea, imagePreview);
    }
    
    popup.setVisible(true);
}

// Helper method to create styled labels
private JLabel createStyledLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
    return label;
}

// Helper method to style text area
private void styleTextArea(JTextArea textArea) {
    textArea.setFont(new Font("Arial", Font.PLAIN, 14));
    textArea.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)
    ));
}

// Helper method to populate form when editing
private void populateFormWithCurrentItem(JTextField nameField, JTextField priceField, 
                                       JComboBox<String> categoryCombo, JTextArea descArea, 
                                       JLabel imagePreview) {
    if (currentMenuItem != null) {
        nameField.setText(currentMenuItem.getItemName());
        priceField.setText(String.valueOf(currentMenuItem.getItemPrice()));
        categoryCombo.setSelectedItem(currentMenuItem.getItemCategory());
        descArea.setText(currentMenuItem.getItemDescription());
        
        if (currentMenuItem.getItemImage() != null && currentMenuItem.getItemImage().length > 0) {
            try {
                ImageIcon icon = new ImageIcon(currentMenuItem.getItemImage());
                ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(
                    120, 120, java.awt.Image.SCALE_SMOOTH));
                imagePreview.setIcon(scaledIcon);
                imagePreview.setText("");
            } catch (Exception e) {
                imagePreview.setText("Existing image");
            }
        }
    }
}

// Helper method to clear form
private void clearForm(JTextField nameField, JTextField priceField, JComboBox<String> categoryCombo, 
                      JTextArea descArea, JLabel imagePreview, String[] selectedImagePath) {
    nameField.setText("");
    priceField.setText("");
    categoryCombo.setSelectedIndex(0);
    descArea.setText("");
    imagePreview.setIcon(null);
    imagePreview.setText("No image selected");
    selectedImagePath[0] = null;
}

// Helper method to style buttons
private void styleButton(JButton button, Color bgColor) {
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(bgColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setPreferredSize(new Dimension(120, 35));
}

// Helper method to add form fields
private void addFormField(JPanel panel, GridBagConstraints gbc, String label, Component field, int row) {
    gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE;
    JLabel jLabel = new JLabel(label);
    jLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
    panel.add(jLabel, gbc);
    
    gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
    if (field instanceof JTextField) {
        ((JTextField)field).setFont(new Font("Arial", Font.PLAIN, 14));
        ((JTextField)field).setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(227, 143, 11), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    panel.add(field, gbc);
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
            
            MenuData newItem = new MenuData(
                currentOwnerId, // ownerId
                0,              // itemId (0 for new item)
                name,           // itemName
                category,       // itemCategory
                description,    // itemDescription
                Double.parseDouble(price), // itemPrice
                imageBytes,     // itemImage
                0.0,            // rating
                "0"             // reviews
            );
        
        boolean success = menuDao.addMenuItem(newItem);
        
        if (success) {
            // Refresh the relevant tab
            refreshCategoryTab(category);
            JOptionPane.showMessageDialog(adminMenuView, 
                "Menu item added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(adminMenuView, 
                "Failed to add menu item.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(adminMenuView, 
            "Error adding menu item: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

private void refreshCategoryTab(String category) {
    JTabbedPane tabbedPane = adminMenuView.getMenuTabbedPane();
    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
        if (tabbedPane.getTitleAt(i).equalsIgnoreCase(category)) {
            refreshTab(i);
            break;
        }
    }
}

private void refreshTab(int tabIndex) {
    JTabbedPane tabbedPane = adminMenuView.getMenuTabbedPane();
    JPanel tabPanel = (JPanel) tabbedPane.getComponentAt(tabIndex);
    String category = tabbedPane.getTitleAt(tabIndex);
    
    // Clear existing components
    if (tabPanel.getComponentCount() > 0 && tabPanel.getComponent(0) instanceof JScrollPane) {
        JScrollPane scrollPane = (JScrollPane) tabPanel.getComponent(0);
        JPanel contentPanel = (JPanel) scrollPane.getViewport().getView();
        contentPanel.removeAll();

        // Load items for this category
        List<MenuData> items = menuDao.getMenuByCategory(category);
        for (MenuData item : items) {
            MenuCardPanel card = new MenuCardPanel(item);
            card.addMouseListener(createCardClickListener(item));
            contentPanel.add(card);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    tabPanel.revalidate();
    tabPanel.repaint();
}
    
    private void handleUpdateItem(String name, String price, String category, String description, String imagePath) {
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
            // Refresh the specific category tab instead of all
            refreshCategoryTab(currentMenuItem.getItemCategory());
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
        String category = currentMenuItem.getItemCategory(); // Store category before deletion
         boolean success = menuDao.deleteMenuItem(currentMenuItem.getItemId(), currentOwnerId);
        
        if (success) {
            JOptionPane.showMessageDialog(adminMenuView, 
                "Menu item deleted successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            currentMenuItem = null; // Clear selection
            refreshCategoryTab(category); // Refresh the specific category tab
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
    
    
    private void loadMenuItems() {
        try {
            // Load all menu items for this owner
            allMenu = menuDao.getMenuByOwner(currentOwnerId);
            filteredMenu = new ArrayList<>(allMenu);
            
            // Use the view's displayMenu method to show the items
            adminMenuView.displayMenu(allMenu);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(adminMenuView, 
                "Error loading menu: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private MouseListener createCardClickListener(MenuData item) {
    return new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { // Double click to edit
                currentMenuItem = item;
                showMenuManagementPopup(); // Updated method name
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            ((JComponent)e.getSource()).setBorder(
                BorderFactory.createLineBorder(Color.ORANGE, 2));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            ((JComponent)e.getSource()).setBorder(
                BorderFactory.createEmptyBorder());
        }
    };
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
    
    class HomeNav implements MouseListener{
        
        private JLabel homelabel;
        
        public HomeNav(JLabel label) {
            this.homelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminHomeView adminHomeView = new AdminHomeView();
            AdminHomeController adminHomeController= new AdminHomeController(adminHomeView, currentOwnerId);
            adminHomeController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            homelabel.setForeground(Color.white);
            homelabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            homelabel.setForeground(Color.black);
            homelabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    class ProfileNav implements MouseListener{
        
        private JLabel profilelabel;
        
        public ProfileNav(JLabel label) {
            this.profilelabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminProfileView adminProfileView = new AdminProfileView();
            AdminProfileController adminProfileController= new AdminProfileController(adminProfileView, currentOwnerId);
            adminProfileController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            profilelabel.setForeground(Color.white);
            profilelabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            profilelabel.setForeground(Color.black);
            profilelabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    class OrderNav implements MouseListener{
        
        private JLabel orderlabel;
        
        public OrderNav(JLabel label) {
            this.orderlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            AdminOrdersView adminOrdersView = new AdminOrdersView();
            AdminOrdersController adminOrdersController= new AdminOrdersController(adminOrdersView, currentOwnerId);
            adminOrdersController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            orderlabel.setForeground(Color.white);
            orderlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            orderlabel.setForeground(Color.black);
            orderlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    class LogoutNav implements MouseListener{
        
        private JLabel logoutlabel;
        
        public LogoutNav(JLabel label) {
            this.logoutlabel = label;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to logout?", "Logout Confirmation",
                JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
            JFrame adminMenuView = (JFrame) SwingUtilities.getWindowAncestor(logoutlabel);
            adminMenuView.dispose();

            LoginView loginView = new LoginView();
            LoginController loginController= new LoginController(loginView);
            loginController.open();
            close();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            logoutlabel.setForeground(Color.WHITE);
            logoutlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            logoutlabel.setForeground(Color.BLACK);
            logoutlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
