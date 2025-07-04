/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.UIElements;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import restaurant.management.system.dao.CustomerDao;
import restaurant.management.system.dao.MenuDao;
import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.dao.OwnerDao;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.MenuData;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.model.OwnerData;

/**
 *
 * @author acer
 */
public class CustomerOrderPanel extends PanelRound {
    
    private OrderData order;
    private JFrame parentFrame;
    private OrderDao orderDao;
    private MenuDao menuDao;
    private boolean isHovered = false;
    private String userName;
    
    // Colors matching your design
    private final Color DEFAULT_COLOR = new Color(239,204,150); // Light orange/beige from prototype
    private final Color HOVER_COLOR = new Color(255,153,0); // Slightly darker on hover
    private final Color TEXT_COLOR = Color.BLACK;
    
    public CustomerOrderPanel(OrderData order, JFrame parentFrame) {
        this.order = order;
        this.parentFrame = parentFrame;
        this.orderDao = new OrderDao();
        this.menuDao = new MenuDao();

        this.userName = getUsername();
        
        initializePanel();
        setupComponents();
        setupEventHandlers();
    }
    
    private String getUsername() {
        try {
            CustomerDao customerDao = new CustomerDao();
            
             CustomerData customer = customerDao.getCustomerById(order.getCustomerId());
             if (customer != null) {
                 return customer.getUsername();
             }
            
        } catch (Exception e) {
            System.err.println("Error fetching customer name: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Default fallback
        return "Customer";
    }
    
    public OrderData getOrder() {
        return this.order;
    }
    
    private void initializePanel() {
        setBackground(DEFAULT_COLOR);
        setRoundBottonLeft(25);
        setRoundBottonRight(25);
        setRoundTopLeft(25);
        setRoundTopRight(25);
        setLayout(null);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        setPreferredSize(new java.awt.Dimension(1100, 70));
        setMaximumSize(new java.awt.Dimension(1100, 70));
        setMinimumSize(new java.awt.Dimension(1100, 70));
        setSize(new java.awt.Dimension(1100, 70));
    }
    
    private void setupComponents() {
        removeAll();
        setLayout(new java.awt.BorderLayout());
        setBackground(new Color(239, 204, 150));
        setPreferredSize(new java.awt.Dimension(600, 75));
        setMaximumSize(new java.awt.Dimension(600, 75));
        setMinimumSize(new java.awt.Dimension(600, 75));
        setRoundTopLeft(20);
        setRoundTopRight(20);
        setRoundBottonLeft(20);
        setRoundBottonRight(20);

        // Avatar/Icon (left) - use GridBagLayout for vertical centering
        JLabel avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new java.awt.Dimension(50, 50));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setVerticalAlignment(JLabel.CENTER);
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/customer.png"));
        java.awt.Image img = icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        avatarLabel.setIcon(new javax.swing.ImageIcon(img));
        
        JPanel avatarPanel = new JPanel(new java.awt.GridBagLayout());
        avatarPanel.setOpaque(false);
        avatarPanel.setPreferredSize(new java.awt.Dimension(80, 75));
        avatarPanel.add(avatarLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new javax.swing.BoxLayout(infoPanel, javax.swing.BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(javax.swing.Box.createVerticalGlue());

        JLabel usernameLabel = new JLabel(userName);
        usernameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        usernameLabel.setForeground(new Color(80, 50, 30));
        infoPanel.add(usernameLabel);

        infoPanel.add(javax.swing.Box.createVerticalStrut(5));

        String infoText = "Table No: " + order.getTableNumber() + ", " + order.getOrderTime() + ", " + order.getOrderDate() + ", Status: " + (order.getOrderStatus() != null ? order.getOrderStatus() : "PENDING");
        JLabel infoLabel = new JLabel(infoText);
        infoLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        infoLabel.setForeground(new Color(100, 70, 50));
        infoPanel.add(infoLabel);

        infoPanel.add(javax.swing.Box.createVerticalGlue());

        add(avatarPanel, java.awt.BorderLayout.WEST);
        add(infoPanel, java.awt.BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showOrderDetailsDialog();
            }
            // No hover color change!
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    private void showOrderDetailsDialog() {
        // Check if this panel is being used in CustomerBillView
        boolean isInBillView = parentFrame.getClass().getSimpleName().equals("CustomerBillView");
        
        // Check if order can be edited based on status
        String orderStatus = order.getOrderStatus() != null ? order.getOrderStatus() : "PENDING";
        boolean canEdit = "PENDING".equalsIgnoreCase(orderStatus) || "CANCELLED".equalsIgnoreCase(orderStatus);
        
        JDialog detailDialog = new JDialog(parentFrame, "Order Details", true);
        detailDialog.setUndecorated(true);
        detailDialog.setSize(420, 320);
        detailDialog.setLocationRelativeTo(parentFrame);
        detailDialog.setLayout(null);
        detailDialog.getContentPane().setBackground(new Color(239,204,150));

        // Main content panel with rounded corners
        PanelRound contentPanel = new PanelRound();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setRoundBottonLeft(25);
        contentPanel.setRoundBottonRight(25);
        contentPanel.setRoundTopLeft(25);
        contentPanel.setRoundTopRight(25);
        contentPanel.setBounds(10, 10, 400, 300);
        contentPanel.setLayout(null);

        // Close (X) button
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setBounds(370, 10, 25, 25);
        closeButton.setBackground(new Color(227, 143, 11));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBorder(null);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> detailDialog.dispose());
        contentPanel.add(closeButton);

        // Header: Restaurant name, Table no., Time (centered)
        JLabel headerLabel = new JLabel("<html><center>" + userName + "<br>Table no. " + order.getTableNumber() + ".<br>" + order.getOrderTime() + "</center></html>");
        headerLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBounds(0, 20, 400, 60);
        contentPanel.add(headerLabel);

        // Separator line
        JSeparator sep = new JSeparator();
        sep.setBounds(20, 80, 360, 2);
        contentPanel.add(sep);

        // Food/Amount table-like section
        JLabel foodHeader = new JLabel("Food");
        foodHeader.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
        foodHeader.setBounds(40, 90, 200, 20);
        contentPanel.add(foodHeader);
        JLabel amountHeader = new JLabel("Amount");
        amountHeader.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
        amountHeader.setBounds(270, 90, 80, 20);
        amountHeader.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(amountHeader);

        int y = 115;
        // Check if orderItems is not null before iterating
        if (order.getOrderItems() != null) {
            for (OrderData.OrderItem item : order.getOrderItems()) {
                JLabel foodLabel = new JLabel("<html><i>" + item.getItemName() + "</i></html>");
                foodLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 14));
                foodLabel.setBounds(40, y, 200, 20);
                contentPanel.add(foodLabel);
                JLabel amountLabel = new JLabel(String.valueOf(item.getQuantity()));
                amountLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 14));
                amountLabel.setBounds(270, y, 80, 20);
                amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(amountLabel);
                y += 22;
            }
        }

