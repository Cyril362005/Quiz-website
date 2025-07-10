package ui;

import javax.swing.*;
import java.awt.*;

public class GradientLabel extends JLabel {
    private final Color start, end;

    public GradientLabel(String txt, Color start, Color end, int size) {
        super(txt, CENTER);
        this.start = start;
        this.end = end;
        setFont(getFont().deriveFont(Font.BOLD, size));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(0, 0, start, getWidth(), 0, end));
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 4;
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
}