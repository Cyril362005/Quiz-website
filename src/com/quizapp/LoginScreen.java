// --- LoginScreen.java ---
package com.quizapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Email/password authentication interface.
 */
public class LoginScreen extends JFrame implements ActionListener {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);

    /**
     * Constructs the login screen.
     */
    public LoginScreen() {
        setTitle("Quiz Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);

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

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setPreferredSize(FIELD_SIZE);
        add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(this);
        signUpButton.setPreferredSize(FIELD_SIZE);
        add(signUpButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            login();
        } else if (e.getSource() == signUpButton) {
            dispose();
            new SignUpScreen();
        }
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter email and password");
            return;
        }
        String query = "SELECT * FROM users WHERE email=? AND password=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                dispose();
                new UserDashboard(new User(userId, username, email));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
// --- END LoginScreen.java ---
