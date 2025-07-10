// --- SignUpScreen.java ---
package ui;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

/**
 * Screen allowing new user registration.
 */
public class SignUpScreen extends JPanel {
    private final MainFrame parent;
    private JTextField usernameField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton registerButton = new JButton("Register");
    private JButton backButton = new JButton("Back");

    /**
     * Constructs the sign-up screen.
     */
    public SignUpScreen(MainFrame parent) {
        this.parent = parent;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10)); // Added padding for better spacing

        JLabel headerLabel = new JLabel("Sign Up", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH); // Added a header label

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.add(new JLabel("Username:", JLabel.RIGHT));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Email:", JLabel.RIGHT));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Password:", JLabel.RIGHT));
        formPanel.add(passwordField);
        add(formPanel, BorderLayout.CENTER);

        registerButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font for button
        registerButton.setBackground(Color.LIGHT_GRAY); // Added background color to button
        registerButton.setForeground(Color.BLACK); // Set button text color

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(_ -> register());
        backButton.addActionListener(_ -> parent.showPanel("Login"));
    }

    private void register() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill mandatory fields");
            return;
        }
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format");
            return;
        }
        // TODO: Replace with actual registration logic
        JOptionPane.showMessageDialog(this, "Registration successful");
        parent.showPanel("Login");
    }
}
// --- END SignUpScreen.java ---
