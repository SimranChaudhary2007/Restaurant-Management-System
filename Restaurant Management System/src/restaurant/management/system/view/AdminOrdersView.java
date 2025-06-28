/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.Image;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import restaurant.management.system.controller.AdminOrdersController;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import restaurant.management.system.UIElements.AdminOrderPanel;
import restaurant.management.system.model.OrderData;

/**
 *
 * @author acer
 */
public class AdminOrdersView extends javax.swing.JFrame {

    // Add missing variable declarations
    private javax.swing.JScrollPane pendingScrollPane;
    private javax.swing.JPanel pendingOrdersPanel;
    private javax.swing.JScrollPane receivedScrollPane;
    private javax.swing.JPanel receivedOrdersPanel;
    private javax.swing.JScrollPane billedScrollPane;
    private javax.swing.JPanel billedOrdersPanel;

    /**
     * Creates new form AdminMenuView
     */
    public AdminOrdersView() {
        initComponents();
        scaleImage1();
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage7();
        scaleImage8();
        scaleImage9();
        setupPendingTab();
        setupReceivedTab();
        JTabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets = new java.awt.Insets(0, 0, 0, 0);
            contentBorderInsets = new java.awt.Insets(0, 0, 0, 0);
        }
        
        @Override
        protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
            return 0;
        }
        
        @Override
        protected int calculateTabAreaWidth(int tabPlacement, int vertRunCount, int maxTabWidth) {
            return 0;
        }
    });
   
    }
    
    private void setupPendingTab() {
        // Create scroll pane for pending orders
        pendingScrollPane = new JScrollPane();
        pendingScrollPane.setBackground(new Color(241, 237, 238));
        pendingScrollPane.setBorder(null);
        pendingScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pendingScrollPane.setVerticalScrollBar(scrollBarCustom1);
        
        // Create panel to hold order panels
        pendingOrdersPanel = new JPanel();
        pendingOrdersPanel.setBackground(new Color(241, 237, 238));
        pendingOrdersPanel.setLayout(null); // Use null layout for absolute positioning
        
        pendingScrollPane.setViewportView(pendingOrdersPanel);
        
        // Add scroll pane to pending tab
        pendingTab.setLayout(null); // Use null layout for absolute positioning
        pendingTab.add(pendingScrollPane);
        pendingScrollPane.setBounds(20, 20, 1010, 540);
    }
    
    private void setupReceivedTab() {
        // Create scroll pane for received orders
        receivedScrollPane = new JScrollPane();
        receivedScrollPane.setBackground(new Color(241, 237, 238));
        receivedScrollPane.setBorder(null);
        receivedScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        receivedScrollPane.setVerticalScrollBar(scrollBarCustom1);
        
        // Create panel to hold order panels
        receivedOrdersPanel = new JPanel();
        receivedOrdersPanel.setBackground(new Color(241, 237, 238));
        receivedOrdersPanel.setLayout(null); // Use null layout for absolute positioning
        
        receivedScrollPane.setViewportView(receivedOrdersPanel);
        
        // Add scroll pane to received tab
        receivedTab.setLayout(null); // Use null layout for absolute positioning
        receivedTab.add(receivedScrollPane);
        receivedScrollPane.setBounds(20, 20, 1010, 540);
    }
    
    public void refreshOrderDisplay() {
        // This method will be called from AdminOrderPanel when status is updated
        // The controller will handle the actual refresh logic
        if (getJTabbedPane().getSelectedIndex() == 0) {
            // Currently on pending tab, refresh pending orders
            // The controller will call displayPendingOrders
        } else if (getJTabbedPane().getSelectedIndex() == 1) {
            // Currently on received tab, refresh received orders
            // The controller will call displayReceivedOrders
        }
    }
    
    public void displayPendingOrders(List<OrderData> orders) {
        pendingOrdersPanel.removeAll();

        if (orders == null || orders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("No pending orders available", SwingConstants.CENTER);
            noOrdersLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 18));
            noOrdersLabel.setForeground(new Color(139, 125, 107));

            // Use setBounds for positioning
            pendingOrdersPanel.add(noOrdersLabel);
            noOrdersLabel.setBounds(0, 50, 990, 100);

        } else {
            int yPosition = 20; // Starting Y position
            int panelHeight = 70; // Height of each order panel
            int spacing = 15; // Spacing between panels

            for (OrderData order : orders) {
                try {
                    // Create AdminOrderPanel for each order
                    AdminOrderPanel orderPanel = new AdminOrderPanel(order, this);

                    // Add with setBounds for positioning
                    pendingOrdersPanel.add(orderPanel);
                    orderPanel.setBounds(20, yPosition, 970, panelHeight);

                    yPosition += panelHeight + spacing;

                } catch (Exception e) {
                    System.err.println("Error creating order panel for order ID: " + 
                                     (order != null ? order.getOrderId() : "null"));
                    e.printStackTrace();
                }
            }

            // Set the preferred size of pendingOrdersPanel to accommodate all panels
            int totalHeight = yPosition + 50; // Add some bottom padding
            pendingOrdersPanel.setPreferredSize(new java.awt.Dimension(990, totalHeight));
        }

        // Refresh the display
        pendingOrdersPanel.revalidate();
        pendingOrdersPanel.repaint();
        pendingScrollPane.revalidate();
        pendingScrollPane.repaint();
    }
    
    public void displayReceivedOrders(List<OrderData> orders) {
        receivedOrdersPanel.removeAll();

        if (orders == null || orders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("No received orders available", SwingConstants.CENTER);
            noOrdersLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 18));
            noOrdersLabel.setForeground(new Color(139, 125, 107));

            // Use setBounds for positioning
            receivedOrdersPanel.add(noOrdersLabel);
            noOrdersLabel.setBounds(0, 50, 990, 100);

        } else {
            int yPosition = 20; // Starting Y position
            int panelHeight = 70; // Height of each order panel
            int spacing = 15; // Spacing between panels

            for (OrderData order : orders) {
                try {
                    // Create AdminOrderPanel for each order
                    AdminOrderPanel orderPanel = new AdminOrderPanel(order, this);

                    // Add with setBounds for positioning
                    receivedOrdersPanel.add(orderPanel);
                    orderPanel.setBounds(20, yPosition, 970, panelHeight);

                    yPosition += panelHeight + spacing;

                } catch (Exception e) {
                    System.err.println("Error creating order panel for order ID: " + 
                                     (order != null ? order.getOrderId() : "null"));
                    e.printStackTrace();
                }
            }

            // Set the preferred size of receivedOrdersPanel to accommodate all panels
            int totalHeight = yPosition + 50; // Add some bottom padding
            receivedOrdersPanel.setPreferredSize(new java.awt.Dimension(990, totalHeight));
        }

        // Refresh the display
        receivedOrdersPanel.revalidate();
        receivedOrdersPanel.repaint();
        receivedScrollPane.revalidate();
        receivedScrollPane.repaint();
    }
    
    public void displayBilledOrders(List<OrderData> orders) {
        if (billedOrdersPanel == null) {
            billedOrdersPanel = new JPanel();
            billedOrdersPanel.setBackground(new Color(241, 237, 238));
            billedOrdersPanel.setLayout(null);
            billedScrollPane = new JScrollPane();
            billedScrollPane.setBackground(new Color(241, 237, 238));
            billedScrollPane.setBorder(null);
            billedScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            billedScrollPane.setViewportView(billedOrdersPanel);
            billTab.setLayout(null);
            billTab.add(billedScrollPane);
            billedScrollPane.setBounds(20, 20, 1010, 540);
        }
        billedOrdersPanel.removeAll();
        if (orders == null || orders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("No billed orders available", SwingConstants.CENTER);
            noOrdersLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 18));
            noOrdersLabel.setForeground(new Color(139, 125, 107));
            billedOrdersPanel.add(noOrdersLabel);
            noOrdersLabel.setBounds(0, 50, 990, 100);
        } else {
            int yPosition = 20;
            int panelHeight = 70;
            int spacing = 15;
            for (OrderData order : orders) {
                try {
                    AdminOrderPanel orderPanel = new AdminOrderPanel(order, this);
                    billedOrdersPanel.add(orderPanel);
                    orderPanel.setBounds(20, yPosition, 970, panelHeight);
                    yPosition += panelHeight + spacing;
                } catch (Exception e) {
                    System.err.println("Error creating billed order panel for order ID: " + (order != null ? order.getOrderId() : "null"));
                    e.printStackTrace();
                }
            }
            int totalHeight = yPosition + 50;
            billedOrdersPanel.setPreferredSize(new java.awt.Dimension(990, totalHeight));
        }
        billedOrdersPanel.revalidate();
        billedOrdersPanel.repaint();
        billedScrollPane.revalidate();
        billedScrollPane.repaint();
    }
    
    public JPanel getPendingOrdersPanel() {
        return pendingOrdersPanel;
    }
    
    public JScrollPane getPendingScrollPane() {
        return pendingScrollPane;
    }
    
    public void scaleImage1(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/home.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(homeIcon.getWidth(), homeIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        homeIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage2(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/user.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(profileIcon.getWidth(), profileIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        profileIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage3(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/menu.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(menuIcon.getWidth(), menuIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        menuIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage4(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/check.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(orderIcon.getWidth(), orderIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        orderIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage5(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/logout.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(logoutIcon.getWidth(), logoutIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        logoutIcon.setIcon(scaledIcon);
    }
    public void scaleImage7(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/recived.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(recivedIcon.getWidth(), recivedIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        recivedIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage8(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/pending.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(pendingIcon.getWidth(), pendingIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        pendingIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage9(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/bill.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(billIcon.getWidth(), billIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        billIcon.setIcon(scaledIcon);
    }
    
    
    
    
   
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollBarCustom1 = new restaurant.management.system.UIElements.ScrollBarCustom();
        jPanel3 = new javax.swing.JPanel();
        panelRound1 = new restaurant.management.system.UIElements.PanelRound();
        recivedIcon = new javax.swing.JLabel();
        pendingIcon = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        RecivedButton = new restaurant.management.system.UIElements.CustomButton();
        PendingButton = new restaurant.management.system.UIElements.CustomButton();
        jLabel6 = new javax.swing.JLabel();
        billIcon = new javax.swing.JLabel();
        BillButton = new restaurant.management.system.UIElements.CustomButton();
        jLabel7 = new javax.swing.JLabel();
        panelRound2 = new restaurant.management.system.UIElements.PanelRound();
        JTabbedPane = new javax.swing.JTabbedPane();
        pendingTab = new javax.swing.JPanel();
        panelRound4 = new restaurant.management.system.UIElements.PanelRound();
        RefreshButton = new restaurant.management.system.UIElements.CustomButton();
        receivedTab = new javax.swing.JPanel();
        RefreshButton1 = new restaurant.management.system.UIElements.CustomButton();
        billTab = new javax.swing.JPanel();
        RefreshButton2 = new restaurant.management.system.UIElements.CustomButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        logoutlabel = new javax.swing.JLabel();
        homelabel = new javax.swing.JLabel();
        profilelabel = new javax.swing.JLabel();
        menulabel = new javax.swing.JLabel();
        orderlabel = new javax.swing.JLabel();
        homeIcon = new javax.swing.JLabel();
        profileIcon = new javax.swing.JLabel();
        menuIcon = new javax.swing.JLabel();
        orderIcon = new javax.swing.JLabel();
        logoutIcon = new javax.swing.JLabel();
        logoIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admins Orders");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(239, 204, 150));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(241, 237, 238));
        panelRound1.setPreferredSize(new java.awt.Dimension(80, 630));
        panelRound1.setRoundBottonLeft(70);
        panelRound1.setRoundBottonRight(70);
        panelRound1.setRoundTopLeft(70);
        panelRound1.setRoundTopRight(70);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        recivedIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/recived.png"))); // NOI18N
        panelRound1.add(recivedIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 60, 70));

        pendingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/pending.png"))); // NOI18N
        panelRound1.add(pendingIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 60, 70));

        jLabel5.setFont(new java.awt.Font("Mongolian Baiti", 1, 15)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Pending");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelRound1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 80, -1));

        RecivedButton.setBackground(new java.awt.Color(227, 143, 11));
        RecivedButton.setBorderColor(new java.awt.Color(241, 237, 238));
        RecivedButton.setBorderPainted(false);
        RecivedButton.setColor(new java.awt.Color(227, 143, 11));
        RecivedButton.setColorClick(new java.awt.Color(227, 143, 11));
        RecivedButton.setColorOver(new java.awt.Color(51, 153, 0));
        RecivedButton.setRadius(50);
        RecivedButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        RecivedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecivedButtonActionPerformed(evt);
            }
        });
        panelRound1.add(RecivedButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 60, 120));

        PendingButton.setBorderColor(new java.awt.Color(241, 237, 238));
        PendingButton.setBorderPainted(false);
        PendingButton.setColor(new java.awt.Color(227, 143, 11));
        PendingButton.setColorClick(new java.awt.Color(227, 143, 11));
        PendingButton.setColorOver(new java.awt.Color(204, 0, 0));
        PendingButton.setRadius(50);
        PendingButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        PendingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendingButtonActionPerformed(evt);
            }
        });
        panelRound1.add(PendingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 120));

        jLabel6.setFont(new java.awt.Font("Mongolian Baiti", 1, 15)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Recived");
        panelRound1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 80, -1));

        billIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/bill.png"))); // NOI18N
        panelRound1.add(billIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 60, 70));

        BillButton.setBackground(new java.awt.Color(227, 143, 11));
        BillButton.setBorderColor(new java.awt.Color(241, 237, 238));
        BillButton.setBorderPainted(false);
        BillButton.setColor(new java.awt.Color(227, 143, 11));
        BillButton.setColorClick(new java.awt.Color(227, 143, 11));
        BillButton.setColorOver(new java.awt.Color(51, 153, 0));
        BillButton.setRadius(50);
        BillButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        BillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BillButtonActionPerformed(evt);
            }
        });
        panelRound1.add(BillButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 60, 120));

        jLabel7.setFont(new java.awt.Font("Mongolian Baiti", 1, 15)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Bill");
        panelRound1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 80, -1));

        jPanel3.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 80, 470));

        panelRound2.setBackground(new java.awt.Color(241, 237, 238));
        panelRound2.setPreferredSize(new java.awt.Dimension(1110, 630));
        panelRound2.setRoundBottonLeft(65);
        panelRound2.setRoundBottonRight(65);
        panelRound2.setRoundTopLeft(65);
        panelRound2.setRoundTopRight(65);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JTabbedPane.setBackground(new java.awt.Color(241, 237, 238));
        JTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        JTabbedPane.setToolTipText("");

        pendingTab.setBackground(new java.awt.Color(241, 237, 238));
        pendingTab.setForeground(new java.awt.Color(241, 237, 238));
        pendingTab.setAutoscrolls(true);
        pendingTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound4.setBackground(new java.awt.Color(227, 143, 11));
        panelRound4.setForeground(new java.awt.Color(227, 143, 11));
        panelRound4.setRoundBottonLeft(50);
        panelRound4.setRoundBottonRight(50);
        panelRound4.setRoundTopLeft(50);
        panelRound4.setRoundTopRight(50);
        panelRound4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pendingTab.add(panelRound4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, -1, 110));

        RefreshButton.setBackground(new java.awt.Color(239, 204, 150));
        RefreshButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RefreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/reload.png"))); // NOI18N
        RefreshButton.setBorderColor(new java.awt.Color(239, 204, 150));
        RefreshButton.setBorderPainted(false);
        RefreshButton.setFocusable(false);
        RefreshButton.setRadius(50);
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });
        pendingTab.add(RefreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 0, 40, 40));

        JTabbedPane.addTab("tab1", pendingTab);

        receivedTab.setBackground(new java.awt.Color(241, 237, 238));
        receivedTab.setForeground(new java.awt.Color(102, 255, 0));
        receivedTab.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        receivedTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RefreshButton1.setBackground(new java.awt.Color(239, 204, 150));
        RefreshButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RefreshButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/reload.png"))); // NOI18N
        RefreshButton1.setBorderColor(new java.awt.Color(239, 204, 150));
        RefreshButton1.setBorderPainted(false);
        RefreshButton1.setFocusable(false);
        RefreshButton1.setRadius(50);
        RefreshButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButton1ActionPerformed(evt);
            }
        });
        receivedTab.add(RefreshButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 0, 40, 40));

        JTabbedPane.addTab("tab2", receivedTab);

        RefreshButton2.setBackground(new java.awt.Color(239, 204, 150));
        RefreshButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RefreshButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/reload.png"))); // NOI18N
        RefreshButton2.setBorderColor(new java.awt.Color(239, 204, 150));
        RefreshButton2.setBorderPainted(false);
        RefreshButton2.setFocusable(false);
        RefreshButton2.setRadius(50);
        RefreshButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout billTabLayout = new javax.swing.GroupLayout(billTab);
        billTab.setLayout(billTabLayout);
        billTabLayout.setHorizontalGroup(
            billTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billTabLayout.createSequentialGroup()
                .addGap(0, 1010, Short.MAX_VALUE)
                .addComponent(RefreshButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        billTabLayout.setVerticalGroup(
            billTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billTabLayout.createSequentialGroup()
                .addComponent(RefreshButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 505, Short.MAX_VALUE))
        );

        JTabbedPane.addTab("tab3", billTab);

        panelRound2.add(JTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 1050, 580));

        jPanel3.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 1090, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 1320, 750));

        jPanel2.setBackground(new java.awt.Color(241, 237, 238));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        jLabel1.setText("Orders");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1320, 90));

        jPanel4.setBackground(new java.awt.Color(227, 143, 11));
        jPanel4.setPreferredSize(new java.awt.Dimension(225, 835));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel8.setText("Sajilo Serve");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 12, -1, -1));

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 52, 210, 1));

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 138, 165, 1));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 211, 165, 1));

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 275, 165, 1));

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 351, 165, 1));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 419, 165, 1));

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 491, 165, 1));

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 562, 165, 1));

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 634, 165, 1));

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 703, 165, 1));

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 778, 165, 1));

        logoutlabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        logoutlabel.setText("Logout");
        logoutlabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        logoutlabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(logoutlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 705, 230, 75));

        homelabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        homelabel.setText("Home");
        homelabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        homelabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(homelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 140, 250, 70));

        profilelabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        profilelabel.setText("Profile");
        profilelabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        profilelabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(profilelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 275, 230, 75));

        menulabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        menulabel.setText("Menu");
        menulabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        menulabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(menulabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 421, 230, 70));

        orderlabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        orderlabel.setText("Orders");
        orderlabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        orderlabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(orderlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 563, 230, 70));

        homeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/home.png"))); // NOI18N
        jPanel4.add(homeIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 152, 35, 35));

        profileIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/user.png"))); // NOI18N
        profileIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(profileIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 290, 35, 35));

        menuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/menu.png"))); // NOI18N
        jPanel4.add(menuIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 432, 35, 35));

        orderIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/check.png"))); // NOI18N
        jPanel4.add(orderIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 575, 35, 35));

        logoutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/logout.png"))); // NOI18N
        jPanel4.add(logoutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 722, 35, 35));

        logoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/Logo.png"))); // NOI18N
        jPanel4.add(logoIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 47, 40));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -5, 230, 840));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PendingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PendingButtonActionPerformed
        // Switch to the pending tab (index 0)
        JTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_PendingButtonActionPerformed

    private void RecivedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecivedButtonActionPerformed
        // Switch to the received tab (index 1)
        JTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_RecivedButtonActionPerformed

    private void BillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BillButtonActionPerformed
        // Switch to the bill tab (index 2)
        JTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_BillButtonActionPerformed

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        restaurant.management.system.dao.OrderDao orderDao = new restaurant.management.system.dao.OrderDao();
        java.util.List<restaurant.management.system.model.OrderData> pendingOrders = orderDao.getOrdersByStatus("PENDING");
        java.util.List<restaurant.management.system.model.OrderData> receivedOrders = orderDao.getOrdersByStatus("RECEIVED");
        displayPendingOrders(pendingOrders);
        displayReceivedOrders(receivedOrders);
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void RefreshButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButton1ActionPerformed
        restaurant.management.system.dao.OrderDao orderDao = new restaurant.management.system.dao.OrderDao();
        java.util.List<restaurant.management.system.model.OrderData> pendingOrders = orderDao.getOrdersByStatus("PENDING");
        java.util.List<restaurant.management.system.model.OrderData> receivedOrders = orderDao.getOrdersByStatus("RECEIVED");
        displayPendingOrders(pendingOrders);
        displayReceivedOrders(receivedOrders);
    }//GEN-LAST:event_RefreshButton1ActionPerformed

    private void RefreshButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButton2ActionPerformed
        restaurant.management.system.dao.OrderDao orderDao = new restaurant.management.system.dao.OrderDao();
        java.util.List<restaurant.management.system.model.OrderData> pendingOrders = orderDao.getOrdersByStatus("PENDING");
        java.util.List<restaurant.management.system.model.OrderData> receivedOrders = orderDao.getOrdersByStatus("RECEIVED");
        displayPendingOrders(pendingOrders);
        displayReceivedOrders(receivedOrders);
    }//GEN-LAST:event_RefreshButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>
        try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(AdminOrdersView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            AdminOrdersView view = new AdminOrdersView();
            new AdminOrdersView();
            view.setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private restaurant.management.system.UIElements.CustomButton BillButton;
    private javax.swing.JTabbedPane JTabbedPane;
    private restaurant.management.system.UIElements.CustomButton PendingButton;
    private restaurant.management.system.UIElements.CustomButton RecivedButton;
    private restaurant.management.system.UIElements.CustomButton RefreshButton;
    private restaurant.management.system.UIElements.CustomButton RefreshButton1;
    private restaurant.management.system.UIElements.CustomButton RefreshButton2;
    private javax.swing.JLabel billIcon;
    private javax.swing.JPanel billTab;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel homelabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel logoIcon;
    private javax.swing.JLabel logoutIcon;
    private javax.swing.JLabel logoutlabel;
    private javax.swing.JLabel menuIcon;
    private javax.swing.JLabel menulabel;
    private javax.swing.JLabel orderIcon;
    private javax.swing.JLabel orderlabel;
    private restaurant.management.system.UIElements.PanelRound panelRound1;
    private restaurant.management.system.UIElements.PanelRound panelRound2;
    private restaurant.management.system.UIElements.PanelRound panelRound4;
    private javax.swing.JLabel pendingIcon;
    private javax.swing.JPanel pendingTab;
    private javax.swing.JLabel profileIcon;
    private javax.swing.JLabel profilelabel;
    private javax.swing.JPanel receivedTab;
    private javax.swing.JLabel recivedIcon;
    private restaurant.management.system.UIElements.ScrollBarCustom scrollBarCustom1;
    // End of variables declaration//GEN-END:variables

    public void homeNavigation(MouseListener listener){
        homelabel.addMouseListener(listener);
    }
    public JLabel getHomelabel(){
        return homelabel;
    }
    public void profileNavigation(MouseListener listener){
        profilelabel.addMouseListener(listener);
    }
    public JLabel getProfilelabel(){
        return profilelabel;
    }
    public void menuNavigation(MouseListener listener){
        menulabel.addMouseListener(listener);
    }
    public JLabel getMenulabel(){
        return menulabel;
    }
    public void logoutNavigation(MouseListener listener){
        logoutlabel.addMouseListener(listener);
    }
    public JLabel getLogoutlabel(){
        return logoutlabel;
    }
    
    public JButton getPendingButton() {
        return PendingButton;
    }

    public JButton getRecivedButton() {
        return RecivedButton;
    }
    
    public JButton getBillButton() {
        return BillButton;
    }
    
    public JTabbedPane getJTabbedPane() {
        return JTabbedPane;
    }
}
