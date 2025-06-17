/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JLabel;


public class RegisterAsView extends javax.swing.JFrame {

    
    public RegisterAsView() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        loginlabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        asCustomerButton = new restaurant.management.system.UIElements.CustomButton();
        asOwnerButton = new restaurant.management.system.UIElements.CustomButton();
        asStaffButton = new restaurant.management.system.UIElements.CustomButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 65)); // NOI18N
        jLabel1.setText("Restaurant Management System");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 260, 910, 80));

        jLabel2.setFont(new java.awt.Font("Mongolian Baiti", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(84, 84, 84));
        jLabel2.setText("Already have an account? ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 770, 290, 30));

        loginlabel.setFont(new java.awt.Font("Microsoft JhengHei", 1, 30)); // NOI18N
        loginlabel.setText("Login");
        getContentPane().add(loginlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 760, 90, 41));

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1218, 800, 87, 2));

        asCustomerButton.setBackground(new java.awt.Color(239, 167, 9));
        asCustomerButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        asCustomerButton.setText("As Customer");
        asCustomerButton.setBorderColor(new java.awt.Color(255, 153, 0));
        asCustomerButton.setColor(new java.awt.Color(239, 167, 9));
        asCustomerButton.setColorClick(new java.awt.Color(255, 204, 0));
        asCustomerButton.setColorOver(new java.awt.Color(255, 204, 0));
        asCustomerButton.setFocusable(false);
        asCustomerButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 45)); // NOI18N
        asCustomerButton.setRadius(30);
        getContentPane().add(asCustomerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 640, 310, 60));

        asOwnerButton.setBackground(new java.awt.Color(239, 167, 9));
        asOwnerButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        asOwnerButton.setText("As Owner");
        asOwnerButton.setBorderColor(new java.awt.Color(255, 153, 0));
        asOwnerButton.setColor(new java.awt.Color(239, 167, 9));
        asOwnerButton.setColorClick(new java.awt.Color(255, 204, 0));
        asOwnerButton.setColorOver(new java.awt.Color(255, 204, 0));
        asOwnerButton.setFocusable(false);
        asOwnerButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 45)); // NOI18N
        asOwnerButton.setRadius(30);
        getContentPane().add(asOwnerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 410, 310, 60));

        asStaffButton.setBackground(new java.awt.Color(239, 167, 9));
        asStaffButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        asStaffButton.setText("As Staff");
        asStaffButton.setBorderColor(new java.awt.Color(255, 153, 0));
        asStaffButton.setColor(new java.awt.Color(239, 167, 9));
        asStaffButton.setColorClick(new java.awt.Color(255, 204, 0));
        asStaffButton.setColorOver(new java.awt.Color(255, 204, 0));
        asStaffButton.setFocusable(false);
        asStaffButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 45)); // NOI18N
        asStaffButton.setRadius(30);
        asStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asStaffButtonActionPerformed(evt);
            }
        });
        getContentPane().add(asStaffButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 520, 310, 60));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/LoginBg.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1535, 835));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void asStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asStaffButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_asStaffButtonActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterAsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterAsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterAsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterAsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterAsView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private restaurant.management.system.UIElements.CustomButton asCustomerButton;
    private restaurant.management.system.UIElements.CustomButton asOwnerButton;
    private restaurant.management.system.UIElements.CustomButton asStaffButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel loginlabel;
    // End of variables declaration//GEN-END:variables
    
    public void ownerNavigation(ActionListener listener) {
        asOwnerButton.addActionListener(listener);
    }
        
    public void staffNavigation(ActionListener listener){
        asStaffButton.addActionListener(listener);
    }

    public void customerNavigation(ActionListener listener) {
        asCustomerButton.addActionListener(listener);
    }
    
    public void loginNavigation(MouseListener listener){
        loginlabel.addMouseListener(listener);
    }
    public JLabel getLoginLabel(){
        return loginlabel;
    }
}
