/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.Color;
import java.awt.event.ActionListener;


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

        jLabel1.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel1.setText("Restaurant Management System");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 390, 40));

        asStaffButton.setBackground(new java.awt.Color(239, 167, 9));
        asStaffButton.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        asStaffButton.setText("As Staff");
        asStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asStaffButtonActionPerformed(evt);
            }
        });
        getContentPane().add(asStaffButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 120, 32));

        asOwnerButton.setBackground(new java.awt.Color(239, 167, 9));
        asOwnerButton.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        asOwnerButton.setText("As Owner");
        asOwnerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asOwnerButtonActionPerformed(evt);
            }
        });
        getContentPane().add(asOwnerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 120, 32));

        asCustomerButton.setBackground(new java.awt.Color(239, 167, 9));
        asCustomerButton.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        asCustomerButton.setText("As Customer");
        asCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asCustomerButtonActionPerformed(evt);
            }
        });
        getContentPane().add(asCustomerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 280, 120, 32));

        jLabel2.setFont(new java.awt.Font("Javanese Text", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(84, 84, 84));
        jLabel2.setText("Already have an account? ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 147, 20));

        loginlabel.setFont(new java.awt.Font("Javanese Text", 1, 13)); // NOI18N
        loginlabel.setText("Login");
        loginlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginlabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginlabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginlabelMouseExited(evt);
            }
        });
        getContentPane().add(loginlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 330, 37, 20));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/LoginBg.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -4, -1, 370));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void asStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asStaffButtonActionPerformed
        new RegisterStaffView().setVisible(true);
        dispose();
    }//GEN-LAST:event_asStaffButtonActionPerformed

    private void asOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asOwnerButtonActionPerformed
        new RegisterOwnerView().setVisible(true);
        dispose();
    }//GEN-LAST:event_asOwnerButtonActionPerformed

    private void asCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asCustomerButtonActionPerformed
        new RegisterCustomerView ().setVisible(true);
        dispose();

    }//GEN-LAST:event_asCustomerButtonActionPerformed

    private void loginlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginlabelMouseClicked
        // TODO add your handling code here:
        new LoginView().setVisible(true);
//        this.dispose();
    }//GEN-LAST:event_loginlabelMouseClicked

    private void loginlabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginlabelMouseEntered
        // TODO add your handling code here:
        loginlabel.setForeground(Color.BLUE);
    }//GEN-LAST:event_loginlabelMouseEntered

    private void loginlabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginlabelMouseExited
        // TODO add your handling code here:
        loginlabel.setForeground(Color.BLACK);
    }//GEN-LAST:event_loginlabelMouseExited

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
    
    public void addOwnerNavigation(ActionListener listener) {
        asOwnerButton.addActionListener(listener);
    }
        
    public void addStaffNavigation(ActionListener listener){
        asStaffButton.addActionListener(listener);

    }

    public void addCustomerNavigation(ActionListener listener) {
        asCustomerButton.addActionListener(listener);
    }
}
