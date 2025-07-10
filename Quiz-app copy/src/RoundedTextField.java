import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedTextField extends JTextField {
    private final String placeholder;
    public RoundedTextField(String placeholder) {
        this.placeholder = placeholder;
        setBorder(new LineBorder(new Color(0x3378FF), 2, true));
        setFont(getFont().deriveFont(Font.PLAIN, 16f));
        setOpaque(false);
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { repaint(); }
            public void focusLost  (FocusEvent e) { repaint(); }
        });
    }
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !isFocusOwner()) {
            g.setColor(new Color(0x3378FF));
            Insets ins = getInsets();
            g.drawString(placeholder, ins.left + 8, getHeight() / 2 + 5);
        }
    }
}