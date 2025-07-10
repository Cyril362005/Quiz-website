// --- SignUpScreen.java ---
package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import javax.swing.*;
import java.sql.*;
import com.formdev.flatlaf.FlatLightLaf;

import model.DatabaseConnection;
import model.Constants;

/** Signup UI with regex email validation. */
public class SignUpScreen extends JFrame {
    private JTextField emailField=new JTextField(20);
    private JPasswordField passField=new JPasswordField(20);
    private static final Pattern EMAIL=Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public SignUpScreen(){
        setTitle("Sign Up");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(480,360));
        setLocationRelativeTo(null);
        com.formdev.flatlaf.FlatLightLaf.setup();
        setResizable(false);

        JPanel p=new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(5,5,5,5); gbc.fill=GridBagConstraints.HORIZONTAL; gbc.gridx=0; gbc.gridy=GridBagConstraints.RELATIVE;
        JLabel t=new JLabel("Create Account",SwingConstants.CENTER);
        t.setFont(Constants.TITLE_FONT); t.setForeground(Constants.PRIMARY_PURPLE);
        p.add(t,gbc); p.add(labeled("Email",emailField),gbc); p.add(labeled("Password",passField),gbc);
        JButton c=new JButton("Create"); c.addActionListener(this::create); p.add(c,gbc);
        add(p); pack(); }

    private JPanel labeled(String l,JComponent c){JPanel p=new JPanel(new BorderLayout());p.add(new JLabel(l),BorderLayout.NORTH);p.add(c,BorderLayout.CENTER);return p;}

    private void create(ActionEvent e){
        String email=emailField.getText().trim();
        String pass=new String(passField.getPassword());
        if(email.isEmpty()||pass.isEmpty()){JOptionPane.showMessageDialog(this,"All fields required","Warning",JOptionPane.WARNING_MESSAGE);return;}
        if(!EMAIL.matcher(email).matches()){JOptionPane.showMessageDialog(this,"Invalid email","Error",JOptionPane.ERROR_MESSAGE);return;}
        try(Connection con=DatabaseConnection.getConnection();PreparedStatement ps=con.prepareStatement("INSERT INTO users(email,password) VALUES(?,?)")){ps.setString(1,email);ps.setString(2,pass);ps.executeUpdate();JOptionPane.showMessageDialog(this,"Account created.");dispose();new LoginScreen().setVisible(true);}catch(Exception ex){ex.printStackTrace();JOptionPane.showMessageDialog(this,"Error","Error",JOptionPane.ERROR_MESSAGE);}    }
}
// --- END SignUpScreen.java ---
