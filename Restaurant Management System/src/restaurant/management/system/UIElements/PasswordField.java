/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.UIElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author acer
 */
public class PasswordField extends JPasswordField {

    private final Image eye;
    private final Image eye_hide;
    private boolean hide = true;
    private boolean showAndHide = false;

    private int arcWidth = 20;
    private int arcHeight = 20;
    private Color borderColor = new Color(255, 153, 0);
    private int borderWidth = 2;

    public PasswordField() {
        setOpaque(false);
        setBorder(new EmptyBorder(5, 5, 5, 30));
        setEchoChar('•');

        eye = new ImageIcon(getClass().getResource("/ImagePicker/view.png"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        eye_hide = new ImageIcon(getClass().getResource("/ImagePicker/hide.png"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (showAndHide) {
                    int x = getWidth() - 30;
                    Rectangle iconArea = new Rectangle(x, 0, 30, getHeight());
                    if (iconArea.contains(me.getPoint())) {
                        hide = !hide;
                        setEchoChar(hide ? '•' : (char) 0);
                        
                        SwingUtilities.invokeLater(() -> {
                            invalidate();
                            repaint();
                            if (getParent() != null) {
                                getParent().repaint();
                            }
                        });
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me) {
                if (showAndHide) {
                    int x = getWidth() - 35;
                    Rectangle iconBounds = new Rectangle(x, 0, 35, getHeight());
                    if (iconBounds.contains(me.getPoint())) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                    }
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRoundRect(borderWidth / 2, borderWidth / 2,
                getWidth() - borderWidth, getHeight() - borderWidth, arcWidth, arcHeight);
        g2.dispose();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (showAndHide) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int iconSize = 20;
            int x = getWidth() - iconSize - 10;
            int y = (getHeight() - iconSize) / 2;
            
            g2.setColor(getBackground());
            g2.fillRect(x - 2, y - 2, iconSize + 4, iconSize + 4);
            
            Image iconToShow = hide ? eye : eye_hide;
            if (iconToShow != null) {
                g2.drawImage(iconToShow, x, y, iconSize, iconSize, this);
            }
            g2.dispose();
        }
    }

    public boolean isShowAndHide() {
        return showAndHide;
    }

    public void setShowAndHide(boolean showAndHide) {
        this.showAndHide = showAndHide;
        SwingUtilities.invokeLater(() -> {
            invalidate();
            repaint();
        });
    }

    //setter
    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
        repaint();
    }
    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
        repaint();
    }
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        repaint();
    }
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }
    
    //getter
    public int getArcWidth() {
        return arcWidth;
    }
    public int getArcHeight() {
        return arcHeight;
    }
    public int getBorderWidth() {
        return borderWidth;
    }
    public Color getBorderColor() {
        return borderColor;
    }
}
