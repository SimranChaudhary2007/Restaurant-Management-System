/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import restaurant.management.system.UIElements.StaffApproveRequest;
import restaurant.management.system.UIElements.CustomerOrderPanel;
import restaurant.management.system.model.StaffRequestData;
import restaurant.management.system.model.OrderData;

/**
 *
 * @author labis
 */
public class AdminHomeView extends javax.swing.JFrame {
    public static final int STAFF_TAB_INDEX = 0;
    public static final int CUSTOMER_TAB_INDEX = 1;
    /**
     * Creates new form AdminHomeView
     */
    public AdminHomeView() {
        initComponents();
        scaleImage1();
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage6();
        scaleImage7();
        scaleImage8();
        
        approveRequest.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
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
    
    public void scaleImage6(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/Logo.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(logoIcon.getWidth(), logoIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        logoIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage7(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/staff.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(staffIcon.getWidth(), staffIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        staffIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage8(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/customer.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(customerIcon.getWidth(), customerIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        customerIcon.setIcon(scaledIcon);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
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
        jLabel4 = new javax.swing.JLabel();
        profilelabel = new javax.swing.JLabel();
        menulabel = new javax.swing.JLabel();
        orderlabel = new javax.swing.JLabel();
        homeIcon = new javax.swing.JLabel();
        profileIcon = new javax.swing.JLabel();
        menuIcon = new javax.swing.JLabel();
        orderIcon = new javax.swing.JLabel();
        logoutIcon = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        logoIcon = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelRound2 = new restaurant.management.system.UIElements.PanelRound();
        analysisButton = new restaurant.management.system.UIElements.CustomButton();
        staffInfoButton = new restaurant.management.system.UIElements.CustomButton();
        suggestionButton = new restaurant.management.system.UIElements.CustomButton();
        noticeButton = new restaurant.management.system.UIElements.CustomButton();
        panelRound3 = new restaurant.management.system.UIElements.PanelRound();
        customerIcon = new javax.swing.JLabel();
        staffIcon = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        CustomerButton = new restaurant.management.system.UIElements.CustomButton();
        StaffButton = new restaurant.management.system.UIElements.CustomButton();
        jLabel6 = new javax.swing.JLabel();
        panelRound1 = new restaurant.management.system.UIElements.PanelRound();
        scroll = new javax.swing.JScrollPane();
        approveRequest = new javax.swing.JTabbedPane();
        staff = new javax.swing.JPanel();
        customerTabPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(241, 237, 238));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        jLabel1.setText("Home");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1320, 90));

        jPanel1.setBackground(new java.awt.Color(227, 143, 11));
        jPanel1.setPreferredSize(new java.awt.Dimension(225, 835));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 52, 210, 1));

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

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 138, 165, 1));

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

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 211, 165, 1));

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

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 275, 165, 1));

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

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 351, 165, 1));

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

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 419, 165, 1));

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

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 491, 165, 1));

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

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 562, 165, 1));

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

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 634, 165, 1));

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

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 703, 165, 1));

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

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 778, 165, 1));

        logoutlabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        logoutlabel.setText("Logout");
        logoutlabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        logoutlabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(logoutlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 705, 230, 75));

        jLabel4.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel4.setText("Home");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 140, 250, 70));

        profilelabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        profilelabel.setText("Profile");
        profilelabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        profilelabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(profilelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 275, 230, 75));

        menulabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        menulabel.setText("Menu");
        menulabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        menulabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(menulabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 421, 230, 70));

        orderlabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        orderlabel.setText("Orders");
        orderlabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        orderlabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(orderlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 563, 230, 70));

        homeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/home.png"))); // NOI18N
        jPanel1.add(homeIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 152, 35, 35));

        profileIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/user.png"))); // NOI18N
        profileIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(profileIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 290, 35, 35));
        profileIcon.getAccessibleContext().setAccessibleName("");

        menuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/menu.png"))); // NOI18N
        jPanel1.add(menuIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 432, 35, 35));

        orderIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/check.png"))); // NOI18N
        jPanel1.add(orderIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 575, 35, 35));

        logoutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/logout.png"))); // NOI18N
        jPanel1.add(logoutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 722, 35, 35));

        jLabel8.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel8.setText("Sajilo Serve");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 12, -1, -1));

        logoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/Logo.png"))); // NOI18N
        jPanel1.add(logoIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 47, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -5, 230, 840));

        jPanel3.setBackground(new java.awt.Color(239, 204, 150));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound2.setBackground(new java.awt.Color(241, 237, 238));
        panelRound2.setRoundBottonLeft(65);
        panelRound2.setRoundBottonRight(65);
        panelRound2.setRoundTopLeft(65);
        panelRound2.setRoundTopRight(65);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        analysisButton.setBackground(new java.awt.Color(227, 143, 12));
        analysisButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        analysisButton.setText("Analysis");
        analysisButton.setBorderColor(new java.awt.Color(227, 143, 12));
        analysisButton.setColor(new java.awt.Color(227, 143, 12));
        analysisButton.setColorClick(new java.awt.Color(255, 169, 8));
        analysisButton.setColorOver(new java.awt.Color(255, 169, 8));
        analysisButton.setFocusable(false);
        analysisButton.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        analysisButton.setRadius(25);
        panelRound2.add(analysisButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 300, 100));

        staffInfoButton.setBackground(new java.awt.Color(227, 103, 12));
        staffInfoButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        staffInfoButton.setText("Staff");
        staffInfoButton.setBorderColor(new java.awt.Color(227, 103, 12));
        staffInfoButton.setColor(new java.awt.Color(227, 103, 12));
        staffInfoButton.setColorClick(new java.awt.Color(255, 145, 8));
        staffInfoButton.setColorOver(new java.awt.Color(255, 145, 8));
        staffInfoButton.setFocusable(false);
        staffInfoButton.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        staffInfoButton.setRadius(25);
        panelRound2.add(staffInfoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 300, 100));

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
        panelRound2.add(suggestionButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 300, 100));

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

        jPanel3.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 60, 430, 630));

        panelRound3.setBackground(new java.awt.Color(241, 237, 238));
        panelRound3.setPreferredSize(new java.awt.Dimension(80, 630));
        panelRound3.setRoundBottonLeft(70);
        panelRound3.setRoundBottonRight(70);
        panelRound3.setRoundTopLeft(70);
        panelRound3.setRoundTopRight(70);
        panelRound3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        customerIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/recived.png"))); // NOI18N
        panelRound3.add(customerIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 190, 55, 70));

        staffIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/pending.png"))); // NOI18N
        panelRound3.add(staffIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 40, 50, 60));

        jLabel5.setFont(new java.awt.Font("Mongolian Baiti", 1, 15)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Staff");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelRound3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 80, -1));

        CustomerButton.setBackground(new java.awt.Color(227, 143, 11));
        CustomerButton.setBorderColor(new java.awt.Color(241, 237, 238));
        CustomerButton.setBorderPainted(false);
        CustomerButton.setColor(new java.awt.Color(227, 143, 11));
        CustomerButton.setColorClick(new java.awt.Color(255, 204, 0));
        CustomerButton.setColorOver(new java.awt.Color(255, 204, 0));
        CustomerButton.setRadius(50);
        CustomerButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustomerButtonActionPerformed(evt);
            }
        });
        panelRound3.add(CustomerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 60, 120));

        StaffButton.setBorderColor(new java.awt.Color(241, 237, 238));
        StaffButton.setBorderPainted(false);
        StaffButton.setColor(new java.awt.Color(227, 143, 11));
        StaffButton.setColorClick(new java.awt.Color(255, 204, 0));
        StaffButton.setColorOver(new java.awt.Color(255, 204, 0));
        StaffButton.setRadius(50);
        StaffButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        StaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StaffButtonActionPerformed(evt);
            }
        });
        panelRound3.add(StaffButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 120));

        jLabel6.setFont(new java.awt.Font("Mongolian Baiti", 1, 15)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Customer");
        panelRound3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 80, -1));

        jPanel3.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 210, 80, 310));

        panelRound1.setBackground(new java.awt.Color(241, 237, 238));
        panelRound1.setRoundBottonLeft(65);
        panelRound1.setRoundBottonRight(65);
        panelRound1.setRoundTopLeft(65);
        panelRound1.setRoundTopRight(65);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setVerifyInputWhenFocusTarget(false);
        scroll.setVerticalScrollBar(scrollBarCustom1);

        javax.swing.GroupLayout staffLayout = new javax.swing.GroupLayout(staff);
        staff.setLayout(staffLayout);
        staffLayout.setHorizontalGroup(
            staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        staffLayout.setVerticalGroup(
            staffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        approveRequest.addTab("Staff", staff);

        javax.swing.GroupLayout customerTabPanelLayout = new javax.swing.GroupLayout(customerTabPanel);
        customerTabPanel.setLayout(customerTabPanelLayout);
        customerTabPanelLayout.setHorizontalGroup(
            customerTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        customerTabPanelLayout.setVerticalGroup(
            customerTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        approveRequest.addTab("Customer", customerTabPanel);

        scroll.setViewportView(approveRequest);

        panelRound1.add(scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 640, 560));

        jPanel3.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 60, 680, 630));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 85, 1320, 750));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomerButtonActionPerformed
        // Switch to customer tab
        approveRequest.setSelectedIndex(CUSTOMER_TAB_INDEX);
    }//GEN-LAST:event_CustomerButtonActionPerformed

    private void StaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StaffButtonActionPerformed
        // Switch to staff tab
        approveRequest.setSelectedIndex(STAFF_TAB_INDEX);
    }//GEN-LAST:event_StaffButtonActionPerformed

    private void suggestionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestionButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suggestionButtonActionPerformed

    private void noticeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noticeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noticeButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AdminHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHomeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private restaurant.management.system.UIElements.CustomButton CustomerButton;
    private restaurant.management.system.UIElements.CustomButton StaffButton;
    private restaurant.management.system.UIElements.CustomButton analysisButton;
    private javax.swing.JTabbedPane approveRequest;
    private javax.swing.JLabel customerIcon;
    private javax.swing.JPanel customerTabPanel;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
    private restaurant.management.system.UIElements.PanelRound panelRound3;
    private javax.swing.JLabel profileIcon;
    private javax.swing.JLabel profilelabel;
    private javax.swing.JScrollPane scroll;
    private restaurant.management.system.UIElements.ScrollBarCustom scrollBarCustom1;
    private javax.swing.JPanel staff;
    private javax.swing.JLabel staffIcon;
    private restaurant.management.system.UIElements.CustomButton staffInfoButton;
    private restaurant.management.system.UIElements.CustomButton suggestionButton;
    // End of variables declaration//GEN-END:variables

    public void analysisNavigation(ActionListener listener) {
        analysisButton.addActionListener(listener);
    }
    public void staffInfoNavigation(ActionListener listener) {
        staffInfoButton.addActionListener(listener);
    }
    public void suggestionNavigation(ActionListener listener) {
        suggestionButton.addActionListener(listener);
    }
    public void noticeNavigation(ActionListener listener) {
        noticeButton.addActionListener(listener);
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
    
    public JButton getStaffButton() {
        return StaffButton;
    }
    public JButton getCustomerButton() {
        return CustomerButton;
    }
    public JTabbedPane getJTabbedPane() {
        return approveRequest;
    }
    
    public void scrollToTop() {
        SwingUtilities.invokeLater(() -> {
            scroll.getVerticalScrollBar().setValue(0);
        });
    }
    
    public void displayStaffRequests(List<StaffRequestData> requests) {
        staff.removeAll();
        staff.setLayout(new BoxLayout(staff, BoxLayout.Y_AXIS));

        for (int i = 0; i < requests.size(); i++) {
            StaffRequestData request = requests.get(i);
            try {
                StaffApproveRequest cardPanel = new StaffApproveRequest(request);
                cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                cardPanel.setApproveListener(e -> {
                    if (approveListener != null) {
                        ActionEvent approveEvent = new ActionEvent(request, ActionEvent.ACTION_PERFORMED, "approve");
                        approveListener.actionPerformed(approveEvent);
                    }
                });

                cardPanel.setRejectListener(e -> {
                    if (rejectListener != null) {
                        ActionEvent rejectEvent = new ActionEvent(request, ActionEvent.ACTION_PERFORMED, "reject");
                        rejectListener.actionPerformed(rejectEvent);
                    }
                });

                staff.add(cardPanel);
                if (i < requests.size() - 1) {
                    staff.add(Box.createVerticalStrut(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        staff.add(Box.createVerticalGlue());
        staff.revalidate();
        staff.repaint();
        
        scrollToTop();
    }

    private ActionListener approveListener;
    private ActionListener rejectListener;
    public void setApproveListener(ActionListener listener) {
        this.approveListener = listener;
    }
    public void setRejectListener(ActionListener listener) {
        this.rejectListener = listener;
    }

    public void removeStaffRequestCard(int requestId) {
        for (Component comp : staff.getComponents()) {
            if (comp instanceof StaffApproveRequest) {
                StaffApproveRequest card = (StaffApproveRequest) comp;
                if (card.getRequestId() == requestId) {
                    staff.remove(card);
                    staff.revalidate();
                    staff.repaint();
                    break;
                }
            }
        }
    }
    
    public void displayCustomerOrders(List<OrderData> orders) {
        customerTabPanel.removeAll();
        customerTabPanel.setLayout(new BoxLayout(customerTabPanel, BoxLayout.Y_AXIS));
        
        if (orders.isEmpty()) {
            // Show message when no modified orders
            JLabel noOrdersLabel = new JLabel("No modified orders to display");
            noOrdersLabel.setFont(new java.awt.Font("Mongolian Baiti", java.awt.Font.ITALIC, 18));
            noOrdersLabel.setForeground(new java.awt.Color(128, 128, 128)); // Gray color
            noOrdersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            customerTabPanel.add(noOrdersLabel);
        } else {
            for (int i = 0; i < orders.size(); i++) {
                OrderData order = orders.get(i);
                try {
                    CustomerOrderPanel cardPanel = new CustomerOrderPanel(order, this);
                    cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    // Add hover effect
                    Color originalColor = new Color(239, 204, 150);
                    Color hoverColor = new Color(255, 161, 15);
                    cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent e) {
                            cardPanel.setBackground(hoverColor);
                            cardPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                        }
                        @Override
                        public void mouseExited(java.awt.event.MouseEvent e) {
                            cardPanel.setBackground(originalColor);
                            cardPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                        }
                    });
                    customerTabPanel.add(cardPanel);
                    if (i < orders.size() - 1) {
                        customerTabPanel.add(Box.createVerticalStrut(10));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        customerTabPanel.add(Box.createVerticalGlue());
        customerTabPanel.revalidate();
        customerTabPanel.repaint();
        
        scrollToTop();
    }
    
    public void refreshCustomerOrder(OrderData updatedOrder) {
        // Find and update the specific order panel
        for (Component comp : customerTabPanel.getComponents()) {
            if (comp instanceof CustomerOrderPanel) {
                CustomerOrderPanel card = (CustomerOrderPanel) comp;
                if (card.getOrder().getOrderId().equals(updatedOrder.getOrderId())) {
                    // Remove the old panel
                    customerTabPanel.remove(card);
                    
                    // Add the updated panel
                    CustomerOrderPanel updatedCard = new CustomerOrderPanel(updatedOrder, this);
                    updatedCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                    customerTabPanel.add(updatedCard, customerTabPanel.getComponentZOrder(card));
                    
                    customerTabPanel.revalidate();
                    customerTabPanel.repaint();
                    break;
                }
            }
        }
    }
    
    public void onOrderModified(OrderData modifiedOrder) {
        // Refresh the specific order in the customer tab
        refreshCustomerOrder(modifiedOrder);
    }
    
    public void refreshOrders() {
        // Reload customer orders from database
        try {
            restaurant.management.system.dao.OrderDao orderDao = new restaurant.management.system.dao.OrderDao();
            List<OrderData> orders = orderDao.getOrdersByStatus("Modified");
            displayCustomerOrders(orders);
        } catch (Exception e) {
            System.err.println("Error refreshing orders: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
