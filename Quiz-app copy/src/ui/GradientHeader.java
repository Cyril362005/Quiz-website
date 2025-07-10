package ui;

import javax.swing.*;
import java.awt.*;

public class GradientHeader extends JPanel {
    private final Color start = new Color(0xA855F7);  // purple-400
    private final Color end   = new Color(0x6366F1);  // indigo-400
    public GradientHeader() { setPreferredSize(new Dimension(10, 48)); }
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new GradientPaint(0,0,start,getWidth(),0,end));
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.setFont(getFont().deriveFont(Font.BOLD, 24f));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        String title = "Quiz Arena";                    // 🔹 rename globally here
        g2.drawString(title,(getWidth()-fm.stringWidth(title))/2,
                             (getHeight()+fm.getAscent())/2-4);
    }
}