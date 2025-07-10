// --- LeaderboardScreen.java ---
package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.AbstractBorder;
import db.DatabaseConnection;

/**
 * Displays a leaderboard of users sorted by total score.
 */
public class LeaderboardScreen extends JPanel {
    private final MainFrame parent;
    private JTable table = new JTable();

    /**
     * Creates the leaderboard screen.
     */
    public LeaderboardScreen(MainFrame parent, int userId, String username) {
        this.parent = parent;
        this.username = username;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10)); // Added padding for better spacing

        JLabel headerLabel = new JLabel("Leaderboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH); // Added a header label

        loadLeaderboard();

        table.setRowHeight(30); // Increased row height for better readability
        table.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for table content
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16)); // Set font for table header
        table.getTableHeader().setBackground(Color.LIGHT_GRAY); // Added background color to header
        table.getTableHeader().setForeground(Color.BLACK); // Set header text color

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for button
        backButton.setBackground(new Color(33, 150, 243));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(new RoundedBorder(12));

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(new Color(30, 136, 229));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(new Color(33, 150, 243));
            }
        });

        backButton.addActionListener(_ -> {
            parent.showPanel("Dashboard");
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadLeaderboard() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Username", "Total Score"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        // TODO: Replace with actual leaderboard loading logic
        model.addRow(new Object[]{"SampleUser", 100});
        table.setModel(model);
    }

    class RoundedBorder extends AbstractBorder {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    String username = "Default Username"; // Temporary fix for unresolved variable
}
// --- END LeaderboardScreen.java ---
