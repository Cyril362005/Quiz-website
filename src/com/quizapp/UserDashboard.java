// --- UserDashboard.java ---
package com.quizapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Quiz category selection interface.
 */
public class UserDashboard extends JFrame implements ActionListener {
    private User user;
    private List<JRadioButton> categoryButtons = new ArrayList<>();
    private JButton startButton;
    private JButton leaderboardButton;
    private JLabel statsLabel;
    private static final Dimension BTN_SIZE = new Dimension(150, 30);

    /**
     * Constructs the dashboard for the given user.
     * @param user logged in user
     */
    public UserDashboard(User user) {
        this.user = user;
        setTitle("Dashboard - " + user.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);

        JPanel top = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("Select Category");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(titleLabel);
        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        top.add(statsLabel);
        add(top, BorderLayout.NORTH);

        JPanel categoryPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup group = new ButtonGroup();
        loadCategories(categoryPanel, group);

        startButton = new JButton("Start Quiz");
        startButton.setPreferredSize(BTN_SIZE);
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        startButton.addActionListener(this);

        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setPreferredSize(BTN_SIZE);
        leaderboardButton.setFont(new Font("Arial", Font.PLAIN, 14));
        leaderboardButton.addActionListener(this);

        JPanel bottom = new JPanel();
        bottom.add(startButton);
        bottom.add(leaderboardButton);

        add(categoryPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        loadStats();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadCategories(JPanel panel, ButtonGroup group) {
        String query = "SELECT category_id, category_name FROM categories";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("category_name");
                JRadioButton rb = new JRadioButton(name);
                rb.setPreferredSize(BTN_SIZE);
                rb.setFont(new Font("Arial", Font.PLAIN, 14));
                rb.setActionCommand(String.valueOf(id));
                group.add(rb);
                panel.add(rb);
                categoryButtons.add(rb);
            }
            if (!categoryButtons.isEmpty()) {
                categoryButtons.get(0).setSelected(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void loadStats() {
        String sql = "SELECT total_score, quizzes_taken FROM users WHERE user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int score = rs.getInt("total_score");
                int taken = rs.getInt("quizzes_taken");
                statsLabel.setText("Total Score: " + score + " | Quizzes Taken: " + taken);
            }
        } catch (SQLException ex) {
            statsLabel.setText("Stats unavailable");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            String selected = null;
            for (JRadioButton rb : categoryButtons) {
                if (rb.isSelected()) {
                    selected = rb.getActionCommand();
                    break;
                }
            }
            if (selected != null) {
                int categoryId = Integer.parseInt(selected);
                dispose();
                new QuizScreen(user, categoryId);
            }
        } else if (e.getSource() == leaderboardButton) {
            new LeaderboardScreen();
        }
    }
}
// --- END UserDashboard.java ---
