package ui;

import model.Question;
import model.QuizResult;
import service.QuestionService;
import com.formdev.flatlaf.util.Animator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.io.File;

/**
 * Screen that presents quiz questions.
 */
public class QuizScreen extends JPanel {
    private final MainFrame parent;
    private int userId;
    private List<Question> questions = new java.util.ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private int timeTaken = 0;
    private Timer timer;
    private JLabel questionLabel = new JLabel();
    private JRadioButton[] optionButtons = new JRadioButton[4];
    private ButtonGroup optionGroup = new ButtonGroup();
    private JButton nextButton = new JButton("Next");
    private JButton lifelineButton = new JButton("\u2728  50:50");
    private JProgressBar progressBar = new JProgressBar();
    private JLabel timerLabel = new JLabel("15");
    private javax.sound.sampled.Clip successClip;
    private javax.sound.sampled.Clip failureClip;
    private Animator pulseAnimator;
    private final JLabel scoreLbl = new JLabel("Score: 0");
    private JDialog spinner; // Modal spinner dialog

    /** Four answer buttons, indexed 0-3 */
    private final JButton[] optBtn = new JButton[4];

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
        playStartSound(); // Play sound effect for quiz start
        loadQuestionsAsync();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10)); // Added padding for better spacing

        JLabel headerLabel = new JLabel("Quiz", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH); // Added a header label

        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font for question text
        add(questionLabel, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 16, 16));
        for (int i = 0; i < 4; i++) {
            optBtn[i] = makeOptionButton(i);   // ← store in the array
            optionsPanel.add(optBtn[i]);
        }
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

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(scoreLbl, BorderLayout.WEST);        // left side
        topBar.add(timerLabel, BorderLayout.EAST);      // you already had timerLabel
        add(topBar, BorderLayout.NORTH);

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

        // Lifeline button styling
        lifelineButton.setBackground(new Color(0x1E293B));
        lifelineButton.setForeground(Color.WHITE);
        lifelineButton.setFocusPainted(false);

        // When disabled
        lifelineButton.setEnabled(false);
        lifelineButton.setBackground(new Color(0x334155));   // dim slate

        scoreLbl.setForeground(new Color(0x22C55E));               // emerald-400
        scoreLbl.setFont(scoreLbl.getFont().deriveFont(Font.BOLD,16f));
        scoreLbl.setIcon(new ImageIcon("src/resources/icons/sparkles16.png")); // optional icon

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 48, 32, 48));
        card.setBackground(new Color(0x1E293B, true));

        /* 🪄 wrap the card */
        JLayer<JComponent> cardLayer = new JLayer<>(card, new HoverScaleUI());

        add(cardLayer, BorderLayout.CENTER);              // instead of add(card,…

    }

    private JButton makeOptionButton(int index) {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        return button;
    }

    private void loadAudioClips() {
        try {
            successClip = javax.sound.sampled.AudioSystem.getClip();
            successClip.open(javax.sound.sampled.AudioSystem.getAudioInputStream(new File("src/resources/correct.wav")));

            failureClip = javax.sound.sampled.AudioSystem.getClip();
            failureClip.open(javax.sound.sampled.AudioSystem.getAudioInputStream(new File("src/resources/incorrect.wav")));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load audio clips: " + e.getMessage());
        }
    }

    /**
     * Plays a sound effect for the quiz start.
     */
    private void playStartSound() {
        try {
            javax.sound.sampled.Clip startClip = javax.sound.sampled.AudioSystem.getClip();
            startClip.open(javax.sound.sampled.AudioSystem.getAudioInputStream(new File("src/resources/Start.wav")));
            startClip.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to play start sound: " + e.getMessage());
        }
    }

    private void loadQuestionsAsync() {
        // (a) draw the spinner
        spinner = createSpinnerDialog();
        spinner.setVisible(true);

        /* (b) run the heavy work in a background thread */
        new SwingWorker<List<Question>, Void>() {
            @Override
            protected List<Question> doInBackground() throws Exception {
                return QuestionService.fetchTen();
            }
            @Override
            protected void done() {
                try {
                    questions = get(); // Assign to your field
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            QuizScreen.this,
                            "Couldn’t load questions – please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    spinner.dispose();
                    return;
                }
                spinner.dispose(); // (c) hide spinner
                initialiseQuizUI(); // Draw first question
            }
        }.execute();
    }

    private void startPulse() {
        stopPulse();                                            // stop any previous
        Color base = new Color(0xFACC15);                       // yellow-300

        pulseAnimator = new Animator(800, frac -> {            // 0 → 1 easing
            // sine-wave alpha   (0.4‒1.0 opacity)
            double a = 0.4 + 0.6 * Math.sin(frac * Math.PI * 2);
            timerLabel.setForeground(new Color(
                base.getRed(), base.getGreen(), base.getBlue(),
                (int) (a * 255)
            ));
        });
        pulseAnimator.setRepeatCount(Animator.INFINITE);
        pulseAnimator.start();
    }

    private void stopPulse() {
        if (pulseAnimator != null) {
            pulseAnimator.stop();
            pulseAnimator = null;
            timerLabel.setForeground(new Color(0xFACC15));        // solid yellow
        }
    }

    private void initialiseQuizUI() {
        currentIndex = 0;
        score = 0;
        timeTaken = 0;
        showQuestion(); // Show first question
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
        timerLabel.setForeground(Color.WHITE);     // base colour for 6-20 s
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
        List<Integer> indices = new java.util.ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (!optionButtons[i].getText().equals(q.getCorrectAnswer())) {
                indices.add(i);
            }
        }
        java.util.Collections.shuffle(indices);
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
            scoreLbl.setText("Score: " + score);  // Update score label
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
        QuizResult result = new QuizResult(score, questions.size(), score, questions.size() - score,
                ((double) score / questions.size()) * 100, timeTaken);
        parent.addAndShow("Result",
                new ResultScreen(parent, result, questions));
    }

    // Navigation methods
    private void navigateToHome()       { parent.showPanel(MainFrame.DASHBOARD); }
    private void navigateToQuiz()        { parent.addAndShow("Quiz" + userId, new QuizScreen(parent, userId)); }
    private void navigateToLeaderboard() { parent.showPanel(MainFrame.LEADERBOARD); }
    private void logout() {
        parent.showPanel("Login");
    }

    private JDialog createSpinnerDialog() {
        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog dlg = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setUndecorated(true);

        JPanel spinnerPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                int s = 36, w = 8;
                int x = (getWidth() - s) / 2, y = (getHeight() - s) / 2;
                g2.setStroke(new BasicStroke(w, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(0x6366F1)); // Indigo accent
                long t = System.currentTimeMillis() / 5;
                g2.drawArc(x, y, s, s, (int) (t % 360), 270);
                repaint(); // Simple animation
            }
        };
        spinnerPanel.setPreferredSize(new Dimension(80, 80));

        dlg.add(spinnerPanel);
        dlg.pack();
        dlg.setLocationRelativeTo(owner);
        return dlg;
    }
}
// --- END QuizScreen.java ---
