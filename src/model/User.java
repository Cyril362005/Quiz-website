// --- User.java ---
package model;
/** Simple user model. */
public class User {
    private int id; private String email; private String password; private int highScore;
    public User() {}
    public User(int id,String email,String password,int highScore){this.id=id;this.email=email;this.password=password;this.highScore=highScore;}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
    public String getPassword(){return password;} public void setPassword(String p){this.password=p;}
    public int getHighScore(){return highScore;} public void setHighScore(int s){this.highScore=s;}
}
// --- END User.java ---
