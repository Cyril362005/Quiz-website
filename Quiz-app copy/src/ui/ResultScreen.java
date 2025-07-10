// --- ResultScreen.java ---
package ui;

import db.DatabaseConnection;
import model.Question;
import model.QuizResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Displays quiz results and stores them in the database.
 */
public class ResultScreen extends JPanel {
    /**
     * Creates a result screen.
     *
     * @param result the quiz result
     * @param reviewList     the list of questions
     */
    public ResultScreen(QuizResult result, List<Question> reviewList) {
        setLayout(new BorderLayout());
        add(new GradientHeader(), BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setBackground(new Color(0x1E2538, true));
        card.setBorder(new javax.swing.border.EmptyBorder(32, 48, 32, 48));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = bigLabel("Quiz Completed!", 32);
        JLabel verdict = new JLabel(result.verdict(), SwingConstants.CENTER);
        verdict.setForeground(new Color(0x9CA3AF));

        JLabel percent = bigLabel(result.percentage() + " %", 40, new Color(0x22C55E));
        JLabel correct = new JLabel(result.getCorrect() + " correct", SwingConstants.CENTER);
        correct.setForeground(Color.WHITE);
        JLabel wrong = new JLabel(result.getWrong() + " wrong", SwingConstants.CENTER);
        wrong.setForeground(Color.WHITE);

        JButton again = stdButton("Play Again", e -> {});
        JButton board = stdButton("Leaderboard", e -> {});

        /* layout */
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(verdict);
        card.add(Box.createVerticalStrut(24));
        card.add(percent);
        card.add(Box.createVerticalStrut(16));
        card.add(correct);
        card.add(wrong);
        card.add(Box.createVerticalStrut(24));
        card.add(makeReviewTable(reviewList, result));              // scroll pane
        card.add(Box.createVerticalStrut(24));
        JPanel row = new JPanel(new GridLayout(1, 2, 16, 0));
        row.setOpaque(false);
        row.add(again);
        row.add(board);
        card.add(row);

        add(card, BorderLayout.CENTER);

        /* simple fade-in */
        setOpaque(false);
        new com.formdev.flatlaf.util.Animator(400,
                f -> {
                    setOpaque(true);
                    repaint();
                }).start();
    }

    /* helper factories */
    private JLabel bigLabel(String s, int size) {
        return bigLabel(s, size, Color.WHITE);
    }

    private JLabel bigLabel(String s, int size, Color c) {
        JLabel l = new JLabel(s, SwingConstants.CENTER);
        l.setFont(l.getFont().deriveFont(Font.BOLD, size));
        l.setForeground(c);
        return l;
    }

    private JButton stdButton(String txt, java.awt.event.ActionListener al) {
        JButton b = new JButton(txt);
        b.addActionListener(al);
        b.setBackground(new Color(0x4F46E5));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private JScrollPane makeReviewTable(List<Question> qs, QuizResult res) {
        JPanel table = new JPanel();
        table.setLayout(new GridLayout(qs.size(), 2, 8, 8));
        table.setBackground(new Color(0x1E2538));

        for (int i = 0; i < qs.size(); i++) {
            Question q = qs.get(i);
            JLabel questionLabel = new JLabel(q.getQuestionText());
            questionLabel.setForeground(Color.WHITE);

            JLabel iconLabel = new JLabel(res.isCorrect(i) ? "✔" : "✖", SwingConstants.CENTER);
            iconLabel.setForeground(res.isCorrect(i) ? new Color(0x22C55E) : new Color(0xEF4444));

            table.add(questionLabel);
            table.add(iconLabel);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        return scrollPane;
    }
}
// --- END ResultScreen.java ---
