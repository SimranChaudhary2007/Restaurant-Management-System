/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package restaurant.management.system.view;


import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import restaurant.management.system.UIElements.PasswordField;

/**
 *
 * @author labis
 */
public class LoginView extends javax.swing.JFrame {

    /**
     * Creates new form LoginView
     */
    public LoginView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminHomeView1 = new restaurant.management.system.view.AdminHomeView();
        textFieldEmail = new restaurant.management.system.UIElements.RoundedTextField();
        jLabel1 = new javax.swing.JLabel();
        forgotPasswordLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        signuplabel = new javax.swing.JLabel();
        passwordField = new restaurant.management.system.UIElements.PasswordField();
        loginButton = new restaurant.management.system.UIElements.CustomButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        bgImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textFieldEmail.setBackground(new java.awt.Color(235, 229, 229));
        textFieldEmail.setText("E-mail");
        textFieldEmail.setBorderColor(new java.awt.Color(0, 0, 0));
        textFieldEmail.setBorderWidth(1);
        textFieldEmail.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 24)); // NOI18N
        getContentPane().add(textFieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 400, 430, 40));

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 65)); // NOI18N
        jLabel1.setText("Restaurant Management System");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 270, 900, 60));

        forgotPasswordLabel.setFont(new java.awt.Font("Mongolian Baiti", 1, 25)); // NOI18N
        forgotPasswordLabel.setText("Forgot Password?");
        getContentPane().add(forgotPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 700, -1, -1));

        jLabel3.setFont(new java.awt.Font("Mongolian Baiti", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Don't have an Account?");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 780, 210, -1));

        signuplabel.setFont(new java.awt.Font("Microsoft JhengHei", 1, 20)); // NOI18N
        signuplabel.setText("SignUp");
        getContentPane().add(signuplabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 773, 80, 30));

        passwordField.setBackground(new java.awt.Color(235, 229, 229));
        passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        passwordField.setBorderColor(new java.awt.Color(0, 0, 0));
        passwordField.setBorderWidth(1);
        passwordField.setFont(new java.awt.Font("Mongolian Baiti", 0, 24)); // NOI18N
        passwordField.setShowAndHide(true);
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        getContentPane().add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 480, 430, 40));

        loginButton.setBackground(new java.awt.Color(239, 167, 9));
        loginButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        loginButton.setText("Login");
        loginButton.setBorderColor(new java.awt.Color(255, 153, 0));
        loginButton.setColor(new java.awt.Color(239, 167, 9));
        loginButton.setColorClick(new java.awt.Color(255, 204, 0));
        loginButton.setColorOver(new java.awt.Color(255, 204, 0));
        loginButton.setFocusable(false);
        loginButton.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 40)); // NOI18N
        loginButton.setRadius(30);
        getContentPane().add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 590, 200, 60));

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1015, 730, 205, 2));

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 800, 75, 2));

        bgImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/LoginBg.png"))); // NOI18N
        getContentPane().add(bgImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
            // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

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
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private restaurant.management.system.view.AdminHomeView adminHomeView1;
    private javax.swing.JLabel bgImage;
    private javax.swing.JLabel forgotPasswordLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private restaurant.management.system.UIElements.CustomButton loginButton;
    private restaurant.management.system.UIElements.PasswordField passwordField;
    private javax.swing.JLabel signuplabel;
    private restaurant.management.system.UIElements.RoundedTextField textFieldEmail;
    // End of variables declaration//GEN-END:variables


    public void signUpNavigation(MouseListener listener){
        signuplabel.addMouseListener(listener);
    }
    public JLabel getSignUplabel(){
        return signuplabel;
    }
    public void loginUser(ActionListener listener){
        loginButton.addActionListener(listener);
    }
    public javax.swing.JTextField getEmailTextField(){
        return textFieldEmail;
    }
    public PasswordField getPasswordField(){
        return passwordField;
    }
    public void forgotPassword(MouseListener listener){
        forgotPasswordLabel.addMouseListener(listener);
    }
    public JLabel getForgotPasswordlabel(){
        return forgotPasswordLabel;
    }
}
