// --- UserDashboard.java ---
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Dashboard presented after user login.
 */
public class UserDashboard extends JFrame {
    private int userId;
    private String username;
    private JButton startQuizButton = new JButton("Start Quiz");
    private JButton leaderboardButton = new JButton("Leaderboard");

    /**
     * Creates the dashboard for the given user.
     *
     * @param userId ID of the logged-in user
     * @param username name of the logged-in user
     */
    public UserDashboard(int userId, String username) {
        this.userId = userId;
        this.username = username;
        setTitle("Welcome " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(startQuizButton, gbc);
        gbc.gridy = 1;
        centerPanel.add(leaderboardButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        startQuizButton.addActionListener((ActionEvent e) -> {
            new QuizScreen(userId).setVisible(true);
            dispose();
        });

        leaderboardButton.addActionListener((ActionEvent e) -> {
            new LeaderboardScreen().setVisible(true);
        });
    }
}
// --- END UserDashboard.java ---
