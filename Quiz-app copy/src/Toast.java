import javax.swing.*;
import java.awt.*;

/**
 * Custom Toast notification that fades away after a few seconds.
 */
public class Toast extends JWindow {
    private static final int FADE_OUT_TIME = 3000; // Time in milliseconds

    public Toast(String message, int duration) {
        JLabel label = new JLabel(message, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 200));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(label);
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        new Thread(() -> {
            try {
                Thread.sleep(duration);
                SwingUtilities.invokeLater(this::dispose);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    public static void showToast(String message, int duration) {
        SwingUtilities.invokeLater(() -> {
            Toast toast = new Toast(message, duration);
            toast.setVisible(true);
        });
    }

    public static void main(String[] args) {
        showToast("This is a toast message!", FADE_OUT_TIME);
    }
}