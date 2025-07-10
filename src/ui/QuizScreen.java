// --- QuizScreen.java ---
package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JLayer;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

import model.DatabaseConnection;
import model.Question;
import model.User;
import model.Constants;

/** Shows questions, timer, lifeline. */
public class QuizScreen extends JFrame {
    private User user; private String category; private List<Question> questions=new ArrayList<>();
    private int index=0,score=0,timeLeft=15; private Timer timer;
    private JLabel timerLabel=new JLabel(); private JProgressBar progress=new JProgressBar(0,15);
    private JLabel scoreLabel=new JLabel(); private JLabel questionLabel=new JLabel();
    private JButton lifelineBtn=new JButton("50:50"); private JRadioButton[] opts=new JRadioButton[4]; private ButtonGroup grp=new ButtonGroup();
    public QuizScreen(User u,String cat){
        user=u;category=cat;setTitle("Quiz - "+cat);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        com.formdev.flatlaf.FlatLightLaf.setup();
        add(new LoginScreen.GradientBar(),BorderLayout.NORTH);
        JPanel main=new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(main);
        JPanel top=new JPanel(new BorderLayout());
        timerLabel.setFont(Constants.TITLE_FONT);
        top.add(timerLabel,BorderLayout.WEST);
        scoreLabel.setText("Score: 0");
        top.add(scoreLabel,BorderLayout.EAST);
        main.add(top,BorderLayout.NORTH);
        progress.setValue(15);
        main.add(progress,BorderLayout.SOUTH);
        JPanel center=new JPanel(new GridLayout(0,1,5,5));
        questionLabel.setFont(Constants.TEXT_FONT);
        center.add(questionLabel);
        for(int i=0;i<4;i++){opts[i]=new JRadioButton();grp.add(opts[i]);center.add(opts[i]);}
        main.add(center,BorderLayout.CENTER);
        lifelineBtn.addActionListener(e->useLifeline());
        main.add(lifelineBtn,BorderLayout.EAST);
        JButton next=new JButton("Next");
        next.addActionListener(e->nextQuestion());
        main.add(next,BorderLayout.SOUTH);
        loadQuestions();
        showQuestion();
        startTimer();
        pack();
    }
    private void loadQuestions(){try(Connection con=DatabaseConnection.getConnection();PreparedStatement ps=con.prepareStatement("SELECT * FROM questions WHERE category=?")){ps.setString(1,category);try(ResultSet rs=ps.executeQuery()){while(rs.next()){questions.add(new Question(rs.getInt("id"),rs.getString("question"),rs.getString("option_a"),rs.getString("option_b"),rs.getString("option_c"),rs.getString("option_d"),rs.getString("correct_option"),rs.getString("category")));}}}catch(Exception ex){ex.printStackTrace();}Collections.shuffle(questions);}    
    private void showQuestion(){
        if(index>=questions.size()){endQuiz();return;}
        Question q=questions.get(index);
        questionLabel.setText("<html><b>"+q.getQuestion()+"</b></html>");
        for(JRadioButton b:opts){b.setEnabled(true);b.setForeground(Color.black);} 
        lifelineBtn.setEnabled(true);
        grp.clearSelection();
        opts[0].setText(q.getOptionA());
        opts[1].setText(q.getOptionB());
        opts[2].setText(q.getOptionC());
        opts[3].setText(q.getOptionD());
        timeLeft=15;
        progress.setValue(15);
        timerLabel.setText("Time: 15");
        startTimer();
    }
    private void startTimer(){if(timer!=null)timer.stop();timer=new Timer(1000,e->{timeLeft--;progress.setValue(timeLeft);timerLabel.setText("Time: "+timeLeft);if(timeLeft<=5)timerLabel.setForeground(Color.RED);else timerLabel.setForeground(Color.BLACK);if(timeLeft<=0){timer.stop();checkAnswer(null);}});timer.start();}
    private void useLifeline(){lifelineBtn.setEnabled(false);Question q=questions.get(index);List<Integer>d=new ArrayList<>();while(d.size()<2){int r=(int)(Math.random()*4);String t=new String[]{q.getOptionA(),q.getOptionB(),q.getOptionC(),q.getOptionD()}[r];if(!t.equals(q.getCorrectOption())&&!d.contains(r))d.add(r);}for(int i:d)opts[i].setEnabled(false);}    
    private void nextQuestion(){timer.stop();String ans=null;for(JRadioButton b:opts)if(b.isSelected())ans=b.getText();checkAnswer(ans);}    
    private void checkAnswer(String ans){
        Question q=questions.get(index);
        if(q.getCorrectOption().equals(ans)){
            score++;scoreLabel.setText("Score: "+score);
            for(JRadioButton b:opts)if(b.isSelected())b.setForeground(Constants.CORRECT_GREEN);
        }else{
            for(JRadioButton b:opts)if(b.getText().equals(q.getCorrectOption()))b.setForeground(Constants.CORRECT_GREEN);
            if(ans!=null)for(JRadioButton b:opts)if(b.isSelected())b.setForeground(Constants.WRONG_RED);
        }
        index++;
        SwingUtilities.invokeLater(()->{
            try{Thread.sleep(500);}catch(Exception ignored){}
            showQuestion();
        });
    }
    private void endQuiz(){
        saveResult();
        dispose();
        new ResultScreen(user,score).setVisible(true);
    }

    private void saveResult(){
        try(Connection c=DatabaseConnection.getConnection();
            PreparedStatement ps=c.prepareStatement("INSERT INTO results(user_id,score) VALUES(?,?)");
            PreparedStatement up=c.prepareStatement("UPDATE users SET high_score=GREATEST(high_score,?) WHERE id=?");){
            ps.setInt(1,user.getId());
            ps.setInt(2,score);
            ps.executeUpdate();
            up.setInt(1,score);
            up.setInt(2,user.getId());
            up.executeUpdate();
        }catch(Exception ex){ex.printStackTrace();}
    }
}
// --- END QuizScreen.java ---
