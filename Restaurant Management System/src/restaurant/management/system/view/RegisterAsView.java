/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.Color;
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
        asStaffButton = new javax.swing.JButton();
        asOwnerButton = new javax.swing.JButton();
        asCustomerButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        loginlabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 65)); // NOI18N
        jLabel1.setText("Restaurant Management System");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 270, 900, 60));

        asStaffButton.setBackground(new java.awt.Color(239, 167, 9));
        asStaffButton.setFont(new java.awt.Font("Microsoft JhengHei", 1, 45)); // NOI18N
        asStaffButton.setText("As Staff");
        getContentPane().add(asStaffButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 520, 310, 60));

        asOwnerButton.setBackground(new java.awt.Color(239, 167, 9));
        asOwnerButton.setFont(new java.awt.Font("Microsoft JhengHei", 1, 45)); // NOI18N
        asOwnerButton.setText("As Owner");
        getContentPane().add(asOwnerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 410, 310, 60));

        asCustomerButton.setBackground(new java.awt.Color(239, 167, 9));
        asCustomerButton.setFont(new java.awt.Font("Microsoft JhengHei", 1, 45)); // NOI18N
        asCustomerButton.setText("As Customer");
        getContentPane().add(asCustomerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 640, 310, 60));

        jLabel2.setFont(new java.awt.Font("Mongolian Baiti", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(84, 84, 84));
        jLabel2.setText("Already have an account? ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 770, 290, 30));

        loginlabel.setFont(new java.awt.Font("Microsoft JhengHei", 1, 30)); // NOI18N
        loginlabel.setText("Login");
        getContentPane().add(loginlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 760, 90, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/LoginBg.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -4, -1, 840));

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
    private javax.swing.JButton asCustomerButton;
    private javax.swing.JButton asOwnerButton;
    private javax.swing.JButton asStaffButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
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
