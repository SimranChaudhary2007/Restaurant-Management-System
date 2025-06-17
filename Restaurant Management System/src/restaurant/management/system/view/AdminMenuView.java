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

/**
 *
 * @author acer
 */
public class AdminMenuView extends javax.swing.JFrame {

    /**
     * Creates new form AdminMenuView
     */
    public AdminMenuView() {
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
        scaleImage10();
        scaleImage11();
        scaleImage12();
        scaleImage13();
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
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/coffee.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(coffeeIcon.getWidth(), coffeeIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        coffeeIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage7(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/cola.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(drinksIcon.getWidth(), drinksIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        drinksIcon.setIcon(scaledIcon);
    }
    public void scaleImage8(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/fast-food.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(momoIcon.getWidth(), momoIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        momoIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage9(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/pizza.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(pizzaIcon.getWidth(), pizzaIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        pizzaIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage10(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/burger.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(burgerIcon.getWidth(), burgerIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        burgerIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage11(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/ramen.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(ramenIcon.getWidth(), ramenIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        ramenIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage12(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/spaghetti.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(chowminIcon.getWidth(), chowminIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        chowminIcon.setIcon(scaledIcon);
    }
    
    public void scaleImage13(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/update.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(updateButton.getWidth(), updateButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        updateButton.setIcon(scaledIcon);
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
        jPanel3 = new javax.swing.JPanel();
        panelRound1 = new restaurant.management.system.UIElements.PanelRound();
        coffeeIcon = new javax.swing.JLabel();
        drinksIcon = new javax.swing.JLabel();
        momoIcon = new javax.swing.JLabel();
        pizzaIcon = new javax.swing.JLabel();
        burgerIcon = new javax.swing.JLabel();
        ramenIcon = new javax.swing.JLabel();
        chowminIcon = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        panelRound2 = new restaurant.management.system.UIElements.PanelRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        menuTabbedPane = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        profilelabel = new javax.swing.JLabel();
        menulabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        homeIcon = new javax.swing.JLabel();
        profileIcon = new javax.swing.JLabel();
        menuIcon = new javax.swing.JLabel();
        orderIcon = new javax.swing.JLabel();
        logoutIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(241, 237, 238));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        jLabel1.setText("Menu");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1320, 90));

        jPanel3.setBackground(new java.awt.Color(239, 204, 150));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(241, 237, 238));
        panelRound1.setPreferredSize(new java.awt.Dimension(80, 630));
        panelRound1.setRoundBottonLeft(65);
        panelRound1.setRoundBottonRight(65);
        panelRound1.setRoundTopLeft(65);
        panelRound1.setRoundTopRight(65);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        coffeeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/coffee.png"))); // NOI18N
        panelRound1.add(coffeeIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 50, 50));

        drinksIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/cola.png"))); // NOI18N
        panelRound1.add(drinksIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 95, 50, 50));

        momoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/fast-food.png"))); // NOI18N
        panelRound1.add(momoIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 175, 50, 50));

        pizzaIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/pizza.png"))); // NOI18N
        panelRound1.add(pizzaIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 255, 50, 50));

        burgerIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/burger.png"))); // NOI18N
        panelRound1.add(burgerIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 330, 50, 50));

        ramenIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/ramen.png"))); // NOI18N
        panelRound1.add(ramenIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 410, 50, 50));

        chowminIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/spaghetti.png"))); // NOI18N
        panelRound1.add(chowminIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 490, 50, 50));

        updateButton.setBackground(new java.awt.Color(239, 167, 9));
        panelRound1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 560, 50, 50));

        jPanel3.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 80, 630));

        panelRound2.setBackground(new java.awt.Color(241, 237, 238));
        panelRound2.setPreferredSize(new java.awt.Dimension(1110, 630));
        panelRound2.setRoundBottonLeft(65);
        panelRound2.setRoundBottonRight(65);
        panelRound2.setRoundTopLeft(65);
        panelRound2.setRoundTopRight(65);
        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBar(scrollBarCustom1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        menuTabbedPane.addTab("Hot Beverage", jPanel5);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        menuTabbedPane.addTab("Cold Beverage", jPanel17);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        menuTabbedPane.addTab("MoMo", jPanel18);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        menuTabbedPane.addTab("Pizza", jPanel19);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        menuTabbedPane.addTab("Burger", jPanel20);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        menuTabbedPane.addTab("Ramen", jPanel21);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        menuTabbedPane.addTab("Spaghetti", jPanel22);

        jScrollPane1.setViewportView(menuTabbedPane);

        panelRound2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1090, 560));

        jPanel3.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 1090, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 1320, 750));

        jPanel1.setBackground(new java.awt.Color(227, 143, 11));
        jPanel1.setPreferredSize(new java.awt.Dimension(225, 835));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel8.setText("Sajilo Serve");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 6, -1, -1));

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

        jLabel3.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel3.setText("Logout");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 705, 230, 75));

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

        jLabel7.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel7.setText("Orders");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 90, 1, 1));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 563, 230, 70));

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
        jPanel1.add(logoutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 720, 35, 35));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -5, 230, 840));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(AdminMenuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminMenuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminMenuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminMenuView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMenuView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel burgerIcon;
    private javax.swing.JLabel chowminIcon;
    private javax.swing.JLabel coffeeIcon;
    private javax.swing.JLabel drinksIcon;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logoutIcon;
    private javax.swing.JLabel menuIcon;
    private javax.swing.JTabbedPane menuTabbedPane;
    private javax.swing.JLabel menulabel;
    private javax.swing.JLabel momoIcon;
    private javax.swing.JLabel orderIcon;
    private restaurant.management.system.UIElements.PanelRound panelRound1;
    private restaurant.management.system.UIElements.PanelRound panelRound2;
    private javax.swing.JLabel pizzaIcon;
    private javax.swing.JLabel profileIcon;
    private javax.swing.JLabel profilelabel;
    private javax.swing.JLabel ramenIcon;
    private restaurant.management.system.UIElements.ScrollBarCustom scrollBarCustom1;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    public JButton getUpdateButton() {
        return updateButton;
    }
    
    public void hotBeveragesNavigation(MouseListener listener){
        coffeeIcon.addMouseListener(listener);
    }
    public JLabel getCoffeeIcon(){
        return coffeeIcon;
    }
    
    public void coldBeveragesNavigation(MouseListener listener){
        drinksIcon.addMouseListener(listener);
    }
    public JLabel getDrinksIcon(){
        return drinksIcon;
    }
    
    public void momoNavigation(MouseListener listener){
        momoIcon.addMouseListener(listener);
    }
    public JLabel getMomoIcon(){
        return momoIcon;
    }
    
    public void pizzaNavigation(MouseListener listener){
        pizzaIcon.addMouseListener(listener);
    }
    public JLabel getPizzaIcon(){
        return pizzaIcon;
    }
    
    public void burgerNavigation(MouseListener listener){
        burgerIcon.addMouseListener(listener);
    }
    public JLabel getBurgerIcon(){
        return burgerIcon;
    }
    
    public void ramenNavigation(MouseListener listener){
        ramenIcon.addMouseListener(listener);
    }
    public JLabel getRamenIcon(){
        return ramenIcon;
    }
    
    public void spaghettiNavigation(MouseListener listener){
        chowminIcon.addMouseListener(listener);
    }
    public JLabel getSpaghettiIcon(){
        return chowminIcon;
    }

    public JTabbedPane getMenuTabbedPane() {
        return menuTabbedPane;
    }
    
}
