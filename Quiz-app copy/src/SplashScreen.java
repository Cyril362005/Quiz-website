// --- SplashScreen.java ---
import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private float opacity = 0f;
    private Timer fadeInTimer;
    private Timer fadeOutTimer;

    public SplashScreen() {
        JPanel content = new JPanel();
        Color bgColor = new Color(250, 250, 250);
        Color primaryColor = new Color(33, 150, 243); // blue
        content.setBackground(bgColor);
        content.setLayout(new BorderLayout());

        JLabel title = new JLabel("🚀 Quiz Application", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(primaryColor);

        JLabel loading = new JLabel("Loading...", SwingConstants.CENTER);
        loading.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loading.setForeground(primaryColor);

        content.add(title, BorderLayout.CENTER);
        content.add(loading, BorderLayout.SOUTH);

        setContentPane(content);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Fade In Timer (increments opacity)
        fadeInTimer = new Timer(30, _ -> {
            opacity += 0.05f;
            if (opacity >= 1f) {
                opacity = 1f;
                fadeInTimer.stop();
                new Timer(1200, _ -> fadeOut()).start(); // wait before fade out
            }
            setWindowOpacity(opacity);
        });

        // Fade Out Timer (decrements opacity)
        fadeOutTimer = new Timer(30, _ -> {
            opacity -= 0.05f;
            if (opacity <= 0f) {
                opacity = 0f;
                fadeOutTimer.stop();
                setVisible(false);
                new MainFrame().showPanel("Login"); // Proper navigation
            }
            setWindowOpacity(opacity);
        });
    }

    public void showSplash(int milliseconds) {
        setVisible(true);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
    }

    public void showSplashWithAnimation() {
        setOpacity(0f);
        setVisible(true);
        fadeInTimer.start();
    }

    private void fadeOut() {
        fadeOutTimer.start();
    }

    private void setWindowOpacity(float value) {
        try {
            setOpacity(value); // Works on Java 1.7+ with GUI enabled
        } catch (UnsupportedOperationException ignored) {}
    }
}
// --- END SplashScreen.java ---