        // Add total amount label
        JLabel totalLabel = new JLabel("Total: Rs. " + String.format("%.2f", order.getTotalAmount()));
        totalLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        totalLabel.setBounds(40, y + 10, 200, 20);
        contentPanel.add(totalLabel);

        // Only show edit button if not in bill view AND order can be edited
        if (!isInBillView && canEdit) {
            // Edit button (centered at bottom)
            JButton editButton = new JButton("Edit");
            editButton.setBounds(150, 240, 100, 32);
            editButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 15));
            editButton.setBackground(new Color(227, 143, 11));
            editButton.setForeground(Color.BLACK);
            editButton.setBorder(null);
            editButton.setFocusPainted(false);
            editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            editButton.addActionListener(e -> {
                detailDialog.dispose();
                showEditDialog();
            });
            contentPanel.add(editButton);
        }

        // Show Approve/Reject buttons if opened from AdminHomeView or StaffHomeView
        if (parentFrame instanceof restaurant.management.system.view.AdminHomeView ||
            parentFrame instanceof restaurant.management.system.view.StaffHomeView) {
            JButton approveButton = new JButton("Approve");
            approveButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
            approveButton.setBackground(new Color(227, 143, 11));
            approveButton.setForeground(Color.BLACK);
            approveButton.setFocusPainted(false);
            approveButton.setBounds(60, 260, 120, 35);
            approveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            approveButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
            approveButton.addActionListener(e -> {
                boolean updated = orderDao.updateOrderStatus(order.getOrderId(), "Pending");
                if (updated) {
                    order.setOrderStatus("Pending");
                    
                    // Refresh all views that might be affected
                    refreshAllViews();
                    
                    JOptionPane.showMessageDialog(contentPanel, "Order approved and moved to Pending!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    detailDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(contentPanel, "Failed to approve order.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            contentPanel.add(approveButton);

            JButton rejectButton = new JButton("Reject");
            rejectButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
            rejectButton.setBackground(new Color(227, 143, 11));
            rejectButton.setForeground(Color.BLACK);
            rejectButton.setFocusPainted(false);
            rejectButton.setBounds(220, 260, 120, 35);
            rejectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            rejectButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
            rejectButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(contentPanel, "Are you sure you want to reject and delete this order?", "Confirm Reject", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean deleted = orderDao.deleteOrder(order.getOrderId());
                    if (deleted) {
                        // Refresh all views that might be affected
                        refreshAllViews();
                        
                        JOptionPane.showMessageDialog(contentPanel, "Order rejected and deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        detailDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(contentPanel, "Failed to delete order.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            contentPanel.add(rejectButton);
        }

        detailDialog.add(contentPanel);
        detailDialog.setVisible(true);
    }
    
    private void showEditDialog() {
        JDialog editDialog = new JDialog(parentFrame, "Edit Order", true);
        editDialog.setSize(700, 600);
        editDialog.setLocationRelativeTo(parentFrame);
        editDialog.setLayout(null);
        editDialog.getContentPane().setBackground(new Color(239,204,150));
        
        // Create main content panel
        PanelRound contentPanel = new PanelRound();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setRoundBottonLeft(20);
        contentPanel.setRoundBottonRight(20);
        contentPanel.setRoundTopLeft(20);
        contentPanel.setRoundTopRight(20);
        contentPanel.setBounds(20, 10, 640, 540);
        contentPanel.setLayout(null);
        
        // Title
        JLabel titleLabel = new JLabel("Edit Order #" + order.getOrderId());
        titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
        titleLabel.setBounds(20, 20, 300, 30);
        contentPanel.add(titleLabel);
        
        // Order info
        JLabel infoLabel = new JLabel("Table " + order.getTableNumber() + " - " + 
                                    order.getOrderDate() + " " + order.getOrderTime());
        infoLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        infoLabel.setBounds(20, 55, 400, 20);
        contentPanel.add(infoLabel);
        
        // Create table for order items
        String[] columnNames = {"Item Name", "Quantity", "Price", "Subtotal"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Only quantity column is editable
            }
        };
        
        // Add order items to table - check for null
        if (order.getOrderItems() != null) {
            for (OrderData.OrderItem item : order.getOrderItems()) {
                Object[] row = {
                    item.getItemName(),
                    item.getQuantity(),
                    String.format("%.2f", item.getPrice()),
                    String.format("%.2f", item.getSubtotal())
                };
                tableModel.addRow(row);
            }
        }
        
        JTable itemsTable = new JTable(tableModel);
        itemsTable.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        itemsTable.getTableHeader().setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
        itemsTable.setRowHeight(25);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Total label reference for updates
        JLabel totalLabel = new JLabel("Total: Rs. " + String.format("%.2f", order.getTotalAmount()));
        
        // Add table model listener to update subtotals when quantity changes
        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 1) { // Quantity column changed
                int row = e.getFirstRow();
                int newQuantity = (Integer) tableModel.getValueAt(row, 1);
                if (newQuantity <= 0) {
                    // Remove row if quantity is 0 or negative
                    tableModel.removeRow(row);
                } else {
                    double price = Double.parseDouble(tableModel.getValueAt(row, 2).toString());
                    double newSubtotal = newQuantity * price;
                    tableModel.setValueAt(String.format("%.2f", newSubtotal), row, 3);
                }
                // Update total
                updateTotalLabel(tableModel, totalLabel);
            }
        });
        
        JScrollPane tableScrollPane = new JScrollPane(itemsTable);
        tableScrollPane.setBounds(20, 90, 580, 300);
        contentPanel.add(tableScrollPane);
        
        // Menu selection dropdown
        JComboBox<MenuData> menuComboBox = new JComboBox<>();
        try {
            List<MenuData> menuItems = menuDao.getAllMenuWithImages();
            for (MenuData menuItem : menuItems) {
                menuComboBox.addItem(menuItem);
            }
        } catch (Exception e) {
            System.err.println("Error loading menu items: " + e.getMessage());
            e.printStackTrace();
        }
        menuComboBox.setBounds(20, 400, 200, 30);
        contentPanel.add(menuComboBox);

        // Quantity spinner
        JLabel qtyLabel = new JLabel("Qty:");
        qtyLabel.setBounds(240, 375, 40, 20);
        contentPanel.add(qtyLabel);

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantitySpinner.setBounds(240, 400, 60, 30);
        contentPanel.add(quantitySpinner);

        // Add item button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.setBounds(320, 400, 100, 30);
        addItemButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
        addItemButton.setBackground(new Color(40, 167, 69));
        addItemButton.setForeground(Color.WHITE);
        addItemButton.setBorder(null);
        addItemButton.setFocusPainted(false);
        addItemButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addItemButton.addActionListener(e -> {
            MenuData selectedItem = (MenuData) menuComboBox.getSelectedItem();
            int quantity = (Integer) quantitySpinner.getValue();
            
            if (selectedItem != null) {
                // Check if item already exists in table
                boolean itemExists = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(selectedItem.getItemName())) {
                        // Update existing item quantity
                        int currentQty = (Integer) tableModel.getValueAt(i, 1);
                        int newQty = currentQty + quantity;
                        tableModel.setValueAt(newQty, i, 1);
                        double price = Double.parseDouble(tableModel.getValueAt(i, 2).toString());
                        tableModel.setValueAt(String.format("%.2f", newQty * price), i, 3);
                        itemExists = true;
                        break;
                    }
                }
                
                if (!itemExists) {
                    // Add new item to table
                    Object[] row = {
                        selectedItem.getItemName(),
                        quantity,
                        String.format("%.2f", selectedItem.getItemPrice()),
                        String.format("%.2f", selectedItem.getItemPrice() * quantity)
                    };
                    tableModel.addRow(row);
                }
                
                // Reset spinner
                quantitySpinner.setValue(1);
                updateTotalLabel(tableModel, totalLabel);
            }
        });
        contentPanel.add(addItemButton);

        // Remove item button
        JButton removeItemButton = new JButton("Remove Selected");
        removeItemButton.setBounds(440, 400, 130, 30);
        removeItemButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
        removeItemButton.setBackground(new Color(220, 53, 69));
        removeItemButton.setForeground(Color.WHITE);
        removeItemButton.setBorder(null);
        removeItemButton.setFocusPainted(false);
        removeItemButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeItemButton.addActionListener(e -> {
            int selectedRow = itemsTable.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeRow(selectedRow);
                updateTotalLabel(tableModel, totalLabel);
            } else {
                JOptionPane.showMessageDialog(editDialog, "Please select an item to remove.", 
                                            "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        contentPanel.add(removeItemButton);

        // Total label
        totalLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        totalLabel.setBounds(450, 450, 200, 25);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(totalLabel);

        // Action buttons
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(150, 490, 120, 35);
        saveButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(null);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(editDialog, 
                        "Cannot save order with no items. Please add items or cancel the order.", 
                        "No Items", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Save the changes
                if (saveOrderChanges(tableModel)) {
                    editDialog.dispose();
                    
                    // Refresh all views that might be affected
                    refreshAllViews();
                    
                    // Show success message
                    JOptionPane.showMessageDialog(parentFrame, 
                        "Order updated successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(editDialog,
                        "Failed to save changes. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPanel.add(saveButton);

        JButton cancelOrderButton = new JButton("Cancel Order");
        cancelOrderButton.setBounds(290, 490, 120, 35);
        cancelOrderButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        cancelOrderButton.setBackground(new Color(220, 53, 69));
        cancelOrderButton.setForeground(Color.WHITE);
        cancelOrderButton.setBorder(null);
        cancelOrderButton.setFocusPainted(false);
        cancelOrderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelOrderButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(editDialog,
                "Are you sure you want to cancel this order?\nThis action cannot be undone.",
                "Cancel Order",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (result == JOptionPane.YES_OPTION) {
                if (orderDao.updateOrderStatus(order.getOrderId(), "CANCELLED")) {
                    order.setOrderStatus("CANCELLED");
                    editDialog.dispose();
                    
                    // Refresh all views that might be affected
                    refreshAllViews();
                    
                    JOptionPane.showMessageDialog(parentFrame,
                        "Order has been cancelled successfully!",
                        "Order Cancelled",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(editDialog,
                        "Failed to cancel order. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPanel.add(cancelOrderButton);

        JButton closeEditButton = new JButton("Close");
        closeEditButton.setBounds(430, 490, 100, 35);
        closeEditButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        closeEditButton.setBackground(new Color(108, 117, 125));
        closeEditButton.setForeground(Color.WHITE);
        closeEditButton.setBorder(null);
        closeEditButton.setFocusPainted(false);
        closeEditButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeEditButton.addActionListener(e -> editDialog.dispose());
        contentPanel.add(closeEditButton);

        editDialog.add(contentPanel);
        editDialog.setVisible(true);
    }

    // Helper method to update total when quantities change
    private void updateTotalLabel(DefaultTableModel model, JLabel totalLabel) {
        double total = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String subtotalStr = model.getValueAt(i, 3).toString();
            total += Double.parseDouble(subtotalStr);
        }
        totalLabel.setText("Total: Rs. " + String.format("%.2f", total));
    }

    // Helper method to save order changes
    private boolean saveOrderChanges(DefaultTableModel model) {
        try {
            // First, delete existing order items
            if (!orderDao.deleteOrderItems(order.getOrderId())) {
                System.err.println("Failed to delete existing order items");
                return false;
            }
            
            // Initialize order items list if null
            if (order.getOrderItems() == null) {
                order.setOrderItems(new java.util.ArrayList<>());
            }
            
            // Clear existing order items from the order object
            order.getOrderItems().clear();
            
            // Add updated items from table
            for (int i = 0; i < model.getRowCount(); i++) {
                String itemName = model.getValueAt(i, 0).toString();
                int quantity = (Integer) model.getValueAt(i, 1);
                double price = Double.parseDouble(model.getValueAt(i, 2).toString());
                double subtotal = Double.parseDouble(model.getValueAt(i, 3).toString());
                
                // Find menu item ID by name
                int menuId = getMenuIdByName(itemName);
                
                OrderData.OrderItem item = new OrderData.OrderItem();
                item.setMenuId(menuId);
                item.setItemName(itemName);
                item.setQuantity(quantity);
                item.setPrice(price);
                item.setSubtotal(subtotal);
                item.setOrderId(order.getOrderId());
                
                order.getOrderItems().add(item);
            }

            // Recalculate total amount
            double newTotal = order.getOrderItems().stream()
                    .mapToDouble(OrderData.OrderItem::getSubtotal)
                    .sum();
            order.setTotalAmount(newTotal);
            
            // Set status to "Modified"
            order.setOrderStatus("Modified");

            // Update the order in the database
            boolean result = orderDao.updateOrderWithItems(order);
            
            if (result) {
                System.out.println("Order updated successfully with Modified status");
            } else {
                System.err.println("Failed to update order in database");
            }
            
            return result;
            
        } catch (Exception e) {
            System.err.println("Error saving order changes: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper method to get menu ID by name
    private int getMenuIdByName(String itemName) {
        try {
            List<MenuData> menuItems = menuDao.getAllMenuWithImages();
            for (MenuData menuItem : menuItems) {
                if (menuItem.getItemName().equals(itemName)) {
                    return menuItem.getItemId();
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting menu ID by name: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // Default if not found
    }
    
    // Helper method to refresh order data from database
    private void refreshOrderData() {
        try {
            OrderData updatedOrder = orderDao.getOrderById(order.getOrderId());
            if (updatedOrder != null) {
                this.order = updatedOrder;
                // Remove all components and re-setup
                removeAll();
                setupComponents();
                revalidate();
                repaint();
            }
        } catch (Exception e) {
            System.err.println("Error refreshing order data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper method to notify about cancelled order
    private void notifyCancelledOrder(OrderData cancelledOrder) {
        try {
            // Check if parent frame is CustomerOrderView
            if (parentFrame instanceof restaurant.management.system.view.CustomerOrderView) {
                restaurant.management.system.view.CustomerOrderView customerView = 
                    (restaurant.management.system.view.CustomerOrderView) parentFrame;
                customerView.onOrderCancelled(cancelledOrder);
            }
        } catch (Exception e) {
            System.err.println("Error notifying cancelled order: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper method to notify about modified order
    private void notifyModifiedOrder(OrderData modifiedOrder) {
        try {
            // Check if parent frame is AdminHomeView
            if (parentFrame instanceof restaurant.management.system.view.AdminHomeView) {
                restaurant.management.system.view.AdminHomeView adminView = 
                    (restaurant.management.system.view.AdminHomeView) parentFrame;
                adminView.onOrderModified(modifiedOrder);
            }
            
            // Check if parent frame is StaffHomeView
            if (parentFrame instanceof restaurant.management.system.view.StaffHomeView) {
                restaurant.management.system.view.StaffHomeView staffView = 
                    (restaurant.management.system.view.StaffHomeView) parentFrame;
                staffView.onOrderModified(modifiedOrder);
            }
        } catch (Exception e) {
            System.err.println("Error notifying modified order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to refresh all views
    private void refreshAllViews() {
        try {
            // Refresh the current order panel
            refreshOrderData();
            
            // Refresh AdminHomeView if it's the parent frame
            if (parentFrame instanceof restaurant.management.system.view.AdminHomeView) {
                restaurant.management.system.view.AdminHomeView adminView = 
                    (restaurant.management.system.view.AdminHomeView) parentFrame;
                adminView.onOrderModified(order);
            }
            
            // Refresh StaffHomeView if it's the parent frame
            if (parentFrame instanceof restaurant.management.system.view.StaffHomeView) {
                restaurant.management.system.view.StaffHomeView staffView = 
                    (restaurant.management.system.view.StaffHomeView) parentFrame;
                staffView.onOrderModified(order);
            }
            
            // Refresh CustomerOrderView if it's the parent frame
            if (parentFrame instanceof restaurant.management.system.view.CustomerOrderView) {
                restaurant.management.system.view.CustomerOrderView customerView = 
                    (restaurant.management.system.view.CustomerOrderView) parentFrame;
                customerView.refreshOrder(order);
            }
            
            // Refresh all AdminOrdersView instances
            restaurant.management.system.controller.AdminOrdersController.refreshAllAdminOrdersViews();
            
            // Refresh all StaffOrdersView instances
            restaurant.management.system.controller.StaffOrdersController.refreshAllStaffOrdersViews();
            
        } catch (Exception e) {
            System.err.println("Error refreshing all views: " + e.getMessage());
            e.printStackTrace();
        }
    }
}