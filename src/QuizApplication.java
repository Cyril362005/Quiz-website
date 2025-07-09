// --- QuizApplication.java ---
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

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
        } catch (Exception e) {
            // ignore
        }
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
// --- END QuizApplication.java ---
