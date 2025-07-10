// --- LeaderboardScreen.java ---
package ui;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatLightLaf;

import model.DatabaseConnection;

/** JTable of top 10 users auto refreshed. */
public class LeaderboardScreen extends JFrame {
    private JTable table=new JTable();
    private Timer refresh;
    public LeaderboardScreen(){
        setTitle("Leaderboard"); setDefaultCloseOperation(DISPOSE_ON_CLOSE); setPreferredSize(new Dimension(400,300)); setLocationRelativeTo(null); com.formdev.flatlaf.FlatLightLaf.setup();
        add(new JScrollPane(table)); loadData(); refresh=new Timer(30000,e->loadData()); refresh.start(); pack(); }
    private void loadData(){DefaultTableModel m=new DefaultTableModel(new Object[]{"User","Score"},0);try(Connection c=DatabaseConnection.getConnection();PreparedStatement ps=c.prepareStatement("SELECT email,high_score FROM users ORDER BY high_score DESC LIMIT 10");ResultSet rs=ps.executeQuery()){while(rs.next())m.addRow(new Object[]{rs.getString(1),rs.getInt(2)});}catch(Exception ex){ex.printStackTrace();}table.setModel(m);}    
}
// --- END LeaderboardScreen.java ---
