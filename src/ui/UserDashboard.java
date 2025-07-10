// --- UserDashboard.java ---
package ui;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.JLayer;
import com.formdev.flatlaf.FlatLightLaf;

import model.DatabaseConnection;
import model.Constants;
import model.User;

/** Shows quiz categories. */
public class UserDashboard extends JFrame {
    private User user;
    public UserDashboard(User user){
        this.user=user;
        setTitle("Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        com.formdev.flatlaf.FlatLightLaf.setup();
        add(new LoginScreen.GradientBar(),BorderLayout.NORTH);
        JPanel main=new JPanel(new GridBagLayout());
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints gbc=new GridBagConstraints(); gbc.insets=new Insets(10,10,10,10); gbc.fill=GridBagConstraints.HORIZONTAL; gbc.gridx=0; gbc.gridy=GridBagConstraints.RELATIVE;
        JLabel hello=new GradientLabel("Welcome, "+user.getEmail());
        main.add(hello,gbc);
        for(String cat:loadCategories()){JButton b=new JButton(cat);b.addActionListener(e->startQuiz(cat));main.add(new JLayer<>(b,new LoginScreen.HoverScaleUI()),gbc);}        
        add(main); pack();
    }
    private List<String> loadCategories(){List<String> l=new ArrayList<>();try(Connection c=DatabaseConnection.getConnection();PreparedStatement ps=c.prepareStatement("SELECT DISTINCT category FROM questions");ResultSet rs=ps.executeQuery()){while(rs.next())l.add(rs.getString(1));}catch(Exception ex){ex.printStackTrace();}return l;}
    private void startQuiz(String cat){dispose();new QuizScreen(user,cat).setVisible(true);}

    /** Label with vertical gradient text. */
    static class GradientLabel extends JLabel {
        GradientLabel(String text){super(text,SwingConstants.CENTER);setFont(Constants.TITLE_FONT);}
        protected void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            FontMetrics fm=g2.getFontMetrics();
            int x=(getWidth()-fm.stringWidth(getText()))/2;
            int y=(getHeight()+fm.getAscent()-fm.getDescent())/2;
            g2.setPaint(new GradientPaint(0,0,Constants.PRIMARY_PURPLE,0,getHeight(),Constants.INDIGO));
            g2.drawString(getText(),x,y);
            g2.dispose();
        }
    }
}
// --- END UserDashboard.java ---
