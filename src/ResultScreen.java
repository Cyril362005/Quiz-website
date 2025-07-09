// --- ResultScreen.java ---
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Displays quiz results and stores them in the database.
 */
public class ResultScreen extends JFrame {
    /**
     * Creates a result screen.
     *
     * @param userId the user ID
     * @param result result details
     */
    public ResultScreen(int userId, QuizResult result) {
        setTitle("Quiz Result");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel resultPanel = new JPanel(new GridLayout(5, 1));
        resultPanel.add(new JLabel("Score: " + result.getScore()));
        resultPanel.add(new JLabel("Correct Answers: " + result.getCorrectAnswers()));
        resultPanel.add(new JLabel("Wrong Answers: " + result.getWrongAnswers()));
        resultPanel.add(new JLabel("Percentage: " + String.format("%.2f", result.getPercentage()) + "%"));
        resultPanel.add(new JLabel("Time Taken: " + result.getTimeTaken() + " sec"));

        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener((ActionEvent e) -> {
            new UserDashboard(userId, "User").setVisible(true);
            dispose();
        });

        add(resultPanel, BorderLayout.CENTER);
        add(dashboardButton, BorderLayout.SOUTH);

        saveResult(userId, result);
    }

    private void saveResult(int userId, QuizResult result) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return;
            String sql = "INSERT INTO quiz_results(user_id, score, total_questions, correct_answers, wrong_answers, percentage, time_taken) VALUES(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, result.getScore());
            ps.setInt(3, result.getTotalQuestions());
            ps.setInt(4, result.getCorrectAnswers());
            ps.setInt(5, result.getWrongAnswers());
            ps.setDouble(6, result.getPercentage());
            ps.setInt(7, result.getTimeTaken());
            ps.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to save result: " + ex.getMessage());
        } finally {
            DatabaseConnection.close(conn, ps, null);
        }
    }
}
// --- END ResultScreen.java ---
