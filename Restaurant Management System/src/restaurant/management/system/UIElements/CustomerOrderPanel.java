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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import restaurant.management.system.model.OrderData;

/**
 *
 * @author acer
 */
public class CustomerOrderPanel extends PanelRound {
    
    private OrderData order;
    private JFrame parentFrame;
    private boolean isHovered = false;
    
    // Colors matching your design
    private final Color DEFAULT_COLOR = new Color(227, 143, 11);
    private final Color HOVER_COLOR = new Color(207, 123, 0);
    private final Color TEXT_COLOR = Color.BLACK;
    
    public CustomerOrderPanel(OrderData order, JFrame parentFrame) {
        this.order = order;
        this.parentFrame = parentFrame;
        initializePanel();
        setupComponents();
        setupEventHandlers();
    }
    
    private void initializePanel() {
        setBackground(DEFAULT_COLOR);
        setRoundBottonLeft(15);
        setRoundBottonRight(15);
        setRoundTopLeft(15);
        setRoundTopRight(15);
        setLayout(null);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void setupComponents() {
        // Restaurant name, time, date label
        JLabel orderInfoLabel = new JLabel();
        orderInfoLabel.setText("Restaurant Name - " + order.getOrderDate() + " " + order.getOrderTime());
        orderInfoLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        orderInfoLabel.setForeground(TEXT_COLOR);
        orderInfoLabel.setBounds(20, 15, 800, 25);
        add(orderInfoLabel);
        
        // Table number and order ID
        JLabel tableLabel = new JLabel("Table " + order.getTableNumber() + " - Order #" + order.getOrderId());
        tableLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        tableLabel.setForeground(TEXT_COLOR);
        tableLabel.setBounds(20, 35, 300, 20);
        add(tableLabel);
        
        // Status
        JLabel statusLabel = new JLabel("Status: " + order.getOrderStatus());
        statusLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setBounds(350, 35, 200, 20);
        add(statusLabel);
        
        // Total amount
        JLabel totalLabel = new JLabel("Rs. " + String.format("%.2f", order.getTotalAmount()));
        totalLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        totalLabel.setForeground(TEXT_COLOR);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel.setBounds(950, 25, 180, 25);
        add(totalLabel);
    }
    
    private void setupEventHandlers() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showOrderDetailsDialog();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_COLOR);
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(DEFAULT_COLOR);
                isHovered = false;
                repaint();
            }
        });
    }
    
    private void showOrderDetailsDialog() {
        JDialog detailDialog = new JDialog(parentFrame, "Order Details", true);
        detailDialog.setSize(600, 500);
        detailDialog.setLocationRelativeTo(parentFrame);
        detailDialog.setLayout(null);
        detailDialog.getContentPane().setBackground(new Color(241, 237, 238));
        
        // Create main content panel
        PanelRound contentPanel = new PanelRound();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setRoundBottonLeft(20);
        contentPanel.setRoundBottonRight(20);
        contentPanel.setRoundTopLeft(20);
        contentPanel.setRoundTopRight(20);
        contentPanel.setBounds(20, 20, 540, 420);
        contentPanel.setLayout(null);
        
        // Close button (X)
        JButton closeButton = new JButton("×");
        closeButton.setBounds(500, 10, 30, 30);
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setBackground(new Color(227, 143, 11));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBorder(null);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> detailDialog.dispose());
        contentPanel.add(closeButton);
        
        // Restaurant name header
        JLabel restaurantLabel = new JLabel("Restaurant name");
        restaurantLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        restaurantLabel.setBounds(20, 20, 200, 25);
        contentPanel.add(restaurantLabel);
        
        // Table number
        JLabel tableNoLabel = new JLabel("Table no.");
        tableNoLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        tableNoLabel.setBounds(20, 50, 100, 20);
        contentPanel.add(tableNoLabel);
        
        JLabel tableValueLabel = new JLabel(String.valueOf(order.getTableNumber()));
        tableValueLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        tableValueLabel.setBounds(120, 50, 50, 20);
        contentPanel.add(tableValueLabel);
        
        // Time
        JLabel timeLabel = new JLabel("Time");
        timeLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        timeLabel.setBounds(200, 50, 50, 20);
        contentPanel.add(timeLabel);
        
        JLabel timeValueLabel = new JLabel(order.getOrderTime());
        timeValueLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
        timeValueLabel.setBounds(250, 50, 100, 20);
        contentPanel.add(timeValueLabel);
        
        // Food items header
        JLabel foodLabel = new JLabel("Food");
        foodLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        foodLabel.setBounds(20, 90, 100, 25);
        contentPanel.add(foodLabel);
        
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        amountLabel.setBounds(400, 90, 100, 25);
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(amountLabel);
        
        // Create items list
        int yPos = 120;
        for (OrderData.OrderItem item : order.getOrderItems()) {
            JLabel itemNameLabel = new JLabel(item.getItemName());
            itemNameLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
            itemNameLabel.setBounds(20, yPos, 250, 20);
            contentPanel.add(itemNameLabel);
            
            JLabel itemQuantityLabel = new JLabel("Qty: " + item.getQuantity());
            itemQuantityLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 12));
            itemQuantityLabel.setBounds(20, yPos + 18, 100, 15);
            contentPanel.add(itemQuantityLabel);
            
            JLabel itemAmountLabel = new JLabel(String.valueOf(item.getQuantity()));
            itemAmountLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
            itemAmountLabel.setBounds(450, yPos, 50, 20);
            itemAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(itemAmountLabel);
            
            yPos += 40;
        }
        
        // Total section
        JLabel totalLabel = new JLabel("Total: Rs. " + String.format("%.2f", order.getTotalAmount()));
        totalLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        totalLabel.setBounds(300, yPos + 20, 200, 25);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(totalLabel);
        
        // Edit button
        JButton editButton = new JButton("Edit");
        editButton.setBounds(220, yPos + 60, 100, 35);
        editButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        editButton.setBackground(new Color(227, 143, 11));
        editButton.setForeground(Color.BLACK);
        editButton.setBorder(null);
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detailDialog.dispose();
                showEditDialog();
            }
        });
        contentPanel.add(editButton);
        
        detailDialog.add(contentPanel);
        detailDialog.setVisible(true);
    }
    
    private void showEditDialog() {
        JDialog editDialog = new JDialog(parentFrame, "Edit Order", true);
        editDialog.setSize(700, 600);
        editDialog.setLocationRelativeTo(parentFrame);
        editDialog.setLayout(null);
        editDialog.getContentPane().setBackground(new Color(241, 237, 238));
        
        // Create main content panel
        PanelRound contentPanel = new PanelRound();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setRoundBottonLeft(20);
        contentPanel.setRoundBottonRight(20);
        contentPanel.setRoundTopLeft(20);
        contentPanel.setRoundTopRight(20);
        contentPanel.setBounds(20, 20, 640, 540);
        contentPanel.setLayout(null);
        
        // Close button (X)
        JButton closeButton = new JButton("×");
        closeButton.setBounds(600, 10, 30, 30);
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setBackground(new Color(227, 143, 11));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBorder(null);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> editDialog.dispose());
        contentPanel.add(closeButton);
        
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
        
        // Add order items to table
        for (OrderData.OrderItem item : order.getOrderItems()) {
            Object[] row = {
                item.getItemName(),
                item.getQuantity(),
                String.format("%.2f", item.getPrice()),
                String.format("%.2f", item.getSubtotal())
            };
            tableModel.addRow(row);
        }
        
        JTable itemsTable = new JTable(tableModel);
        itemsTable.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
        itemsTable.getTableHeader().setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
        itemsTable.setRowHeight(25);
        
        JScrollPane tableScrollPane = new JScrollPane(itemsTable);
        tableScrollPane.setBounds(20, 90, 580, 300);
        contentPanel.add(tableScrollPane);
        
        // Total label
        JLabel totalLabel = new JLabel("Total: Rs. " + String.format("%.2f", order.getTotalAmount()));
        totalLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        totalLabel.setBounds(450, 410, 150, 25);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(totalLabel);
        
        // Buttons
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(200, 460, 120, 35);
        saveButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(null);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save changes
                // Update order items with new quantities
                // Recalculate totals
                // Save to database
                editDialog.dispose();
            }
        });
        contentPanel.add(saveButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(340, 460, 100, 35);
        cancelButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(null);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> editDialog.dispose());
        contentPanel.add(cancelButton);
        
        editDialog.add(contentPanel);
        editDialog.setVisible(true);
    }
    
    // Getter for order (if needed)
    public OrderData getOrder() {
        return order;
    }
    
    // Method to update order data and refresh display
    public void updateOrder(OrderData newOrder) {
        this.order = newOrder;
        removeAll();
        setupComponents();
        revalidate();
        repaint();
    }
}