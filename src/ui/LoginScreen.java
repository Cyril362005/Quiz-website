// --- LoginScreen.java ---
package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import com.formdev.flatlaf.FlatLightLaf;

import model.DatabaseConnection;
import model.Constants;
import model.User;

/** Email/password login UI. */
public class LoginScreen extends JFrame {
    private JTextField emailField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    public LoginScreen() {
        setTitle("Quiz Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(480,360));
        setLocationRelativeTo(null);
        com.formdev.flatlaf.FlatLightLaf.setup();
        setResizable(false);

        add(new GradientBar(), BorderLayout.NORTH);
        RoundedPanel card = new RoundedPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=0; gbc.gridy=GridBagConstraints.RELATIVE;

        JLabel title=new JLabel("Welcome",SwingConstants.CENTER);
        title.setFont(Constants.TITLE_FONT); title.setForeground(Constants.PRIMARY_PURPLE);
        card.add(title, gbc);
        card.add(labeled("Email", emailField), gbc);
        card.add(labeled("Password", passwordField), gbc);
        JButton login=new JButton("Login");
        login.addActionListener(this::login);
        card.add(wrapHover(login), gbc);
        JButton signup=new JButton("Sign Up");
        signup.addActionListener(e->{dispose();new SignUpScreen().setVisible(true);});
        card.add(wrapHover(signup), gbc);

        add(card, BorderLayout.CENTER);
        pack();
    }

    private JPanel labeled(String text,JComponent comp){
        JPanel p=new JPanel(new BorderLayout());
        p.add(new JLabel(text),BorderLayout.NORTH); p.add(comp,BorderLayout.CENTER); return p;
    }

    private void login(ActionEvent e){
        String email=emailField.getText().trim();
        String pass=new String(passwordField.getPassword());
        if(email.isEmpty()||pass.isEmpty()){JOptionPane.showMessageDialog(this,"Please enter email and password","Warning",JOptionPane.WARNING_MESSAGE);return;}
        try(Connection con=DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement("SELECT id,email,password,high_score FROM users WHERE email=? AND password=?")){
            ps.setString(1,email); ps.setString(2,pass); try(ResultSet rs=ps.executeQuery()){ if(rs.next()){User u=new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)); dispose(); new UserDashboard(u).setVisible(true);} else {JOptionPane.showMessageDialog(this,"Invalid credentials","Error",JOptionPane.ERROR_MESSAGE);} }
        }catch(Exception ex){ex.printStackTrace();JOptionPane.showMessageDialog(this,"Database error","Error",JOptionPane.ERROR_MESSAGE);}    }

    private JLayer<JButton> wrapHover(JButton b){ return new JLayer<>(b,new HoverScaleUI()); }

    /** Top gradient bar. */
    public static class GradientBar extends JPanel {
        public Dimension getPreferredSize(){return new Dimension(100,40);}        
        protected void paintComponent(Graphics g){super.paintComponent(g);Graphics2D g2=(Graphics2D)g;g2.setPaint(new GradientPaint(0,0,Constants.PRIMARY_PURPLE,getWidth(),0,Constants.INDIGO));g2.fillRect(0,0,getWidth(),getHeight());}
    }
    /** Rounded white panel. */
    public static class RoundedPanel extends JPanel{
        RoundedPanel(){setOpaque(false);}        
        protected void paintComponent(Graphics g){Graphics2D g2=(Graphics2D)g.create();g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);Shape r=new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20);g2.setColor(Color.white);g2.fill(r);g2.dispose();}
    }
    /** JLayer UI scaling buttons on hover. */
    public static class HoverScaleUI extends LayerUI<JButton>{
        private boolean hover;
        public void installUI(JComponent c){super.installUI(c);((JLayer<?>)c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK);}        
        public void uninstallUI(JComponent c){((JLayer<?>)c).setLayerEventMask(0);super.uninstallUI(c);}        
        protected void processMouseEvent(MouseEvent e,JLayer<? extends JButton>l){if(e.getID()==MouseEvent.MOUSE_ENTERED)hover=true;else if(e.getID()==MouseEvent.MOUSE_EXITED)hover=false;l.repaint();}
        public void paint(Graphics g,JComponent c){Graphics2D g2=(Graphics2D)g.create();if(hover){g2.scale(1.05,1.05);g2.translate(-c.getWidth()*0.025,-c.getHeight()*0.025);}super.paint(g2,c);g2.dispose();}
    }
}
// --- END LoginScreen.java ---
