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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author acer
 */
public class AdminOrderPanel extends PanelRound {
    
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
    
    public AdminOrderPanel(OrderData order, JFrame parentFrame) {
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
                 return customer.getFullName();
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
    }
    
    private void setupComponents() {
        // Username, table number, time, date label (bold, centered vertically)
        JLabel orderInfoLabel = new JLabel(userName + ", Table No: "+ order.getTableNumber() + ", " + order.getOrderTime() + ", " + order.getOrderDate());
        orderInfoLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
        orderInfoLabel.setForeground(TEXT_COLOR);
        orderInfoLabel.setBounds(30, 18, 800, 30);
        add(orderInfoLabel);
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
        if ("BILLED".equals(order.getOrderStatus())) {
            showBillDetailsDialog();
            return;
        }
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

        // Header: Username, Table no., Time (centered)
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
                y += 25;
            }
        }

        // Total amount
        JLabel totalLabel = new JLabel("Total: $" + String.format("%.2f", order.getTotalAmount()));
        totalLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        totalLabel.setBounds(40, y + 10, 200, 20);
        contentPanel.add(totalLabel);

        // Status update section
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        statusLabel.setBounds(40, y + 40, 80, 20);
        contentPanel.add(statusLabel);

        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"PENDING", "RECEIVED"});
        statusCombo.setSelectedItem(order.getOrderStatus());
        statusCombo.setBounds(120, y + 40, 120, 25);
        contentPanel.add(statusCombo);

        // Update button
        JButton updateButton = new JButton("Update Status");
        updateButton.setBackground(new Color(227, 143, 11));
        updateButton.setForeground(Color.BLACK);
        updateButton.setBorder(null);
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateButton.setBounds(250, y + 40, 120, 25);
        contentPanel.add(updateButton);

        // Check if opened from Received tab (tab index 1)
        final boolean isReceivedTab;
        if (parentFrame instanceof restaurant.management.system.view.AdminOrdersView) {
            int selectedTab = ((restaurant.management.system.view.AdminOrdersView) parentFrame).getJTabbedPane().getSelectedIndex();
            isReceivedTab = (selectedTab == 1);
        } else if (parentFrame instanceof restaurant.management.system.view.StaffOrdersView) {
            int selectedTab = ((restaurant.management.system.view.StaffOrdersView) parentFrame).getJTabbedPane().getSelectedIndex();
            isReceivedTab = (selectedTab == 1);
        } else {
            isReceivedTab = false;
        }
        if (isReceivedTab) {
            statusLabel.setVisible(false);
            statusCombo.setVisible(false);
            updateButton.setText("Confirm Order");
        }

        // Action for update/confirm button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isReceivedTab) {
                    // Update order status to BILLED
                    if (orderDao.updateOrderStatus(order.getOrderId(), "BILLED")) {
                        order.setOrderStatus("BILLED");
                        JOptionPane.showMessageDialog(detailDialog, "Order confirmed and moved to Bills!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Refresh received and bill tabs for both AdminOrdersView and StaffOrdersView
                        refreshAllOrderViews();
                        detailDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(detailDialog, "Failed to confirm order!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String newStatus = (String) statusCombo.getSelectedItem();
                    if (orderDao.updateOrderStatus(order.getOrderId(), newStatus)) {
                        order.setOrderStatus(newStatus);
                        JOptionPane.showMessageDialog(detailDialog, "Order status updated successfully! Please refresh the view to see changes.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Refresh all order views for simultaneous updates
                        refreshAllOrderViews();
                        detailDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(detailDialog, "Failed to update order status!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        detailDialog.add(contentPanel);
        detailDialog.setVisible(true);
    }

    private void showBillDetailsDialog() {
        JDialog billDialog = new JDialog(parentFrame, "Bill Details", true);
        billDialog.setUndecorated(true);
        billDialog.setSize(600, 500);
        billDialog.setLocationRelativeTo(parentFrame);
        billDialog.setLayout(null);
        billDialog.getContentPane().setBackground(new Color(239,204,150));

        // Header
        JLabel headerLabel = new JLabel("<html><center><b>" + userName + "<br>Table no. " + order.getTableNumber() + ".<br>" + order.getOrderTime() + "</b></center></html>");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 22));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBounds(0, 10, 600, 80);
        billDialog.add(headerLabel);

        // Close button
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 24));
        closeButton.setBounds(550, 20, 40, 40);
        closeButton.setBackground(new Color(239,204,150));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBorder(null);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> billDialog.dispose());
        billDialog.add(closeButton);

        // Items panel (rounded)
        JPanel itemsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        itemsPanel.setLayout(null);
        itemsPanel.setBounds(40, 100, 520, 270);
        billDialog.add(itemsPanel);

        // List items
        int y = 20;
        if (order.getOrderItems() != null) {
            for (restaurant.management.system.model.OrderData.OrderItem item : order.getOrderItems()) {
                JLabel itemLabel = new JLabel("<html><i>" + item.getItemName() + "</i></html>");
                itemLabel.setFont(new Font("Serif", Font.ITALIC, 20));
                itemLabel.setBounds(20, y, 250, 30);
                itemsPanel.add(itemLabel);

                JLabel qtyPriceLabel = new JLabel(item.getQuantity() + "x" + (int)item.getPrice() + "= Rs. " + (int)item.getSubtotal());
                qtyPriceLabel.setFont(new Font("Serif", Font.BOLD, 18));
                qtyPriceLabel.setBounds(300, y, 200, 30);
                qtyPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                itemsPanel.add(qtyPriceLabel);
                y += 35;
            }
        }

        // Total label
        JLabel totalLabel = new JLabel("Total: Rs." + String.format("%.0f", order.getTotalAmount()));
        totalLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
        totalLabel.setBounds(320, 400, 250, 40);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        billDialog.add(totalLabel);

        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Serif", Font.BOLD, 20));
        deleteButton.setBackground(new Color(255, 153, 0));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBounds(200, 420, 200, 40);
        deleteButton.setFocusPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.addActionListener(e -> {
            // Only remove from UI, not from database
            refreshAllOrderViews();
            JOptionPane.showMessageDialog(billDialog, "Are you sure you want to delete Bill ", "Confirm Deletion", JOptionPane.INFORMATION_MESSAGE);
            billDialog.dispose();
        });
        billDialog.add(deleteButton);

        billDialog.setVisible(true);
    }

    private void refreshAllOrderViews() {
        try {
            // Get fresh data from database
            java.util.List<restaurant.management.system.model.OrderData> pendingOrders = orderDao.getOrdersByStatus("PENDING");
            java.util.List<restaurant.management.system.model.OrderData> receivedOrders = orderDao.getOrdersByStatus("RECEIVED");
            java.util.List<restaurant.management.system.model.OrderData> billedOrders = orderDao.getOrdersByStatus("BILLED");
            
            // Refresh AdminOrdersView if it's the parent frame
            if (parentFrame instanceof restaurant.management.system.view.AdminOrdersView) {
                restaurant.management.system.view.AdminOrdersView adminView = (restaurant.management.system.view.AdminOrdersView) parentFrame;
                adminView.displayPendingOrders(pendingOrders);
                adminView.displayReceivedOrders(receivedOrders);
                adminView.displayBilledOrders(billedOrders);
            }
            
            // Refresh StaffOrdersView if it's the parent frame
            if (parentFrame instanceof restaurant.management.system.view.StaffOrdersView) {
                restaurant.management.system.view.StaffOrdersView staffView = (restaurant.management.system.view.StaffOrdersView) parentFrame;
                staffView.displayPendingOrders(pendingOrders);
                staffView.displayReceivedOrders(receivedOrders);
                staffView.displayBilledOrders(billedOrders);
            }
        } catch (Exception e) {
            System.err.println("Error refreshing order views: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 