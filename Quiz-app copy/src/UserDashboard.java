// --- UserDashboard.java ---
import javax.swing.*;
import java.awt.*;

/**
 * Dashboard presented after user login.
 */
public class UserDashboard extends JPanel {
    private final MainFrame parent;
    private int userId;
    private String username;

    /**
     * Creates the dashboard for the given user.
     *
     * @param userId the ID of the user
     * @param username the name of the user
     * @param parent the main frame of the application
     */
    public UserDashboard(MainFrame parent, int userId, String username) {
        this.parent = parent;
        this.userId = userId;
        this.username = username;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Welcome panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel with main actions
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Quiz button
        JButton startQuizButton = new JButton("Start Quiz");
        startQuizButton.setFont(new Font("Arial", Font.BOLD, 14));
        startQuizButton.addActionListener(_ -> startQuiz());
        centerPanel.add(startQuizButton);

        // Leaderboard button
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(_ -> openLeaderboard());
        centerPanel.add(leaderboardButton);

        add(centerPanel, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(_ -> parent.showPanel("Login"));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Start quiz
    private void startQuiz() {
        parent.addAndShow("Quiz" + userId, new QuizScreen(parent, userId)); // 2-arg ctor
    }

    // Open leaderboard
    private void openLeaderboard() {
        parent.showPanel(MainFrame.LEADERBOARD);
    }
}
// --- END UserDashboard.java ---
