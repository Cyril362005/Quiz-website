// --- LeaderboardScreen.java ---
package com.quizapp;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User rankings and scores.
 */
public class LeaderboardScreen extends JFrame {
    /**
     * Constructs the leaderboard screen.
     */
    public LeaderboardScreen() {
        setTitle("Leaderboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);

        String[] columns = {"Rank", "Username", "Score"};
        Object[][] data = loadData();
        JTable table = new JTable(data, columns);
        table.setPreferredScrollableViewportSize(new Dimension(300, 200));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        add(new JScrollPane(table), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Object[][] loadData() {
        String query = "SELECT username, total_score FROM users ORDER BY total_score DESC LIMIT 10";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            Object[][] rows = new Object[10][3];
            int index = 0;
            while (rs.next() && index < 10) {
                rows[index][0] = index + 1;
                rows[index][1] = rs.getString("username");
                rows[index][2] = rs.getInt("total_score");
                index++;
            }
            if (index < 10) {
                Object[][] trimmed = new Object[index][3];
                System.arraycopy(rows, 0, trimmed, 0, index);
                return trimmed;
            }
            return rows;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            return new Object[0][0];
        }
    }
}
// --- END LeaderboardScreen.java ---
