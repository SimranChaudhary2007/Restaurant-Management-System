/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.view;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author acer
 */
public class PanelShadow extends JPanel {

    // Rounded corners
    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;

    // Shadow properties
    private int shadowSize = 6;
    private float shadowOpacity = 0.5f;
    private Color shadowColor = Color.BLACK;

    public PanelShadow() {
        setOpaque(false); // Important for custom painting
    }

    // Getters/Setters for rounded corners
    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int v) { roundTopLeft = v; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int v) { roundTopRight = v; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int v) { roundBottomLeft = v; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int v) { roundBottomRight = v; repaint(); }

    // Getters/Setters for shadow
    public int getShadowSize() { return shadowSize; }
    public void setShadowSize(int v) { shadowSize = v; repaint(); }

    public float getShadowOpacity() { return shadowOpacity; }
    public void setShadowOpacity(float v) { shadowOpacity = v; repaint(); }

    public Color getShadowColor() { return shadowColor; }
    public void setShadowColor(Color c) { shadowColor = c; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int shadowPad = shadowSize * 2;

        BufferedImage img = new BufferedImage(width - shadowPad, height - shadowPad, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg = img.createGraphics();
        gImg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded shape
        gImg.setColor(getBackground());
        Area area = new Area(createRoundedShape(width - shadowPad, height - shadowPad));
        gImg.fill(area);
        gImg.dispose();

        // Shadow creation
        Graphics2D g2 = (Graphics2D) g;
        ShadowRenderer shadowRenderer = new ShadowRenderer(shadowSize, shadowOpacity, shadowColor);
        BufferedImage shadow = shadowRenderer.createShadow(img);
        g2.drawImage(shadow, 0, 0, null);
        g2.drawImage(img, shadowSize, shadowSize, null);
    }

    private Shape createRoundedShape(int width, int height) {
        Area area = new Area(createRoundCorner(width, height, roundTopLeft, "TOP_LEFT"));
        if (roundTopRight > 0) area.intersect(new Area(createRoundCorner(width, height, roundTopRight, "TOP_RIGHT")));
        if (roundBottomLeft > 0) area.intersect(new Area(createRoundCorner(width, height, roundBottomLeft, "BOTTOM_LEFT")));
        if (roundBottomRight > 0) area.intersect(new Area(createRoundCorner(width, height, roundBottomRight, "BOTTOM_RIGHT")));
        return area;
    }

    private Shape createRoundCorner(int width, int height, int round, String corner) {
        int roundX = Math.min(width, round);
        int roundY = Math.min(height, round);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));

        switch (corner) {
            case "TOP_LEFT":
                area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
                area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
                break;
            case "TOP_RIGHT":
                area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
                area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
                break;
            case "BOTTOM_LEFT":
                area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
                area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
                break;
            case "BOTTOM_RIGHT":
                area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
                area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
                break;
        }

        return area;
    }
}