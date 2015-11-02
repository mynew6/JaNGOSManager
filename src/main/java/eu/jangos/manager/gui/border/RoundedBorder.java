package eu.jangos.manager.gui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

/**
 * Rounded Border is a visual component with rounded border.
 *
 * @author Warkdev
 * @version v0.1.
 */
public class RoundedBorder extends AbstractBorder {

    /**
     * Color of shadow
     */
    private Color shadowColor = Color.black;
    /**
     * Double values for Horizontal and Vertical radius of corner arcs
     */
    private Dimension arcs = new Dimension(20, 20);
    /**
     * Distance between shadow border and opaque panel border
     */
    private int shadowGap = 5;

    public RoundedBorder(Color c, int g) {
        this.shadowColor = c;
        this.shadowGap = g;
    }    
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d;
        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
            g2d.setColor(shadowColor);
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, this.shadowGap, this.shadowGap));
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return (getBorderInsets(c, new Insets(shadowGap, shadowGap, shadowGap, shadowGap)));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = shadowGap;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
