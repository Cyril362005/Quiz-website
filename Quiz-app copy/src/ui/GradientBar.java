package ui;

import javax.swing.*;
import java.awt.*;

public class GradientBar extends JPanel {
    private final Color start  = new Color(0x00E5FF);
    private final Color finish = new Color(0x0033FF);

    public GradientBar() {
        setOpaque(false);
        setPreferredSize(new Dimension(10, 8));               // height = 8 px
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
    }
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY);
        g2.setPaint(new GradientPaint(0, 0, start, getWidth(), 0, finish));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}