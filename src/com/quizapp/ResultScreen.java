// --- ResultScreen.java ---
package com.quizapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Detailed quiz results with statistics.
 */
public class ResultScreen extends JFrame implements ActionListener {
    private User user;
    private int score;
    private int correct;
    private int wrong;
    private long timeTaken;
    private double percentage;
    private JButton dashboardButton;
    private static final Dimension BTN_SIZE = new Dimension(150, 30);

    /**
     * Constructs result screen.
     */
    public ResultScreen(User user, int score, int correct, int wrong, long timeTaken, double percentage) {
        this.user = user;
        this.score = score;
        this.correct = correct;
        this.wrong = wrong;
        this.timeTaken = timeTaken;
        this.percentage = percentage;

        setTitle("Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);

        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(scoreLabel);
        JLabel correctLabel = new JLabel("Correct: " + correct);
        correctLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(correctLabel);
        JLabel wrongLabel = new JLabel("Wrong: " + wrong);
        wrongLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(wrongLabel);
        JLabel timeLabel = new JLabel("Time: " + timeTaken + " seconds");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(timeLabel);
        JLabel percLabel = new JLabel("Percentage: " + String.format("%.2f", percentage) + "%");
        percLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(percLabel);

        dashboardButton = new JButton("Back to Dashboard");
        dashboardButton.addActionListener(this);
        dashboardButton.setPreferredSize(BTN_SIZE);
        dashboardButton.setFont(new Font("Arial", Font.PLAIN, 14));
        add(dashboardButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardButton) {
            dispose();
            new UserDashboard(user);
        }
    }
}
// --- END ResultScreen.java ---
