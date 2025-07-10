// --- QuizResult.java ---
package model;

/**
 * Model class holding quiz result details.
 */
public class QuizResult {
    private int score;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private double percentage;
    private int timeTaken;

    public QuizResult(int score, int totalQuestions, int correctAnswers, int wrongAnswers, double percentage, int timeTaken) {
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.percentage = percentage;
        this.timeTaken = timeTaken;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public String verdict() {
        return percentage >= 50 ? "Pass" : "Fail";
    }

    public boolean isCorrect(int index) {
        // Placeholder logic for determining correctness
        return index < correctAnswers;
    }

    public int getCorrect() {
        return correctAnswers;
    }

    public int getWrong() {
        return wrongAnswers;
    }

    public double percentage() {
        return percentage;
    }
}
// --- END QuizResult.java ---
