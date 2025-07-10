// --- DatabaseConnection.java ---
package model;

import java.sql.*;

/** JDBC helper using PreparedStatement. */
public class DatabaseConnection {
    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { e.printStackTrace(); }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
    }
    public static void close(Connection c, PreparedStatement ps, ResultSet rs) {
        try { if(rs!=null) rs.close(); } catch(Exception ignored) {}
        try { if(ps!=null) ps.close(); } catch(Exception ignored) {}
        try { if(c!=null) c.close(); } catch(Exception ignored) {}
    }
}
// --- END DatabaseConnection.java ---
