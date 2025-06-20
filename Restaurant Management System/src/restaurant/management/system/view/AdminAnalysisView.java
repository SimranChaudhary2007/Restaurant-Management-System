/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;



/**
 *
 * @author ACER
 */
public class AdminAnalysisView extends javax.swing.JFrame {

    /**
     * Creates new form AdminAnalysisView
     */
    public AdminAnalysisView() {
        initComponents();
        scaleImage1();
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();  
        showBarChart();
        showLineChart();
        scaleImage6();
        scaleImage7();
        scaleImage8();
        scaleImage9();
        
    }
public void showBarChart() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    // Add data
    dataset.addValue(5000, "Amount", "Momo");
    dataset.addValue(3000, "Amount", "Burger");
    dataset.addValue(4500, "Amount", "Keema Noodles");
    dataset.addValue(7000, "Amount", "Khatti Roll");
    dataset.addValue(6200, "Amount", "Pizza");
    dataset.addValue(4800, "Amount", "Sandwich");

    // Create the bar chart
    JFreeChart barChart = ChartFactory.createBarChart(
        "Top Selling Food Items",     // Chart title
        "Food Items",                 // X-axis label
        "Total Sales (Rs.)",          // Y-axis label
        dataset,                      // Dataset
        PlotOrientation.VERTICAL,     // Chart orientation
        false, true, false            // Legend, Tooltips, URLs
    );

    // Define custom background color
    Color softBackground = new Color(241, 237, 238);

    // Chart background
    barChart.setBackgroundPaint(softBackground);

    // Anti-aliasing for smooth text and edges
    barChart.setAntiAlias(true);
    barChart.setTextAntiAlias(true);
    barChart.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
    barChart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // Chart title style
    barChart.getTitle().setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
    barChart.getTitle().setPaint(new Color(0, 0, 0)); // Optional: brown for contrast

    // Customize plot
    CategoryPlot plot = barChart.getCategoryPlot();
    plot.setBackgroundPaint(softBackground); // Match chart background
    plot.setRangeGridlinePaint(new Color(220, 210, 210)); // Subtle gridlines
    plot.setOutlineVisible(false);

    // Customize axes
    CategoryAxis xAxis = plot.getDomainAxis();
    xAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
    xAxis.setTickLabelFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
    xAxis.setLabelFont(new Font("Mongolian Baiti", Font.BOLD, 16));

    plot.getRangeAxis().setTickLabelFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
    plot.getRangeAxis().setLabelFont(new Font("Mongolian Baiti", Font.BOLD, 16));

    // Bar renderer customization
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(227, 143, 12)); // Your brand orange
    renderer.setDrawBarOutline(false);
    renderer.setBarPainter(new StandardBarPainter()); // No gradient

    // Final chart panel
    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setPreferredSize(new Dimension(1200, 700));
    chartPanel.setMouseWheelEnabled(true);
    chartPanel.setOpaque(true);
    chartPanel.setBackground(softBackground);

    // Show in your GUI panel
    BarChart.removeAll();
    BarChart.setLayout(new BorderLayout());
    BarChart.add(chartPanel, BorderLayout.CENTER);
    BarChart.validate();
    BarChart.repaint();
}

