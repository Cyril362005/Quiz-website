// --- QuizScreen.java ---
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
import java.util.Collections;
import java.util.List;

/**
 * Quiz interface with timer and lifelines.
 */
public class QuizScreen extends JFrame implements ActionListener {
    private User user;
    private int categoryId;
    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private int correct = 0;
    private int wrong = 0;
    private long startTime;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons = new JRadioButton[4];
    private JButton nextButton;
    private JButton lifelineButton;
    private JProgressBar timerBar;
    private Timer timer;
    private int timeLeft = 15;
    private static final Dimension BTN_SIZE = new Dimension(150, 30);

    /**
     * Constructs the quiz screen.
     * @param user logged in user
     * @param categoryId selected category
     */
    public QuizScreen(User user, int categoryId) {
        this.user = user;
        this.categoryId = categoryId;
        setTitle("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);

        questionLabel = new JLabel("Question");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setPreferredSize(BTN_SIZE);
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            group.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        nextButton = new JButton("Next");
        nextButton.setPreferredSize(BTN_SIZE);
        nextButton.setFont(new Font("Arial", Font.PLAIN, 14));
        nextButton.addActionListener(this);
        lifelineButton = new JButton("50-50");
        lifelineButton.setPreferredSize(BTN_SIZE);
        lifelineButton.setFont(new Font("Arial", Font.PLAIN, 14));
        lifelineButton.addActionListener(this);
        timerBar = new JProgressBar(0, 15);
        timerBar.setValue(15);
        timerBar.setStringPainted(true);
        bottom.add(lifelineButton);
        bottom.add(nextButton);
        bottom.add(timerBar);
        add(bottom, BorderLayout.SOUTH);

        loadQuestions();
        showQuestion();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadQuestions() {
        String query = "SELECT * FROM questions WHERE category_id=? ORDER BY RAND()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Question q = new Question(
                    rs.getInt("question_id"),
                    rs.getInt("category_id"),
                    rs.getString("question_text"),
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4"),
                    rs.getString("correct_answer"),
                    rs.getString("difficulty_level")
                );
                questions.add(q);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            finishQuiz();
            return;
        }
        Question q = questions.get(currentIndex);
        questionLabel.setText(q.getQuestionText());
        optionButtons[0].setText(q.getOption1());
        optionButtons[1].setText(q.getOption2());
        optionButtons[2].setText(q.getOption3());
        optionButtons[3].setText(q.getOption4());
        for (JRadioButton rb : optionButtons) {
            rb.setEnabled(true);
            rb.setSelected(false);
        }
        lifelineButton.setEnabled(true);
        timeLeft = 15;
        timerBar.setValue(timeLeft);
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerBar.setValue(timeLeft);
            if (timeLeft <= 0) {
                timer.stop();
                checkAnswer();
            }
        });
        timer.start();
        if (currentIndex == 0) {
            startTime = System.currentTimeMillis();
        }
    }

    private void checkAnswer() {
        timer.stop();
        Question q = questions.get(currentIndex);
        String selected = null;
        for (JRadioButton rb : optionButtons) {
            if (rb.isSelected()) {
                selected = rb.getText();
                break;
            }
        }
        if (q.getCorrectAnswer().equals(selected)) {
            score += 10;
            correct++;
        } else {
            wrong++;
        }
        currentIndex++;
        showQuestion();
    }

    private void finishQuiz() {
        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        double percentage = questions.isEmpty() ? 0 : (correct * 100.0 / questions.size());
        saveResult(timeTaken, percentage);
        dispose();
        new ResultScreen(user, score, correct, wrong, timeTaken, percentage);
    }

    private void saveResult(long timeTaken, double percentage) {
        String insert = "INSERT INTO quiz_results(user_id,category_id,score,total_questions,correct_answers,wrong_answers,percentage,time_taken) VALUES(?,?,?,?,?,?,?,?)";
        String update = "UPDATE users SET total_score = total_score + ?, quizzes_taken = quizzes_taken + 1 WHERE user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
             PreparedStatement upd = conn.prepareStatement(update)) {
            stmt.setInt(1, user.getUserId());
            stmt.setInt(2, categoryId);
            stmt.setInt(3, score);
            stmt.setInt(4, questions.size());
            stmt.setInt(5, correct);
            stmt.setInt(6, wrong);
            stmt.setDouble(7, percentage);
            stmt.setLong(8, timeTaken);
            stmt.executeUpdate();

            upd.setInt(1, score);
            upd.setInt(2, user.getUserId());
            upd.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            checkAnswer();
        } else if (e.getSource() == lifelineButton) {
            useLifeline();
        }
    }

    private void useLifeline() {
        lifelineButton.setEnabled(false);
        Question q = questions.get(currentIndex);
        List<JRadioButton> toDisable = new ArrayList<>();
        for (JRadioButton rb : optionButtons) {
            if (!rb.getText().equals(q.getCorrectAnswer())) {
                toDisable.add(rb);
            }
        }
        Collections.shuffle(toDisable);
        for (int i = 0; i < 2 && i < toDisable.size(); i++) {
            toDisable.get(i).setEnabled(false);
        }
    }
}
// --- END QuizScreen.java ---
