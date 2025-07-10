// --- QuizScreen.java ---
import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.applet.Applet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * Screen that presents quiz questions.
 */
public class QuizScreen extends JPanel {
    private final MainFrame parent;
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
    private Clip successClip;
    private Clip failureClip;

    /**
     * Creates a quiz screen for the specified user.
     *
     * @param userId the user taking the quiz
     */
    public QuizScreen(MainFrame parent, int userId) {
        this.parent = parent;
        this.userId = userId;
        buildUI();
        loadAudioClips();
        loadQuestions();
        playStartSound(); // Play sound effect for quiz start
        showQuestion();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10)); // Added padding for better spacing

        JLabel headerLabel = new JLabel("Quiz", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH); // Added a header label

        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font for question text
        add(questionLabel, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.add(optionButtons[0]);
        optionsPanel.add(optionButtons[1]);
        optionsPanel.add(optionButtons[2]);
        optionsPanel.add(optionButtons[3]);
        add(optionsPanel, BorderLayout.SOUTH);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for buttons
            optionButtons[i].setBackground(Color.LIGHT_GRAY); // Added background color to buttons
            optionButtons[i].setForeground(Color.BLACK); // Set button text color
        }

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

        nextButton.addActionListener(_ -> nextQuestion());
        lifelineButton.addActionListener(_ -> applyLifeline());

        // Enhance the progress bar to display percentage and add smooth animations
        progressBar.setStringPainted(true); // Display percentage
        progressBar.setForeground(new Color(45, 180, 255)); // Set custom color
        progressBar.setValue(0); // Initialize value
        progressBar.setMaximum(questions.size());

        // Add a vertical sidebar with icons for navigation
        JPanel sidebarPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        sidebarPanel.setBackground(Color.DARK_GRAY);

        // Create navigation buttons with icons
        JButton homeButton = new JButton(new ImageIcon("src/resources/home.png"));
        homeButton.setToolTipText("Home");
        homeButton.setBackground(Color.DARK_GRAY);
        homeButton.setBorderPainted(false);
        homeButton.addActionListener(_ -> navigateToHome());

        JButton quizButton = new JButton(new ImageIcon("src/resources/quiz.png"));
        quizButton.setToolTipText("Quiz");
        quizButton.setBackground(Color.DARK_GRAY);
        quizButton.setBorderPainted(false);
        quizButton.addActionListener(_ -> navigateToQuiz());

        JButton leaderboardButton = new JButton(new ImageIcon("src/resources/leaderboard.png"));
        leaderboardButton.setToolTipText("Leaderboard");
        leaderboardButton.setBackground(Color.DARK_GRAY);
        leaderboardButton.setBorderPainted(false);
        leaderboardButton.addActionListener(_ -> navigateToLeaderboard());

        JButton logoutButton = new JButton(new ImageIcon("src/resources/logout.png"));
        logoutButton.setToolTipText("Logout");
        logoutButton.setBackground(Color.DARK_GRAY);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(_ -> logout());

        // Add buttons to the sidebar
        sidebarPanel.add(homeButton);
        sidebarPanel.add(quizButton);
        sidebarPanel.add(leaderboardButton);
        sidebarPanel.add(logoutButton);

        // Add the sidebar to the main frame
        add(sidebarPanel, BorderLayout.WEST);
    }

    private void loadAudioClips() {
        try {
            successClip = AudioSystem.getClip();
            successClip.open(AudioSystem.getAudioInputStream(new File("src/resources/correct.wav")));

            failureClip = AudioSystem.getClip();
            failureClip.open(AudioSystem.getAudioInputStream(new File("src/resources/incorrect.wav")));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load audio clips: " + e.getMessage());
        }
    }

    /**
     * Plays a sound effect for the quiz start.
     */
    private void playStartSound() {
        try {
            Clip startClip = AudioSystem.getClip();
            startClip.open(AudioSystem.getAudioInputStream(new File("src/resources/Start.wav")));
            startClip.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to play start sound: " + e.getMessage());
        }
    }

    private void loadQuestions() {
        // Mock questions for testing
        for (int i = 1; i <= 10; i++) {
            Question q = new Question();
            q.setQuestionId(i);
            q.setCategoryId(1);
            q.setQuestionText("Sample Question " + i + "?");
            String[] opts = new String[4];
            opts[0] = "Option 1-" + i;
            opts[1] = "Option 2-" + i;
            opts[2] = "Option 3-" + i;
            opts[3] = "Option 4-" + i;
            q.setOptions(opts);
            q.setCorrectAnswer("Option 1-" + i);
            questions.add(q);
        }
        progressBar.setMaximum(questions.size());
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
        // Update progress bar with animation
        updateProgressBar(currentIndex);
        timerLabel.setText("15");
        if (timer != null) timer.stop();
        timer = new Timer(1000, _ -> {
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

    // Smooth animation for progress bar update
    private void updateProgressBar(int value) {
        Timer animationTimer = new Timer(10, null);
        animationTimer.addActionListener(e -> {
            int currentValue = progressBar.getValue();
            if (currentValue < value) {
                progressBar.setValue(currentValue + 1);
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        animationTimer.start();
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
            successClip.setFramePosition(0);
            successClip.start();
        } else {
            failureClip.setFramePosition(0);
            failureClip.start();
        }
        currentIndex++;
        showQuestion();
    }

    private void finishQuiz() {
        progressBar.setValue(questions.size());
        QuizResult result = new QuizResult(score, questions.size(), score, questions.size()-score,
                ((double)score/questions.size())*100, timeTaken);
        // Navigate to result screen
        parent.addAndShow("Result",
                new ResultScreen(parent, userId,
                        new QuizResult(score, questions.size(),
                                score, questions.size() - score,
                                (score * 100.0) / questions.size(),
                                timeTaken)));
    }

    // Navigation methods
    private void navigateToHome()       { parent.showPanel(MainFrame.DASHBOARD); }
    private void navigateToQuiz()        { parent.addAndShow("Quiz" + userId, new QuizScreen(parent, userId)); }
    private void navigateToLeaderboard() { parent.showPanel(MainFrame.LEADERBOARD); }
    private void logout() {
        parent.showPanel("Login");
    }

    private JButton makeOptionButton(int idx) {
        JButton b = new JButton();
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16f));
        b.setBackground(new Color(0x1E293B)); // dark slate
        b.setForeground(Color.WHITE);

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (b.isEnabled()) b.setBackground(new Color(0x6366F1)); // indigo-400
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                if (b.isEnabled()) b.setBackground(new Color(0x1E293B));
            }
        });

        b.putClientProperty("idx", idx);
        b.addActionListener(e -> checkAnswer((int) b.getClientProperty("idx")));
        return b;
    }

    private void lockButtons(int chosen, boolean ok) {
        optionButtons[chosen].setBackground(ok ? new Color(0x22C55E) : new Color(0xEF4444));
        optionButtons[questions.get(currentIndex).getCorrectAnswerIndex()].setBackground(new Color(0x22C55E));
    }

    // After locking buttons, color them based on correctness
    lockButtons(chosen, ok);
}
// --- END QuizScreen.java ---
