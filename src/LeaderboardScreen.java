// --- LeaderboardScreen.java ---
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 * Displays a leaderboard of users sorted by total score.
 */
public class LeaderboardScreen extends JFrame {
    private JTable table = new JTable();

    /**
     * Creates the leaderboard screen.
     */
    public LeaderboardScreen() {
        setTitle("Leaderboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        loadLeaderboard();
    }

    private void loadLeaderboard() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Username", "Total Score"}, 0);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return;
            ps = conn.prepareStatement("SELECT username, total_score FROM users ORDER BY total_score DESC LIMIT 10");
            rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("username"), rs.getInt("total_score")});
            }
            table.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load leaderboard: " + ex.getMessage());
        } finally {
            DatabaseConnection.close(conn, ps, rs);
        }
    }
}
// --- END LeaderboardScreen.java ---
