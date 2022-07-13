package com.example.softarex_demo_project.dto;

import com.example.softarex_demo_project.model.question.AnswerEntity;
import com.example.softarex_demo_project.model.question.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;

/**
 * This class is a DTO of Question class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QuestionDto {
    private Long id;

    @Valid
    private UserDto author;

    @Valid
    private UserDto recipient;

    private String question;
    private AnswerEntity answerEntity;

    public static QuestionDto fromQuestion(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.author = UserDto.fromUser(question.getAuthor());
        questionDto.recipient = UserDto.fromUser(question.getRecipient());
        questionDto.question = question.getQuestion();
        questionDto.answerEntity = question.getAnswerEntity();
        return questionDto;
    }

    public Question toQuestion() {
        Question result = new Question();
        result.setId(id);
        result.setAuthor(author.toUser());
        result.setRecipient(recipient.toUser());
        result.setQuestion(question);
        result.setAnswerEntity(answerEntity);
        return result;
    }
}