public void showLineChart() {
    // Create dataset for the income analysis
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.setValue(200, "Income", "January");
    dataset.setValue(150, "Income", "February");
    dataset.setValue(180, "Income", "March");
    dataset.setValue(100, "Income", "April");
    dataset.setValue(80, "Income", "May");
    dataset.setValue(250, "Income", "June");

    // Create the line chart
    JFreeChart lineChart = ChartFactory.createLineChart(
        "Income Chart",     // Chart title
        "Months",           // X-axis label
        "Income (Rs.)",     // Y-axis label
        dataset, 
        PlotOrientation.VERTICAL, 
        false, true, false  // Legend, Tooltips, URLs
    );

    // Fonts
    Font plainFont = new Font("Mongolian Baiti", Font.PLAIN, 16);
    lineChart.getTitle().setFont(new Font("Mongolian Baiti", Font.BOLD, 20));

    // Set background color
    Color backgroundColor = new Color(241, 237, 238);
    lineChart.setBackgroundPaint(backgroundColor);  // Chart background
    CategoryPlot plot = lineChart.getCategoryPlot();
    plot.setBackgroundPaint(backgroundColor);       // Plot background

    // Set fonts
    CategoryAxis xAxis = plot.getDomainAxis();
    ValueAxis yAxis = plot.getRangeAxis();
    xAxis.setTickLabelFont(plainFont);
    xAxis.setLabelFont(plainFont);
    yAxis.setTickLabelFont(plainFont);
    yAxis.setLabelFont(plainFont);

    // Customize line appearance
    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(227, 143, 12)); // Orange tone
    renderer.setSeriesStroke(0, new BasicStroke(2.0f));

    // Setup chart panel
    ChartPanel chartPanel = new ChartPanel(lineChart);
    chartPanel.setMouseWheelEnabled(true);
    chartPanel.setPreferredSize(new java.awt.Dimension(1000, 500));

    // Display in the panel
    panelLineChart.removeAll();
    panelLineChart.setLayout(new BorderLayout());
    panelLineChart.add(chartPanel, BorderLayout.CENTER);
    panelLineChart.validate();
    panelLineChart.repaint();
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
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/people.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(peopleIcon.getWidth(), peopleIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        peopleIcon.setIcon(scaledIcon);
    }
        public void scaleImage7(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/dollar.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(dollarIcon.getWidth(), dollarIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        dollarIcon.setIcon(scaledIcon);
    }
        public void scaleImage8(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/revenue.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale = img1.getScaledInstance(revenueIcon.getWidth(), revenueIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        revenueIcon.setIcon(scaledIcon);
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        logoutlabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        profilelabel = new javax.swing.JLabel();
        menulabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        homeIcon = new javax.swing.JLabel();
        profileIcon = new javax.swing.JLabel();
        menuIcon = new javax.swing.JLabel();
        orderIcon = new javax.swing.JLabel();
        logoutIcon = new javax.swing.JLabel();
        logoIcon = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelRound1 = new restaurant.management.system.UIElements.PanelRound();
        BarChart = new javax.swing.JPanel();
        panelLineChart = new javax.swing.JPanel();
        panelShadow1 = new restaurant.management.system.UIElements.PanelShadow();
        Numbercostumer = new restaurant.management.system.UIElements.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        peopleIcon = new javax.swing.JLabel();
        Numbercostumer1 = new restaurant.management.system.UIElements.PanelRound();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dollarIcon = new javax.swing.JLabel();
        Numbercostumer2 = new restaurant.management.system.UIElements.PanelRound();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        revenueIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1545, 835));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(241, 237, 238));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 48)); // NOI18N
        jLabel1.setText("Analysis");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1320, 90));

        jPanel1.setBackground(new java.awt.Color(227, 143, 11));
        jPanel1.setPreferredSize(new java.awt.Dimension(225, 835));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Mongolian Baiti", 1, 30)); // NOI18N
        jLabel8.setText("Sajilo Serve");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 12, -1, -1));

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
        jPanel1.add(logoutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 722, 35, 35));

        logoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/Logo.png"))); // NOI18N
        jPanel1.add(logoIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 47, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -5, 230, 840));

        jPanel3.setBackground(new java.awt.Color(239, 204, 150));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(241, 237, 238));
        panelRound1.setRoundBottonLeft(65);
        panelRound1.setRoundBottonRight(65);
        panelRound1.setRoundTopLeft(65);
        panelRound1.setRoundTopRight(65);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarChart.setBackground(new java.awt.Color(102, 102, 102));
        BarChart.setLayout(new java.awt.BorderLayout());
        panelRound1.add(BarChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 550, 340));

        panelLineChart.setBackground(new java.awt.Color(102, 102, 102));
        panelLineChart.setLayout(new java.awt.BorderLayout());
        panelRound1.add(panelLineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 580, 340));

        jPanel3.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 1250, 410));

        panelShadow1.setBackground(new java.awt.Color(241, 237, 238));
        panelShadow1.setRoundBottomLeft(65);
        panelShadow1.setRoundBottomRight(65);
        panelShadow1.setRoundTopLeft(65);
        panelShadow1.setRoundTopRight(65);
        panelShadow1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Numbercostumer.setBackground(new java.awt.Color(227, 143, 12));
        Numbercostumer.setRoundBottonLeft(30);
        Numbercostumer.setRoundBottonRight(30);
        Numbercostumer.setRoundTopLeft(30);
        Numbercostumer.setRoundTopRight(30);
        Numbercostumer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Mongolian Baiti", 1, 20)); // NOI18N
        jLabel5.setText("Number Of Customers");
        Numbercostumer.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 200, -1));

        jLabel9.setFont(new java.awt.Font("Mongolian Baiti", 1, 20)); // NOI18N
        jLabel9.setText("200");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Numbercostumer.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 40, 30));

        peopleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/people.png"))); // NOI18N
        Numbercostumer.add(peopleIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 80, 70));

        panelShadow1.add(Numbercostumer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 280, 160));

        Numbercostumer1.setBackground(new java.awt.Color(227, 143, 12));
        Numbercostumer1.setRoundBottonLeft(30);
        Numbercostumer1.setRoundBottonRight(30);
        Numbercostumer1.setRoundTopLeft(30);
        Numbercostumer1.setRoundTopRight(30);
        Numbercostumer1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Mongolian Baiti", 1, 20)); // NOI18N
        jLabel6.setText("Today's Income");
        Numbercostumer1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 150, -1));

        jLabel10.setFont(new java.awt.Font("Mongolian Baiti", 1, 20)); // NOI18N
        jLabel10.setText("10000");
        jLabel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Numbercostumer1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 120, 30));

        dollarIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/dollar.png"))); // NOI18N
        Numbercostumer1.add(dollarIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, 70));

        panelShadow1.add(Numbercostumer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 280, 160));

        Numbercostumer2.setBackground(new java.awt.Color(227, 143, 12));
        Numbercostumer2.setRoundBottonLeft(30);
        Numbercostumer2.setRoundBottonRight(30);
        Numbercostumer2.setRoundTopLeft(30);
        Numbercostumer2.setRoundTopRight(30);
        Numbercostumer2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Mongolian Baiti", 1, 20)); // NOI18N
        jLabel3.setText("Total Income");
        Numbercostumer2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 120, -1));

        jLabel2.setFont(new java.awt.Font("Mongolian Baiti", 1, 20)); // NOI18N
        jLabel2.setText("1000000");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Numbercostumer2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 120, 30));

        revenueIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/revenue.png"))); // NOI18N
        Numbercostumer2.add(revenueIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 90, 80));

        panelShadow1.add(Numbercostumer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 280, 160));

        jPanel3.add(panelShadow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 1020, 220));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 85, 1310, 750));

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
            java.util.logging.Logger.getLogger(AdminAnalysisView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminAnalysisView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminAnalysisView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminAnalysisView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminAnalysisView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarChart;
    private restaurant.management.system.UIElements.PanelRound Numbercostumer;
    private restaurant.management.system.UIElements.PanelRound Numbercostumer1;
    private restaurant.management.system.UIElements.PanelRound Numbercostumer2;
    private javax.swing.JLabel dollarIcon;
    private javax.swing.JLabel homeIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JLabel orderIcon;
    private javax.swing.JPanel panelLineChart;
    private restaurant.management.system.UIElements.PanelRound panelRound1;
    private restaurant.management.system.UIElements.PanelShadow panelShadow1;
    private javax.swing.JLabel peopleIcon;
    private javax.swing.JLabel profileIcon;
    private javax.swing.JLabel profilelabel;
    private javax.swing.JLabel revenueIcon;
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
    public void logoutNavigation(MouseListener listener){
        logoutlabel.addMouseListener(listener);
    }
    public JLabel getLogoutlabel(){
        return logoutlabel;
    }
}

