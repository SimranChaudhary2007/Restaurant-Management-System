/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 *
 * @author ACER
 */
public class AdminProfileView extends javax.swing.JFrame {
    
    File selectedProfileFile;
    File selectedRestroFile;      

    /**
     * Creates new form AdminProfileView
     */
    public AdminProfileView() {
        
        initComponents();
        scaleImage1();
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage6();
        scaleImage7();
        scaleImage8();
        scaleImage9();
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
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/pen.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(insertProfileIcon.getWidth(), insertProfileIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        insertProfileIcon.setIcon(scaledIcon); // Centers it on screen (optional)
    }
    
    public void scaleImage7(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/pen.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(insertRestroIcon.getWidth(), insertRestroIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        insertRestroIcon.setIcon(scaledIcon); // Centers it on screen (optional)
    }
    
    public void scaleImage8(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/setting.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(accMageIcon.getWidth(), accMageIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        accMageIcon.setIcon(scaledIcon); // Centers it on screen (optional)
    }
    
    public void scaleImage9(){
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

        jPanel3 = new javax.swing.JPanel();
        panelShadow3 = new restaurant.management.system.UIElements.PanelShadow();
        jPanel5 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        resturantNameTextField = new javax.swing.JTextField();
        emailAddressTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        phoneNumberTextField = new javax.swing.JTextField();
        resturantAddressTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        panelShadow2 = new restaurant.management.system.UIElements.PanelShadow();
        AdminResturantName = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        AdminName = new javax.swing.JLabel();
        panelRound4 = new restaurant.management.system.UIElements.PanelRound();
        insertRestroIcon = new javax.swing.JLabel();
        panelRound2 = new restaurant.management.system.UIElements.PanelRound();
        insertProfileIcon = new javax.swing.JLabel();
        setprofilepicture = new javax.swing.JLabel();
        setrestaurantpicture = new javax.swing.JLabel();
        panelRound3 = new restaurant.management.system.UIElements.PanelRound();
        accMageIcon = new javax.swing.JLabel();
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
        jPanel17 = new javax.swing.JPanel();
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
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(239, 204, 150));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelShadow3.setBackground(new java.awt.Color(241, 237, 238));
        panelShadow3.setRoundBottomLeft(65);
        panelShadow3.setRoundBottomRight(65);
        panelShadow3.setRoundTopLeft(65);
        panelShadow3.setRoundTopRight(65);
        panelShadow3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(227, 143, 11));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelShadow3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 495, 410, 1));

