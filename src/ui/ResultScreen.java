// --- ResultScreen.java ---
package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import model.User;
import model.Constants;

/** Final score display with fade in. */
public class ResultScreen extends JFrame {
    private float alpha=0f; private Timer fade;
    public ResultScreen(User user,int score){
        setTitle("Results"); setDefaultCloseOperation(EXIT_ON_CLOSE); setPreferredSize(new Dimension(400,300)); setLocationRelativeTo(null); com.formdev.flatlaf.FlatLightLaf.setup();
        add(new LoginScreen.GradientBar(),BorderLayout.NORTH);
        JLabel msg=new JLabel("Your Score: "+score,SwingConstants.CENTER); msg.setFont(Constants.TITLE_FONT); add(msg,BorderLayout.CENTER);
        JButton lb=new JButton("Leaderboard"); lb.addActionListener(e->{dispose();new LeaderboardScreen().setVisible(true);}); add(lb,BorderLayout.SOUTH);
        pack(); startFade(); }
    private void startFade(){fade=new Timer(40,e->{alpha+=0.05f;if(alpha>=1f){alpha=1f;fade.stop();}repaint();});fade.start();}
    public void paint(Graphics g){Graphics2D g2=(Graphics2D)g.create();g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));super.paint(g2);g2.dispose();}
}
// --- END ResultScreen.java ---
