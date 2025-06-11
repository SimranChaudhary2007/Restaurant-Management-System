/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import restaurant.management.system.view.AdminMenuView;

/**
 *
 * @author pradeepta 3434
 */
public class AdminMenuController {
    private AdminMenuView adminMenuView;
    
    public AdminMenuController(AdminMenuView view){
        this.adminMenuView = view;
        initializeEventListeners();
    }
    
    private void initializeEventListeners() {
        adminMenuView.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdatePopup();
            }
        });
    }
    
    private void showUpdatePopup() {
        JDialog popup = new JDialog(adminMenuView, "Update Menu Item", true);
        popup.setSize(450, 400);
        popup.setLocationRelativeTo(adminMenuView);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(241, 237, 238));
        
        // Title label
        JLabel titleLabel = new JLabel("Update Menu Item", JLabel.CENTER);
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
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(241, 237, 238));
        
        // Update button
        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        updateBtn.setBackground(new Color(227, 143, 11));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setPreferredSize(new Dimension(100, 40));
        updateBtn.setFocusPainted(false);
        
        // Cancel button
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        cancelBtn.setBackground(new Color(169, 169, 169));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setPreferredSize(new Dimension(100, 40));
        cancelBtn.setFocusPainted(false);
        
        // Button actions
        updateBtn.addActionListener(e -> {
            handleUpdateItem(nameField.getText(), priceField.getText(), 
                           (String)categoryCombo.getSelectedItem(), descArea.getText());
            popup.dispose();
        });
        
        cancelBtn.addActionListener(e -> popup.dispose());
        
        buttonPanel.add(updateBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(cancelBtn);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        popup.add(mainPanel);
        popup.setVisible(true);
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
}
