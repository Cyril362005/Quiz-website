package service;

import model.Question;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing questions.
 */
public class QuestionService {

    /**
     * Fetches a list of ten questions.
     * This is a placeholder implementation and should be replaced with actual logic.
     *
     * @return a list of ten questions
     */
    public static List<Question> fetchTen() {
        List<Question> questions = new ArrayList<>();

        // Placeholder logic: Add dummy questions
        for (int i = 1; i <= 10; i++) {
            Question question = new Question();
            question.setQuestionId(i);
            question.setQuestionText("Question " + i);
            question.setOptions(new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
            question.setCorrectAnswer("Option 1");
            questions.add(question);
        }

        return questions;
    }
}