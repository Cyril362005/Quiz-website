// --- DatabaseConnection.java ---
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Provides utility methods to manage database connections for the quiz app.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quiz_app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "quizadmin"; // ✅ Use your actual MySQL user
    private static final String PASSWORD = "362005"; // ✅ Replace with your actual password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            showError("MySQL Driver not found: " + e.getMessage());
        }
    }

    /**
     * Establishes and returns a connection to the database.
     *
     * @return a Connection object or null if connection fails.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            showError("Failed to connect to database:\n" + e.getMessage());
            return null;
        }
    }

    /**
     * Closes JDBC resources safely.
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
        try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
// --- END DatabaseConnection.java ---
