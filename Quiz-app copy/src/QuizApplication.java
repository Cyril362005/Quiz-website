// --- QuizApplication.java ---
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

/**
 * Main application entry point.
 */
public class QuizApplication {
    /**
     * Application main method.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
// --- END QuizApplication.java ---
