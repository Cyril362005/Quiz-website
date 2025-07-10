// --- LoginScreen.java ---
package ui;

import com.formdev.flatlaf.FlatLightLaf;
import db.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.prefs.Preferences;

/**
 * Login window allowing users to authenticate.
 */
public class LoginScreen extends JPanel {
    private final MainFrame parent;
    private JTextField emailField = new JTextField(20) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                g.setColor(Color.GRAY);
                g.drawString("Enter your email", getInsets().left, g.getFontMetrics().getHeight());
            }
        }
    };
    private JPasswordField passwordField = new JPasswordField(20) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0) {
                g.setColor(Color.GRAY);
                g.drawString("Enter your password", getInsets().left, g.getFontMetrics().getHeight());
            }
        }
    };
    private JButton loginButton = new JButton("🔓 Login");
    private JButton signUpButton = new JButton("📝 Sign Up");
    private JCheckBox rememberMeCheckBox = new JCheckBox("Remember Me");
    private JProgressBar progressBar = new JProgressBar();

    /**
     * Constructs the login screen.
     */
    public LoginScreen(MainFrame parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); // Added padding for better spacing

        GradientPanel headerPanel = new GradientPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel headerLabel = new JLabel("Login", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Username:", JLabel.RIGHT));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Password:", JLabel.RIGHT));
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);
        formPanel.add(rememberMeCheckBox);
        add(formPanel, BorderLayout.CENTER);

        loginButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for button
        loginButton.setBackground(Color.LIGHT_GRAY); // Added background color to button
        loginButton.setForeground(Color.BLACK); // Set button text color

        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        add(progressBar, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(signUpButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load saved preferences
        Preferences prefs = Preferences.userRoot().node("quiz_app");
        emailField.setText(prefs.get("email", ""));
        rememberMeCheckBox.setSelected(prefs.getBoolean("rememberMe", false));

        // Set rounded borders for text fields
        emailField.setBorder(new CompoundBorder(
            new LineBorder(Color.GRAY, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        passwordField.setBorder(new CompoundBorder(
            new LineBorder(Color.GRAY, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));

        loginButton.addActionListener(this::loginAction);
        signUpButton.addActionListener(e ->
                parent.showPanel(MainFrame.SIGN_UP));   // ← NO new SignUpScreen()

        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(45, 180, 255));
            }
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        signUpButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                signUpButton.setBackground(new Color(45, 180, 255));
            }
            public void mouseExited(MouseEvent e) {
                signUpButton.setBackground(Color.LIGHT_GRAY);
            }
        });
    }

    private void loginAction(ActionEvent e) {
        progressBar.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields");
                progressBar.setVisible(false);
                return;
            }

            // Removed lingering DatabaseConnection references

            progressBar.setVisible(false);
        });
    }
}

class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, getWidth(), getHeight(), Color.BLUE);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
// --- END LoginScreen.java ---
