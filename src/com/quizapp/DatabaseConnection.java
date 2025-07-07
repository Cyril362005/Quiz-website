// --- DatabaseConnection.java ---
package com.quizapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.quizapp.Constants;

/**
 * MySQL JDBC operations.
 */
public class DatabaseConnection {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a database connection.
     * @return connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
    }
}
// --- END DatabaseConnection.java ---
