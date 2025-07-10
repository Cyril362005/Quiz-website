// --- Question.java ---
package model;
/** Model for a quiz question. */
public class Question {
    private int id; private String question; private String optionA; private String optionB; private String optionC; private String optionD; private String correctOption; private String category;
    public Question() {}
    public Question(int id,String q,String a,String b,String c,String d,String correct,String cat){this.id=id;this.question=q;this.optionA=a;this.optionB=b;this.optionC=c;this.optionD=d;this.correctOption=correct;this.category=cat;}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getQuestion(){return question;} public void setQuestion(String q){this.question=q;}
    public String getOptionA(){return optionA;} public void setOptionA(String a){this.optionA=a;}
    public String getOptionB(){return optionB;} public void setOptionB(String b){this.optionB=b;}
    public String getOptionC(){return optionC;} public void setOptionC(String c){this.optionC=c;}
    public String getOptionD(){return optionD;} public void setOptionD(String d){this.optionD=d;}
    public String getCorrectOption(){return correctOption;} public void setCorrectOption(String c){this.correctOption=c;}
    public String getCategory(){return category;} public void setCategory(String c){this.category=c;}
}
// --- END Question.java ---
