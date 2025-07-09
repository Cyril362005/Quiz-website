// --- QuizScreen.java ---
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Screen that presents quiz questions.
 */
public class QuizScreen extends JFrame {
    private int userId;
    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private int timeTaken = 0;
    private Timer timer;
    private JLabel questionLabel = new JLabel();
    private JRadioButton[] optionButtons = new JRadioButton[4];
    private ButtonGroup optionGroup = new ButtonGroup();
    private JButton nextButton = new JButton("Next");
    private JButton lifelineButton = new JButton("50-50");
    private JProgressBar progressBar = new JProgressBar();
    private JLabel timerLabel = new JLabel("15");

    /**
     * Creates a quiz screen for the specified user.
     *
     * @param userId the user taking the quiz
     */
    public QuizScreen(int userId) {
        this.userId = userId;
        setTitle("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(timerLabel, BorderLayout.EAST);
        topPanel.add(progressBar, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        questionLabel.setPreferredSize(new Dimension(500, 60));
        centerPanel.add(questionLabel, gbc);
        gbc.gridwidth = 1;

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            gbc.gridx = i % 2;
            gbc.gridy = 1 + i / 2;
            centerPanel.add(optionButtons[i], gbc);
        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(lifelineButton);
        bottomPanel.add(nextButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        nextButton.addActionListener((ActionEvent e) -> nextQuestion());
        lifelineButton.addActionListener((ActionEvent e) -> applyLifeline());

        loadQuestions();
        showQuestion();
    }

    private void loadQuestions() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) return;
            ps = conn.prepareStatement("SELECT * FROM questions ORDER BY RAND() LIMIT 5");
            rs = ps.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("question_id"));
                q.setCategoryId(rs.getInt("category_id"));
                q.setQuestionText(rs.getString("question_text"));
                String[] opts = new String[4];
                opts[0] = rs.getString("option1");
                opts[1] = rs.getString("option2");
                opts[2] = rs.getString("option3");
                opts[3] = rs.getString("option4");
                q.setOptions(opts);
                q.setCorrectAnswer(rs.getString("correct_answer"));
                questions.add(q);
            }
            progressBar.setMaximum(questions.size());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load questions: " + ex.getMessage());
        } finally {
            DatabaseConnection.close(conn, ps, rs);
        }
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            finishQuiz();
            return;
        }
        Question q = questions.get(currentIndex);
        questionLabel.setText("Q" + (currentIndex+1) + ": " + q.getQuestionText());
        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(opts[i]);
            optionButtons[i].setVisible(true);
        }
        optionGroup.clearSelection();
        lifelineButton.setEnabled(true);
        progressBar.setValue(currentIndex);
        timerLabel.setText("15");
        if (timer != null) timer.stop();
        timer = new Timer(1000, (ActionEvent e) -> {
            int val = Integer.parseInt(timerLabel.getText());
            val--; timeTaken++;
            timerLabel.setText(Integer.toString(val));
            if (val <= 0) {
                timer.stop();
                nextQuestion();
            }
        });
        timer.start();
    }

    private void applyLifeline() {
        Question q = questions.get(currentIndex);
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (!optionButtons[i].getText().equals(q.getCorrectAnswer())) {
                indices.add(i);
            }
        }
        Collections.shuffle(indices);
        optionButtons[indices.get(0)].setVisible(false);
        optionButtons[indices.get(1)].setVisible(false);
        lifelineButton.setEnabled(false);
    }

    private void nextQuestion() {
        timer.stop();
        Question q = questions.get(currentIndex);
        String selected = null;
        for (JRadioButton rb : optionButtons) {
            if (rb.isSelected()) {
                selected = rb.getText();
            }
        }
        if (selected != null && selected.equals(q.getCorrectAnswer())) {
            score++;
        }
        currentIndex++;
        showQuestion();
    }

    private void finishQuiz() {
        progressBar.setValue(questions.size());
        QuizResult result = new QuizResult(score, questions.size(), score, questions.size()-score,
                ((double)score/questions.size())*100, timeTaken);
        new ResultScreen(userId, result).setVisible(true);
        dispose();
    }
}
// --- END QuizScreen.java ---
