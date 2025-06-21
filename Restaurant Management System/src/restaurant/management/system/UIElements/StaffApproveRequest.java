package restaurant.management.system.UIElements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import restaurant.management.system.model.StaffRequestData;

public class StaffApproveRequest extends PanelRound {
    private final StaffRequestData staffRequestData;
    private final int requestId;
    private ActionListener approveListener;
    private ActionListener rejectListener;

    public StaffApproveRequest(StaffRequestData staffRequest) {
        this.staffRequestData = staffRequest;
        this.requestId = staffRequest.getRequestId();
        initComponents();
        setupPanel();
    }

    public int getRequestId() {
        return this.requestId;
    }

    public void setApproveListener(ActionListener listener) {
        this.approveListener = listener;
    }

    public void setRejectListener(ActionListener listener) {
        this.rejectListener = listener;
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

        JLabel avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new java.awt.Dimension(50, 50));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setVerticalAlignment(JLabel.CENTER);
        ImageIcon icon = new ImageIcon(getClass().getResource("/ImagePicker/staff.png"));
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        avatarLabel.setIcon(new ImageIcon(img));

        JPanel avatarPanel = new JPanel(new java.awt.GridBagLayout());
        avatarPanel.setOpaque(false);
        avatarPanel.setPreferredSize(new java.awt.Dimension(80, 75));
        avatarPanel.add(avatarLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.add(Box.createVerticalGlue());

        JLabel usernameLabel = new JLabel(staffRequestData.getFullName());
        usernameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 22));
        usernameLabel.setForeground(new Color(80, 50, 30));
        infoPanel.add(usernameLabel);

        infoPanel.add(Box.createVerticalStrut(5));

        JLabel emailLabel = new JLabel(staffRequestData.getEmail());
        emailLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(100, 70, 50));
        infoPanel.add(emailLabel);

        infoPanel.add(Box.createVerticalGlue());

        add(avatarPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
    }

    private void setupPanel() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(StaffApproveRequest.this);
                StaffApprovalPopupDialog popup = new StaffApprovalPopupDialog(parentFrame, staffRequestData);
                popup.setVisible(true);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(255, 161, 15));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(239, 204, 150));
            }
        });
    }

    private class StaffApprovalPopupDialog extends JDialog {

        public StaffApprovalPopupDialog(JFrame parent, StaffRequestData request) {
            super(parent, "Staff Approval Request", true);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(null);
            contentPanel.setBackground(new Color(239, 204, 150));
            contentPanel.setBounds(0, 0, 450, 400);
            add(contentPanel);

            JLabel titleLabel = new JLabel("Staff Approval Request");
            titleLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 20));
            titleLabel.setForeground(new Color(80, 50, 30));
            titleLabel.setBounds(30, 20, 350, 28);
            contentPanel.add(titleLabel);

            JPanel staffInfoPanel = new JPanel();
            staffInfoPanel.setLayout(null);
            staffInfoPanel.setBackground(Color.WHITE);
            staffInfoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(222, 226, 230)));
            staffInfoPanel.setBounds(30, 60, 390, 220);
            contentPanel.add(staffInfoPanel);

            int yPos = 20;
            int rowHeight = 32;

            staffInfoPanel.add(createLabel("Full Name:", 20, yPos, true));
            staffInfoPanel.add(createLabel(request.getFullName(), 120, yPos, false));
            yPos += rowHeight;

            staffInfoPanel.add(createLabel("Restaurant:", 20, yPos, true));
            staffInfoPanel.add(createLabel(request.getRestaurantName(), 120, yPos, false));
            yPos += rowHeight;

            staffInfoPanel.add(createLabel("Email:", 20, yPos, true));
            staffInfoPanel.add(createLabel(request.getEmail(), 120, yPos, false));
            yPos += rowHeight;

            staffInfoPanel.add(createLabel("Phone:", 20, yPos, true));
            staffInfoPanel.add(createLabel(request.getPhoneNumber(), 120, yPos, false));
            yPos += rowHeight;

            if (request.getRequestDate() != null) {
                staffInfoPanel.add(createLabel("Requested:", 20, yPos, true));
                staffInfoPanel.add(createLabel(new java.text.SimpleDateFormat("yyyy-MM-dd").format(request.getRequestDate()), 120, yPos, false));
            }

            JButton approveButton = new JButton("Approve");
            approveButton.setBackground(new Color(255, 161, 15));
            approveButton.setForeground(Color.WHITE);
            approveButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
            approveButton.setBounds(110, 320, 100, 38);
            approveButton.addActionListener(e -> {
                if (approveListener != null) {
                    ActionEvent newEvent = new ActionEvent(request, ActionEvent.ACTION_PERFORMED, "approve");
                    approveListener.actionPerformed(newEvent);
                }
                dispose();
            });
            contentPanel.add(approveButton);

            JButton rejectButton = new JButton("Reject");
            rejectButton.setBackground(new Color(255, 161, 15));
            rejectButton.setForeground(Color.WHITE);
            rejectButton.setFont(new Font("Mongolian Baiti", Font.BOLD, 14));
            rejectButton.setBounds(240, 320, 100, 38);
            rejectButton.addActionListener(e -> {
                if (rejectListener != null) {
                    ActionEvent newEvent = new ActionEvent(request, ActionEvent.ACTION_PERFORMED, "reject");
                    rejectListener.actionPerformed(newEvent);
                }
                dispose();
            });
            contentPanel.add(rejectButton);

            setSize(450, 400);
            setLocationRelativeTo(parent);
            setModal(true);
            setResizable(false);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        private JLabel createLabel(String text, int x, int y, boolean isBold) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Mongolian Baiti", isBold ? Font.BOLD : Font.PLAIN, 14));
            label.setBounds(x, y, 250, 22);
            return label;
        }
    }
}