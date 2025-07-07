// --- Question.java ---
package com.quizapp;
/**
 * Question data model.
 */
public class Question {
    private int questionId;
    private int categoryId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
    private String difficultyLevel;

    public Question(int questionId, int categoryId, String questionText,
                    String option1, String option2, String option3, String option4,
                    String correctAnswer, String difficultyLevel) {
        this.questionId = questionId;
        this.categoryId = categoryId;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.difficultyLevel = difficultyLevel;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
}
// --- END Question.java ---
