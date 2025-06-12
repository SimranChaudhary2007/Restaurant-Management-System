/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.UIElements;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author acer
 */
public class RoundedTextField extends JTextField {
    private int arcWidth = 20;
    private int arcHeight = 20;
    private Color borderColor = new Color(255, 153, 0);
    private int borderWidth = 2;

    public RoundedTextField() {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    public RoundedTextField(String text) {
        super(text);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    public RoundedTextField(String text, int columns) {
        super(text, columns);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint border
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRoundRect(borderWidth/2, borderWidth/2, 
                        getWidth() - borderWidth, getHeight() - borderWidth, 
                        arcWidth, arcHeight);

        g2.dispose();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        repaint();
    }

    //setters
    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
        repaint();
    }
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        repaint();
    }
    public void setRoundness(int arcWidth, int arcHeight) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        repaint();
    }
    
    //getter
    public int getArcWidth() {
        return arcWidth;
    }
    public int getArcHeight() {
        return arcHeight;
    }
    public Color getBorderColor() {
        return borderColor;
    }
    public int getBorderWidth() {
        return borderWidth;
    }
    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
        repaint();
    }
}
