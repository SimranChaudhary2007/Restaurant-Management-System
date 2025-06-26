/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.view.CustomerOrderView.CancelledOrderData;
import restaurant.management.system.view.CustomerOrderView.CancelledOrderManager;

/**
 *
 * @author ACER
 */
public class StaffHomeView extends javax.swing.JFrame {
    private JPanel cancelledOrdersPanel;
    private JScrollPane cancelledOrdersScrollPane;

    public StaffHomeView() {
        initComponents();
        scaleImage1();
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage6();
        setupCancelledOrdersDisplay();
    }
    
    private void setupCancelledOrdersDisplay() {
        // Setup panelRound1 to display cancelled orders
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        // Add a title label
        JLabel titleLabel = new JLabel("Cancelled Orders");
        titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 0));
        panelRound1.add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 30));
        
        // Create scroll pane for cancelled orders
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(new Color(241, 237, 238));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Create panel to hold cancelled order cards
        JPanel cancelledOrdersPanel = new JPanel();
        cancelledOrdersPanel.setBackground(new Color(241, 237, 238));
        cancelledOrdersPanel.setLayout(new BoxLayout(cancelledOrdersPanel, BoxLayout.Y_AXIS));
        
        scrollPane.setViewportView(cancelledOrdersPanel);
        panelRound1.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 630, 550));
        
        // Store reference to the panel for later use
        this.cancelledOrdersPanel = cancelledOrdersPanel;
        this.cancelledOrdersScrollPane = scrollPane;
        
        // Load and display cancelled orders
        displayCancelledOrders();
    }
    
    public void displayCancelledOrders() {
        if (cancelledOrdersPanel == null) return;
        
        cancelledOrdersPanel.removeAll();
        
        List<CancelledOrderData> cancelledOrders = CancelledOrderManager.getCancelledOrders();
        
        if (cancelledOrders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("No cancelled orders", SwingConstants.CENTER);
            noOrdersLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
            noOrdersLabel.setForeground(new Color(139, 125, 107));
            cancelledOrdersPanel.add(noOrdersLabel);
        } else {
            for (CancelledOrderData order : cancelledOrders) {
                JPanel orderCard = createCancelledOrderCard(order);
                cancelledOrdersPanel.add(orderCard);
                cancelledOrdersPanel.add(Box.createVerticalStrut(10)); // Add spacing
            }
        }
        
        cancelledOrdersPanel.revalidate();
        cancelledOrdersPanel.repaint();
    }
    
    private JPanel createCancelledOrderCard(CancelledOrderData order) {
        JPanel card = new JPanel();
        card.setBackground(new Color(255, 200, 200)); // Light red background for cancelled orders
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 2));
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(600, 120));
        card.setPreferredSize(new Dimension(600, 120));
        
        // Header with order info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 200, 200));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        
        JLabel orderInfoLabel = new JLabel("Order ID: " + order.getOrderId() + " | Table: " + order.getTableNumber());
        orderInfoLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 16));
        headerPanel.add(orderInfoLabel, BorderLayout.NORTH);
        
        JLabel timeLabel = new JLabel("Date: " + order.getOrderDate() + " | Time: " + order.getOrderTime());
        timeLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 14));
        headerPanel.add(timeLabel, BorderLayout.CENTER);
        
        JLabel totalLabel = new JLabel("Total: Rs. " + String.format("%.2f", order.getTotalAmount()));
        totalLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
        totalLabel.setForeground(new Color(220, 53, 69));
        headerPanel.add(totalLabel, BorderLayout.SOUTH);
        
        card.add(headerPanel, BorderLayout.NORTH);
        
        // Items panel
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(new Color(255, 200, 200));
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        
        JLabel itemsLabel = new JLabel("Items:");
        itemsLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 12));
        itemsPanel.add(itemsLabel);
        
        if (order.getOrderItems() != null) {
            for (OrderData.OrderItem item : order.getOrderItems()) {
                JLabel itemLabel = new JLabel("• " + item.getItemName() + " x" + item.getQuantity());
                itemLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
                itemsPanel.add(itemLabel);
            }
        }
        
        card.add(itemsPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    public void refreshCancelledOrders() {
        displayCancelledOrders();
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
        logoutIcon.setIcon(scaledIcon); // Centers it on screen (optional)
    }
    
    public void scaleImage6(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/Logo.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(logoIcon.getWidth(), logoIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        logoIcon.setIcon(scaledIcon);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelRound1 = new restaurant.management.system.UIElements.PanelRound();
        panelRound2 = new restaurant.management.system.UIElements.PanelRound();
        suggestionButton = new restaurant.management.system.UIElements.CustomButton();
        noticeButton = new restaurant.management.system.UIElements.CustomButton();
        requestButton = new restaurant.management.system.UIElements.CustomButton();
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
        setSize(new java.awt.Dimension(1535, 835));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(241, 237, 238));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        jLabel1.setText("Home");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 11, 1320, 90));

        jPanel3.setBackground(new java.awt.Color(239, 204, 150));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(241, 237, 238));
        panelRound1.setRoundBottonLeft(65);
        panelRound1.setRoundBottonRight(65);
        panelRound1.setRoundTopLeft(65);
        panelRound1.setRoundTopRight(65);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 670, 630));

        panelRound2.setBackground(new java.awt.Color(241, 237, 238));
        panelRound2.setRoundBottonLeft(65);
        panelRound2.setRoundBottonRight(65);
        panelRound2.setRoundTopLeft(65);
        panelRound2.setRoundTopRight(65);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        suggestionButton.setBackground(new java.awt.Color(192, 137, 19));
        suggestionButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        suggestionButton.setText("Suggestion");
        suggestionButton.setBorderColor(new java.awt.Color(192, 137, 19));
        suggestionButton.setColor(new java.awt.Color(192, 137, 19));
        suggestionButton.setColorClick(new java.awt.Color(221, 188, 0));
        suggestionButton.setColorOver(new java.awt.Color(221, 188, 0));
        suggestionButton.setFocusable(false);
        suggestionButton.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        suggestionButton.setRadius(25);
        suggestionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionButtonActionPerformed(evt);
            }
        });
        panelRound2.add(suggestionButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 300, 100));

        noticeButton.setBackground(new java.awt.Color(183, 103, 8));
        noticeButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        noticeButton.setText("Notices");
        noticeButton.setBorderColor(new java.awt.Color(183, 103, 8));
        noticeButton.setColor(new java.awt.Color(183, 103, 8));
        noticeButton.setColorClick(new java.awt.Color(224, 112, 0));
        noticeButton.setColorOver(new java.awt.Color(224, 112, 0));
        noticeButton.setFocusable(false);
        noticeButton.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        noticeButton.setRadius(25);
        noticeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noticeButtonActionPerformed(evt);
            }
        });
        panelRound2.add(noticeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 300, 100));

        requestButton.setBackground(new java.awt.Color(227, 103, 12));
        requestButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        requestButton.setText("Request");
        requestButton.setBorderColor(new java.awt.Color(227, 103, 12));
        requestButton.setColor(new java.awt.Color(227, 103, 12));
        requestButton.setColorClick(new java.awt.Color(255, 145, 8));
        requestButton.setColorOver(new java.awt.Color(255, 145, 8));
        requestButton.setFocusable(false);
        requestButton.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        requestButton.setRadius(25);
        requestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestButtonActionPerformed(evt);
            }
        });
        panelRound2.add(requestButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 300, 100));

        jPanel3.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 70, 430, 630));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 96, 1320, 750));

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

    private void noticeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noticeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noticeButtonActionPerformed

    private void suggestionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestionButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suggestionButtonActionPerformed

    private void requestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_requestButtonActionPerformed

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
            java.util.logging.Logger.getLogger(StaffHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffHomeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel homelabel;
    private javax.swing.JLabel jLabel1;
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
    private restaurant.management.system.UIElements.CustomButton noticeButton;
    private javax.swing.JLabel orderIcon;
    private javax.swing.JLabel orderlabel;
    private restaurant.management.system.UIElements.PanelRound panelRound1;
    private restaurant.management.system.UIElements.PanelRound panelRound2;
    private javax.swing.JLabel profileIcon;
    private javax.swing.JLabel profilelabel;
    private restaurant.management.system.UIElements.CustomButton requestButton;
    private restaurant.management.system.UIElements.CustomButton suggestionButton;
    // End of variables declaration//GEN-END:variables

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
    public void orderNavigation(MouseListener listener){
        orderlabel.addMouseListener(listener);
    }
    public JLabel getOrderlabel(){
        return orderlabel;
    }
    public void logoutNavigation(MouseListener listener){
        logoutlabel.addMouseListener(listener);
    }
    public JLabel getLogoutlabel(){
        return logoutlabel;
    }
    
    
    public void suggestionNavigation(ActionListener listener) {
        suggestionButton.addActionListener(listener);
    }
    public void noticeNavigation(ActionListener listener) {
        noticeButton.addActionListener(listener);
    }
    public void requestButtonNavigation(ActionListener listener) {
        requestButton.addActionListener(listener);
    }
}
