// --- Constants.java ---
package model;
/** Provides application constants like DB configuration and theme colors. */
public class Constants {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/quizdb";
    public static final String DB_USER = "quizuser";
    public static final String DB_PASSWORD = "quizpass";

    public static final java.awt.Color PRIMARY_PURPLE = new java.awt.Color(102,0,204);
    public static final java.awt.Color INDIGO = new java.awt.Color(63,81,181);
    public static final java.awt.Color CORRECT_GREEN = new java.awt.Color(76,175,80);
    public static final java.awt.Color WRONG_RED = new java.awt.Color(244,67,54);

    public static final java.awt.Font TITLE_FONT = new java.awt.Font("SansSerif", java.awt.Font.BOLD,24);
    public static final java.awt.Font TEXT_FONT = new java.awt.Font("SansSerif", java.awt.Font.PLAIN,16);

    private Constants() {}
}
// --- END Constants.java ---
