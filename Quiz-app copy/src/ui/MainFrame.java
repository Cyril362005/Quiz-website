package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window that keeps one copy of each screen and
 * lets every child screen ask to be displayed with showPanel().
 *
 *  ■  Card names (constants) are defined at the top so you never
 *     mistype them elsewhere in the project.
 */
public class MainFrame extends JFrame {

    /*–––– Card names ––––*/
    public static final String LOGIN        = "Login";
    public static final String SIGN_UP      = "SignUp";
    public static final String LEADERBOARD  = "Leaderboard";
    public static final String DASHBOARD    = "Dashboard";   // ← NEW

    private final CardLayout card = new CardLayout();
    private final JPanel     deck = new JPanel(card);
    private final JProgressBar quizProgress = new JProgressBar(0, questions.size());

    public MainFrame() {
        super("Quiz-App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        /*  ⚠  ONLY the screens whose constructors need *no*
               user-specific data are created here.  */
        deck.add(new LoginScreen(this),       LOGIN);
        deck.add(new SignUpScreen(this),      SIGN_UP);
        deck.add(new LeaderboardScreen(this, 0, "Default Username"), LEADERBOARD);

        quizProgress.setPreferredSize(new Dimension(10, 8));
        quizProgress.setBorderPainted(false);
        quizProgress.setForeground(new Color(0x8B5CF6));  // violet-500
        add(quizProgress, BorderLayout.SOUTH);

        add(deck);
        add(new GradientHeader(), BorderLayout.NORTH);
        showPanel(LOGIN);
    }

    /*–––– navigation helpers ––––*/
    public void showPanel(String name)                 { card.show(deck, name); }
    public void addAndShow(String name, JPanel panel)  { deck.add(panel, name); showPanel(name); }

    public void updateProgressBar(int qIndex) {
        quizProgress.setValue(qIndex);   // Swing animates width automatically
    }

    /*–––– app entry point ––––*/
    public static void main(String[] args) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}