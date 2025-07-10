// --- QuizApplication.java ---
/**
 * Setup Instructions:
 * 1. Ensure Java 8+ is installed.
 * 2. Compile all source files: `javac src/*.java src/ui/*.java src/model/*.java` from project root.
 * 3. Run the application: `java -cp src QuizApplication`.
 * 4. Import schema.sql into MySQL and update Constants.java with your DB details.
 */

import javax.swing.SwingUtilities;
import ui.LoginScreen;

public class QuizApplication {
    /** Launches the login screen. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
// --- END QuizApplication.java ---
