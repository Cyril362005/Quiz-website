// --- SignUpScreen.java ---
package com.quizapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * User registration with email validation.
 */
public class SignUpScreen extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * Constructs the sign up screen.
     */
    public SignUpScreen() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(userLabel);
        usernameField = new JTextField();
        usernameField.setPreferredSize(FIELD_SIZE);
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(emailLabel);
        emailField = new JTextField();
        emailField.setPreferredSize(FIELD_SIZE);
        add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passLabel);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(FIELD_SIZE);
        add(passwordField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setPreferredSize(FIELD_SIZE);
        add(registerButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            register();
        }
    }

    private void register() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(this, "Invalid email format");
            return;
        }
        String check = "SELECT user_id FROM users WHERE email=?";
        String insert = "INSERT INTO users(username,email,password) VALUES(?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement cStmt = conn.prepareStatement(check);
             PreparedStatement iStmt = conn.prepareStatement(insert)) {
            cStmt.setString(1, email);
            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Email already exists");
                return;
            }
            iStmt.setString(1, username);
            iStmt.setString(2, email);
            iStmt.setString(3, password);
            iStmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful");
            dispose();
            new LoginScreen();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
// --- END SignUpScreen.java ---
