package ui;

import javax.swing.plaf.LayerUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HoverScaleUI extends LayerUI<JComponent> {
    private float scale = 1f;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.addMouseListener(mouse);
        c.addMouseMotionListener(mouse);
    }

    @Override
    public void uninstallUI(JComponent c) {
        c.removeMouseListener(mouse);
        c.removeMouseMotionListener(mouse);
        super.uninstallUI(c);
    }

    private final MouseAdapter mouse = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            animate(1.05f);
        }

        public void mouseExited(MouseEvent e) {
            animate(1f);
        }
    };

    private void animate(float target) {
        new com.formdev.flatlaf.util.Animator(160, f -> {
            scale = scale + (target - scale) * f;
            JLayer<? extends JComponent> layer = (JLayer<? extends JComponent>) e.getComponent();
            layer.repaint();
        }).start();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        int w = c.getWidth(), h = c.getHeight();
        g2.translate(w / 2, h / 2);
        g2.scale(scale, scale);
        g2.translate(-w / 2, -h / 2);
        super.paint(g2, c);
        g2.dispose();
    }
}