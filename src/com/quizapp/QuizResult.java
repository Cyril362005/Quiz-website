// --- QuizResult.java ---
package com.quizapp;
/**
 * Quiz result data model.
 */
public class QuizResult {
    private int resultId;
    private int userId;
    private int categoryId;
    private int score;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private double percentage;
    private int timeTaken;

    public QuizResult(int resultId, int userId, int categoryId, int score, int totalQuestions,
                      int correctAnswers, int wrongAnswers, double percentage, int timeTaken) {
        this.resultId = resultId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.percentage = percentage;
        this.timeTaken = timeTaken;
    }

    public int getResultId() { return resultId; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getWrongAnswers() { return wrongAnswers; }
    public double getPercentage() { return percentage; }
    public int getTimeTaken() { return timeTaken; }
}
// --- END QuizResult.java ---
