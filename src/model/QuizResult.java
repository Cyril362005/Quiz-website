// --- QuizResult.java ---
package model;
/** Stores a user's quiz attempt. */
public class QuizResult {
    private int id; private int userId; private int score; private java.util.Date takenOn;
    public QuizResult() {}
    public QuizResult(int id,int userId,int score,java.util.Date taken){this.id=id;this.userId=userId;this.score=score;this.takenOn=taken;}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getUserId(){return userId;} public void setUserId(int uid){this.userId=uid;}
    public int getScore(){return score;} public void setScore(int s){this.score=s;}
    public java.util.Date getTakenOn(){return takenOn;} public void setTakenOn(java.util.Date d){this.takenOn=d;}
}
// --- END QuizResult.java ---
