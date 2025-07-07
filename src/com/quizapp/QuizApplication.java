// --- QuizApplication.java ---
package com.quizapp;
/**
 * Main application entry point.
 * 
 * Setup Instructions:
 * 1. Import database.sql into your MySQL server.
 * 2. Update DatabaseConnection with your MySQL credentials.
 * 3. Compile all Java files: javac -cp .:mysql-connector-java.jar -d bin $(git ls-files "*.java")
 * 4. Run the application: java -cp .:bin:mysql-connector-java.jar com.quizapp.QuizApplication
 */
import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;

public class QuizApplication {
    /**
     * Launches the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (!GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> new LoginScreen());
        } else {
            System.out.println("GUI not supported in this environment");
        }
    }
}
// --- END QuizApplication.java ---
