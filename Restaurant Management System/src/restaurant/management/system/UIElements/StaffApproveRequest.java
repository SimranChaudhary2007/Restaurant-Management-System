/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.UIElements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.Box;
import restaurant.management.system.model.StaffRequestData;

/**
 *
 * @author acer
 */
public class StaffApproveRequest extends PanelRound {
    private StaffRequestData staffRequestData;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private ActionListener approveListener;
    private ActionListener rejectListener;
    private JLabel avatarLabel;
    
    public StaffApproveRequest(StaffRequestData staffRequest) {
        this.staffRequestData = staffRequest;
        initComponents();
        setupPanel();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(239, 204, 150));
        setRoundTopLeft(20);
        setRoundTopRight(20);
        setRoundBottonLeft(20);
        setRoundBottonRight(20);
        setPreferredSize(new java.awt.Dimension(600, 75));
        setMaximumSize(new java.awt.Dimension(600, 75));
        setMinimumSize(new java.awt.Dimension(600, 75));

        // Avatar/Icon
        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new java.awt.Dimension(50, 50));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setVerticalAlignment(JLabel.CENTER);
        avatarLabel.setOpaque(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/ImagePicker/staff.png"));
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        avatarLabel.setIcon(new ImageIcon(img));

        JPanel avatarPanel = new JPanel(new GridBagLayout());
        avatarPanel.setOpaque(false);
        avatarPanel.setPreferredSize(new java.awt.Dimension(80, 75));
        avatarPanel.add(avatarLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        infoPanel.add(Box.createVerticalGlue());

        usernameLabel = new JLabel(staffRequestData.getFullName());
        usernameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        usernameLabel.setForeground(new Color(80, 50, 30));
        usernameLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        infoPanel.add(usernameLabel);

        infoPanel.add(Box.createVerticalStrut(5));

        emailLabel = new JLabel(staffRequestData.getEmail());
        emailLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(100, 70, 50));
        emailLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        infoPanel.add(emailLabel);

        infoPanel.add(Box.createVerticalGlue());

        add(avatarPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
    }
    
    private void setupPanel() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showStaffApprovalPopup();
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(255,161,15));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(239, 204, 150));
            }
        });
    }
    
    public void showStaffApprovalPopup(){
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        StaffApprovalPopupDialog popup = new StaffApprovalPopupDialog(parentFrame, staffRequestData);
        
        // Set listeners for approve/reject actions
        popup.setApproveListener(e -> {
            if (approveListener != null) {
                approveListener.actionPerformed(new ActionEvent(this, 0, "approve"));
            }
            popup.dispose();
        });
        
        popup.setRejectListener(e -> {
            if (rejectListener != null) {
                rejectListener.actionPerformed(new ActionEvent(this, 0, "reject"));
            }
            popup.dispose();
        });
        
        popup.setVisible(true);
    }
    
    public void setApproveListener(ActionListener listener) {
        this.approveListener = listener;
    }
    
    public void setRejectListener(ActionListener listener) {
        this.rejectListener = listener;
    }
    
    public StaffRequestData getStaffRequest() {
        return staffRequestData;
    }
    
    public void updateRequest(StaffRequestData updatedRequest) {
        this.staffRequestData = updatedRequest;
        usernameLabel.setText(updatedRequest.getFullName());
        emailLabel.setText(updatedRequest.getEmail());
        repaint();
    }

    private class StaffApprovalPopupDialog extends JDialog {
        private StaffRequestData staffRequest;
        private JLabel usernameLabel;
        private JLabel restaurantLabel;
        private JLabel phoneLabel;
        private JLabel emailLabel;
        private JLabel requestDateLabel;
        private JButton approveButton;
        private JButton rejectButton;
        private JButton closeButton;
        private JPanel contentPanel;
        private JPanel staffInfoPanel;
        
        private ActionListener approveListener;
        private ActionListener rejectListener;
        
        public StaffApprovalPopupDialog(JFrame parent, StaffRequestData staffRequest) {
            super(parent, "Staff Approval", true);
            this.staffRequest = staffRequest;
            initPopupComponents();
            setupPopupDialog();
        }
        
        private void initPopupComponents() {
            setLayout(null);
            contentPanel = new JPanel();
            contentPanel.setLayout(null);
            contentPanel.setBackground(new Color(239, 204, 150));
            contentPanel.setBounds(0, 0, 450, 400);
            add(contentPanel);

            JLabel titleLabel = new JLabel("Staff Approval Request");
            titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
            titleLabel.setForeground(new Color(80, 50, 30));
            titleLabel.setBounds(30, 20, 350, 28);
            contentPanel.add(titleLabel);

            staffInfoPanel = new JPanel();
            staffInfoPanel.setLayout(null);
            staffInfoPanel.setBackground(Color.WHITE);
            staffInfoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(222, 226, 230)));
            staffInfoPanel.setBounds(30, 60, 390, 220);
            contentPanel.add(staffInfoPanel);

            int yPos = 20;
            int rowHeight = 32;
            JLabel nameLabel = new JLabel("Full Name:");
            nameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
            nameLabel.setBounds(20, yPos, 90, 22);
            staffInfoPanel.add(nameLabel);
            usernameLabel = new JLabel(staffRequest.getFullName());
            usernameLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
            usernameLabel.setBounds(120, yPos, 250, 22);
            staffInfoPanel.add(usernameLabel);
            yPos += rowHeight;
            if (hasRestaurantInfo()) {
                JLabel restLabel = new JLabel("Restaurant:");
                restLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
                restLabel.setBounds(20, yPos, 90, 22);
                staffInfoPanel.add(restLabel);
                restaurantLabel = new JLabel(getRestaurantName());
                restaurantLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
                restaurantLabel.setBounds(120, yPos, 250, 22);
                staffInfoPanel.add(restaurantLabel);
                yPos += rowHeight;
            }
            JLabel emailTitleLabel = new JLabel("Email:");
            emailTitleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
            emailTitleLabel.setBounds(20, yPos, 90, 22);
            staffInfoPanel.add(emailTitleLabel);
            emailLabel = new JLabel(staffRequest.getEmail());
            emailLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
            emailLabel.setBounds(120, yPos, 250, 22);
            staffInfoPanel.add(emailLabel);
            yPos += rowHeight;
            if (hasPhoneInfo()) {
                JLabel phoneTitleLabel = new JLabel("Phone:");
                phoneTitleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
                phoneTitleLabel.setBounds(20, yPos, 90, 22);
                staffInfoPanel.add(phoneTitleLabel);
                phoneLabel = new JLabel(getPhoneNumber());
                phoneLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
                phoneLabel.setBounds(120, yPos, 250, 22);
                staffInfoPanel.add(phoneLabel);
                yPos += rowHeight;
            }
            if (hasRequestDate()) {
                JLabel dateTitleLabel = new JLabel("Requested:");
                dateTitleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
                dateTitleLabel.setBounds(20, yPos, 90, 22);
                staffInfoPanel.add(dateTitleLabel);
                requestDateLabel = new JLabel(getRequestDate());
                requestDateLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
                requestDateLabel.setBounds(120, yPos, 250, 22);
                staffInfoPanel.add(requestDateLabel);
            }
            approveButton = new JButton("Approve");
            approveButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 17));
            approveButton.setBackground(new Color(255,161,15));
            approveButton.setForeground(Color.BLACK);
            approveButton.setFocusPainted(false);
            approveButton.setBorderPainted(false);
            approveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            approveButton.setBounds(110, 320, 100, 38);
            approveButton.addActionListener(this::approveAction);
            approveButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    approveButton.setBackground(new Color(255,153,0));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    approveButton.setBackground(new Color(255,161,15));
                }
            });
            contentPanel.add(approveButton);
            rejectButton = new JButton("Reject");
            rejectButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 17));
            rejectButton.setBackground(new Color(255,161,15));
            rejectButton.setForeground(Color.BLACK);
            rejectButton.setFocusPainted(false);
            rejectButton.setBorderPainted(false);
            rejectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            rejectButton.setBounds(240, 320, 100, 38);
            rejectButton.addActionListener(this::rejectAction);
            rejectButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    rejectButton.setBackground(new Color(255,153,0));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    rejectButton.setBackground(new Color(255,161,15));
                }
            });
            contentPanel.add(rejectButton);
        }
        
        private void setupPopupDialog() {
            setSize(450, 400);
            setResizable(false);
            setLocationRelativeTo(getParent());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
        
        private void approveAction(ActionEvent e) {
            if (approveListener != null) {
                approveListener.actionPerformed(new ActionEvent(this, 0, "approve"));
            }
        }
        
        private void rejectAction(ActionEvent e) {
            if (rejectListener != null) {
                rejectListener.actionPerformed(new ActionEvent(this, 0, "reject"));
            }
        }
        
        public void setApproveListener(ActionListener listener) {
            this.approveListener = listener;
        }
        
        public void setRejectListener(ActionListener listener) {
            this.rejectListener = listener;
        }
        
        private boolean hasRestaurantInfo() {
            try {
                return staffRequest.getClass().getMethod("getRestaurantName") != null;
            } catch (Exception e) {
                return false;
            }
        }
        
        private boolean hasPhoneInfo() {
            try {
                return staffRequest.getClass().getMethod("getPhoneNumber") != null;
            } catch (Exception e) {
                return false;
            }
        }
        
        private boolean hasRequestDate() {
            try {
                return staffRequest.getClass().getMethod("getRequestDate") != null;
            } catch (Exception e) {
                return false;
            }
        }
        
        private String getRestaurantName() {
            try {
                return (String) staffRequest.getClass().getMethod("getRestaurantName").invoke(staffRequest);
            } catch (Exception e) {
                return "N/A";
            }
        }
        
        private String getPhoneNumber() {
            try {
                return (String) staffRequest.getClass().getMethod("getPhoneNumber").invoke(staffRequest);
            } catch (Exception e) {
                return "N/A";
            }
        }
        
        private String getRequestDate() {
            try {
                Object date = staffRequest.getClass().getMethod("getRequestDate").invoke(staffRequest);
                return date != null ? date.toString() : "N/A";
            } catch (Exception e) {
                return "N/A";
            }
        }
    }
}