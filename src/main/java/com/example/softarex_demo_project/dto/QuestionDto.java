package com.example.softarex_demo_project.dto;

import com.example.softarex_demo_project.model.question.AnswerEntity;
import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class is a DTO of Question class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDto {
    private Long id;
    private User author;
    private User recipient;
    private String question;
    private AnswerEntity answerEntity;

    public static QuestionDto fromQuestion(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.author = question.getAuthor();
        questionDto.recipient = question.getRecipient();
        questionDto.question = question.getQuestion();
        questionDto.answerEntity = question.getAnswerEntity();
        return questionDto;
    }

    public Question toQuestion() {
        Question result = new Question();
        result.setId(id);
        result.setAuthor(author);
        result.setRecipient(recipient);
        result.setQuestion(question);
        result.setAnswerEntity(answerEntity);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerEntity getAnswerEntity() {
        return answerEntity;
    }

    public void setAnswerEntity(AnswerEntity answerEntity) {
        this.answerEntity = answerEntity;
    }
}