        jPanel19.setBackground(new java.awt.Color(227, 143, 11));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelShadow3.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 407, 440, 1));

        jPanel18.setBackground(new java.awt.Color(227, 143, 11));
        jPanel18.setPreferredSize(new java.awt.Dimension(450, 1));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        panelShadow3.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 311, -1, -1));

        jPanel23.setBackground(new java.awt.Color(227, 143, 11));
        jPanel23.setPreferredSize(new java.awt.Dimension(450, 1));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelShadow3.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 211, 430, -1));

        jPanel4.setBackground(new java.awt.Color(227, 143, 11));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        panelShadow3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 112, 530, -1));

        resturantNameTextField.setBackground(new java.awt.Color(241, 237, 238));
        resturantNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        resturantNameTextField.setBorder(null);
        resturantNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resturantNameTextFieldActionPerformed(evt);
            }
        });
        panelShadow3.add(resturantNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 179, 420, 40));

        emailAddressTextField.setBackground(new java.awt.Color(241, 237, 238));
        emailAddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        emailAddressTextField.setBorder(null);
        emailAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailAddressTextFieldActionPerformed(evt);
            }
        });
        panelShadow3.add(emailAddressTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 276, 440, 49));

        jLabel5.setFont(new java.awt.Font("Mongolian Baiti", 1, 24)); // NOI18N
        jLabel5.setText("Resturant Address:");
        panelShadow3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 474, -1, -1));

        jLabel6.setFont(new java.awt.Font("Mongolian Baiti", 1, 24)); // NOI18N
        jLabel6.setText("Name:");
        panelShadow3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        nameTextField.setBackground(new java.awt.Color(241, 237, 238));
        nameTextField.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        nameTextField.setBorder(null);
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });
        panelShadow3.add(nameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 520, 40));

        phoneNumberTextField.setBackground(new java.awt.Color(241, 237, 238));
        phoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        phoneNumberTextField.setBorder(null);
        phoneNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberTextFieldActionPerformed(evt);
            }
        });
        panelShadow3.add(phoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 430, 50));

        resturantAddressTextField.setBackground(new java.awt.Color(241, 237, 238));
        resturantAddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        resturantAddressTextField.setBorder(null);
        resturantAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resturantAddressTextFieldActionPerformed(evt);
            }
        });
        panelShadow3.add(resturantAddressTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 464, 400, 40));

        jLabel4.setFont(new java.awt.Font("Mongolian Baiti", 1, 24)); // NOI18N
        jLabel4.setText("Email Address:");
        panelShadow3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 286, -1, -1));

        jLabel2.setFont(new java.awt.Font("Mongolian Baiti", 1, 24)); // NOI18N
        jLabel2.setText("Resturant Name:");
        panelShadow3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 187, -1, -1));

        jLabel3.setFont(new java.awt.Font("Mongolian Baiti", 1, 24)); // NOI18N
        jLabel3.setText("Phone Number:");
        panelShadow3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 385, -1, -1));

        updateButton.setBackground(new java.awt.Color(227, 143, 11));
        updateButton.setFont(new java.awt.Font("Mongolian Baiti", 1, 18)); // NOI18N
        updateButton.setText("Update Profile");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });
        panelShadow3.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 540, 160, 46));

        jPanel3.add(panelShadow3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 729, 610));

        panelShadow2.setBackground(new java.awt.Color(241, 237, 238));
        panelShadow2.setRoundBottomLeft(65);
        panelShadow2.setRoundBottomRight(65);
        panelShadow2.setRoundTopLeft(65);
        panelShadow2.setRoundTopRight(65);
        panelShadow2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AdminResturantName.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        AdminResturantName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AdminResturantName.setText("Resturant");
        AdminResturantName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelShadow2.add(AdminResturantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 540, 410, -1));

        jSeparator2.setBackground(new java.awt.Color(239, 204, 150));
        jSeparator2.setForeground(new java.awt.Color(239, 204, 150));
        panelShadow2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 380, 10));

        AdminName.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        AdminName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AdminName.setText("Admin");
        AdminName.setToolTipText("");
        AdminName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelShadow2.add(AdminName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 420, -1));

        panelRound4.setBackground(new java.awt.Color(255, 197, 169));
        panelRound4.setRoundBottonLeft(1000);
        panelRound4.setRoundBottonRight(1000);
        panelRound4.setRoundTopLeft(1000);
        panelRound4.setRoundTopRight(1000);
        panelRound4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        insertRestroIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/pen.png"))); // NOI18N
        panelRound4.add(insertRestroIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 6, 30, 28));

        panelShadow2.add(panelRound4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, 40, 40));

        panelRound2.setBackground(new java.awt.Color(255, 197, 169));
        panelRound2.setRoundBottonLeft(1000);
        panelRound2.setRoundBottonRight(1000);
        panelRound2.setRoundTopLeft(1000);
        panelRound2.setRoundTopRight(1000);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        insertProfileIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/pen.png"))); // NOI18N
        panelRound2.add(insertProfileIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 6, 30, 28));

        panelShadow2.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 40, 40));

        setprofilepicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelShadow2.add(setprofilepicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 130, 130));

        setrestaurantpicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelShadow2.add(setrestaurantpicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 250, 160));

        jPanel3.add(panelShadow2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 422, 610));

        panelRound3.setBackground(new java.awt.Color(255, 153, 51));
        panelRound3.setRoundBottonLeft(1000);
        panelRound3.setRoundBottonRight(1000);
        panelRound3.setRoundTopLeft(1000);
        panelRound3.setRoundTopRight(1000);
        panelRound3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accMageIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/setting.png"))); // NOI18N
        panelRound3.add(accMageIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 40, 40));

        jPanel3.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 70, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 85, 1310, 750));

        jPanel2.setBackground(new java.awt.Color(241, 237, 238));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        jLabel1.setText("Profile");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1310, 90));

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

        jPanel17.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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

        homelabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        homelabel.setText("Home");
        homelabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        homelabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(homelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 140, 250, 70));

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

        menuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/menu.png"))); // NOI18N
        jPanel1.add(menuIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 432, 35, 35));

        orderIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/check.png"))); // NOI18N
        jPanel1.add(orderIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 575, 35, 35));

        logoutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/logout.png"))); // NOI18N
        jPanel1.add(logoutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 722, 35, 35));

        logoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/Logo.png"))); // NOI18N
        jPanel1.add(logoIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 47, 40));

        jLabel8.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel8.setText("Sajilo Serve");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 12, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -5, 230, 840));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resturantAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resturantAddressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resturantAddressTextFieldActionPerformed

    private void emailAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailAddressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailAddressTextFieldActionPerformed

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void phoneNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberTextFieldActionPerformed

    private void resturantNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resturantNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resturantNameTextFieldActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AdminProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AdminProfileView().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdminName;
    private javax.swing.JLabel AdminResturantName;
    private javax.swing.JLabel accMageIcon;
    private javax.swing.JTextField emailAddressTextField;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel homelabel;
    private javax.swing.JLabel insertProfileIcon;
    private javax.swing.JLabel insertRestroIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel logoIcon;
    private javax.swing.JLabel logoutIcon;
    private javax.swing.JLabel logoutlabel;
    private javax.swing.JLabel menuIcon;
    private javax.swing.JLabel menulabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel orderIcon;
    private javax.swing.JLabel orderlabel;
    private restaurant.management.system.UIElements.PanelRound panelRound2;
    private restaurant.management.system.UIElements.PanelRound panelRound3;
    private restaurant.management.system.UIElements.PanelRound panelRound4;
    private restaurant.management.system.UIElements.PanelShadow panelShadow2;
    private restaurant.management.system.UIElements.PanelShadow panelShadow3;
    private javax.swing.JTextField phoneNumberTextField;
    private javax.swing.JLabel profileIcon;
    private javax.swing.JLabel profilelabel;
    private javax.swing.JTextField resturantAddressTextField;
    private javax.swing.JTextField resturantNameTextField;
    private javax.swing.JLabel setprofilepicture;
    private javax.swing.JLabel setrestaurantpicture;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    //button Navigation
    public void homeNavigation(MouseListener listener){
        homelabel.addMouseListener(listener);
    }
    public JLabel getProfilelabel(){
        return homelabel;
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
    public JLabel getAdminName() { //aaaaaaaaaa
    return AdminName;
}
    public JLabel getAdminResturantName() { //aaaaaaaaa
    return AdminResturantName;
}
 
    public JTextField getNameTextField() {
        return nameTextField;
    }
    public JTextField getRestaurantNameTextField() {
        return resturantNameTextField;
    }
    public JTextField getRestaurantAddressTextField() {
        return resturantAddressTextField;
    }
    public JTextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }
    public JTextField getEmailAddressTextField() {
        return emailAddressTextField;
    }
    
    public void setUpdateButtonAction(ActionListener listener) {
        updateButton.addActionListener(listener);
    }
    
    // For profile picture
    public void uploadProfileImageButton(MouseListener listener){
        insertProfileIcon.addMouseListener(listener);
    }
    public JLabel getUploadProfile(){
        return insertProfileIcon;
    }
    public void selectedProfileFile(File file){
        this.selectedProfileFile = file;
    }
    public void setDefaultProfileImage() {
        try {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/path/to/default-profile.png"));

            if (defaultIcon.getIconWidth() == -1) {
                setprofilepicture.setText("No Image");
                setprofilepicture.setIcon(null);
            } else {
                Image scaledImage = defaultIcon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
                setprofilepicture.setIcon(new ImageIcon(scaledImage));
                setprofilepicture.setText("");
            }
        } catch (Exception e) {
            setprofilepicture.setText("No Image");
            setprofilepicture.setIcon(null);
        }
    }
    public void displayProfileImage(byte[] imageData) {
        try {
            if (imageData != null && imageData.length > 0) {
                ImageIcon originalIcon = new ImageIcon(imageData);
                Image scaledImage = originalIcon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                setprofilepicture.setIcon(scaledIcon);
                setprofilepicture.setText("");
            } else {
                setDefaultProfileImage();
            }
        } catch (Exception e) {
            setDefaultProfileImage();
        }
    }
    
    // For restaurant picture
    public void uploadRestroImageButton(MouseListener listener){
        insertRestroIcon.addMouseListener(listener);
    }
    public JLabel getUploadRestaurant(){
        return insertRestroIcon;
    }
    public void selectedRestaurantFile(File file){
        this.selectedRestroFile = file;
    }
    public void setDefaultRestaurantImage() {
        try {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/path/to/default-restaurant.png"));

            if (defaultIcon.getIconWidth() == -1) {
                setrestaurantpicture.setText("No Restaurant Image");
                setrestaurantpicture.setIcon(null);
            } else {
                Image scaledImage = defaultIcon.getImage().getScaledInstance(250, 160, Image.SCALE_SMOOTH);
                setrestaurantpicture.setIcon(new ImageIcon(scaledImage));
                setrestaurantpicture.setText("");
            }
        } catch (Exception e) {
            setrestaurantpicture.setText("No Restaurant Image");
            setrestaurantpicture.setIcon(null);
        }
    }
    public void displayRestaurantImage(byte[] imageData) {
        try {
            if (imageData != null && imageData.length > 0) {
                ImageIcon originalIcon = new ImageIcon(imageData);
                Image scaledImage = originalIcon.getImage().getScaledInstance(250, 160, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                setrestaurantpicture.setIcon(scaledIcon);
                setrestaurantpicture.setText(""); 
            } else {
                setDefaultRestaurantImage();
            }
        } catch (Exception e) {
            setDefaultRestaurantImage();
        }
    }
    
  
    public void accountManagement(MouseListener listener){
        accMageIcon.addMouseListener(listener);
    }
    public JLabel getAccManagement(){
        return accMageIcon;
    }
}
