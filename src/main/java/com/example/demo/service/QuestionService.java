package com.example.demo.service;

import com.example.demo.dto.Answer;
import com.example.demo.dto.DataHolder;
import com.example.demo.dto.Distribution;
import com.example.demo.dto.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final DataHolder dataHolder;

    public Collection<Question> getAllQuestions(String userId) {

        if (dataHolder.getUserQuestions() == null) {
            return Collections.emptyList();
        }
        return dataHolder.getUserQuestions().get(userId).values();
    }

    public Question getQuestion(String userId, String questionName) {

        if (dataHolder.getUserQuestions() == null ||
                dataHolder.getUserQuestions().get(userId) == null) {
            return null;
        }
        return dataHolder.getUserQuestions().get(userId).get(questionName);
    }


    public void markAnswerForQuestion(String userId, String questionName, String answerId) {

        Question question = dataHolder.getUserQuestions().get(userId).get(questionName);

        if (question != null) {
            Optional<Answer> optional = question.getAnswers().stream().filter(
                    a -> a.getId().equalsIgnoreCase(answerId)).findAny();
            if (optional.isPresent()) {
                optional.get().setSelected(true);
            } else {
                System.out.println("No Result - not enough data. optional = " + optional); // Logger - later
            }
        }
    }

    public void addQuestion(String userId, String questionName, String questionBody) {

        if (dataHolder.getUserQuestions().get(userId) == null) {
            Map<String, Question> inside = new HashMap<>();
            inside.put(questionName, new Question(questionName, questionBody, null));
            dataHolder.getUserQuestions().put(userId, inside);
        } else {
            dataHolder.getUserQuestions().get(userId).put(questionName, new Question(questionName, questionBody, null));
        }
    }

    public void addAnswer(String userId, String questionName, String answerId, String answerBody) {

        Map<String, Question> questionMap = dataHolder.getUserQuestions().get(userId);
        if (questionMap.get(questionName).getAnswers() == null) {
            questionMap.get(questionName).setAnswers(
                    new HashSet<>(Arrays.asList(
                            Answer.builder().id(answerId).answerBody(answerBody).build())));
        } else {
            questionMap.get(questionName).getAnswers().add(
                    Answer.builder().id(answerId).answerBody(answerBody).build());
        }
    }

    public Collection<Distribution> getRelativeDistributionByQuestion(String questionName) {

        Map<String, Map<String, Question>> mapMap = dataHolder.getUserQuestions();

        // Map answerId, count
        Map<String, Integer> answerCount = new HashMap<>();
        for (var entry : mapMap.entrySet()) {

            Question question = entry.getValue().get(questionName);
            Optional<Answer> optionalAnswer = question.getAnswers().stream()
                    .filter(a -> a.getSelected() != null)
                    .filter(Answer::getSelected).findAny();
            if (optionalAnswer.isPresent()) {
                String answerId = optionalAnswer.get().getId();
                answerCount.put(answerId, answerCount.getOrDefault(answerId, 0) + 1);
            }
        }

        Collection<Distribution> distributions = new ArrayList<>(answerCount.size());

        BigDecimal totalCount = BigDecimal.valueOf(answerCount.size());
        for (var entry : answerCount.entrySet()) {

            BigDecimal distributionPerAnswer = BigDecimal.valueOf(entry.getValue()).divide(totalCount, 3, RoundingMode.HALF_UP);
            distributions.add(new Distribution(entry.getKey(), distributionPerAnswer.toPlainString()));
        }
        System.out.println("distributions = " + distributions); // Logger later

        return distributions;
    }


}
