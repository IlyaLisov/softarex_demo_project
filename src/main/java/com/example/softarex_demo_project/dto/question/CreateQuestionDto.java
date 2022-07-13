package com.example.softarex_demo_project.dto.question;

import com.example.softarex_demo_project.model.question.AnswerType;
import com.example.softarex_demo_project.model.question.Question;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This class is a DTO of Question class for creation.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class CreateQuestionDto {
    private Long id;
    private String authorEmail;

    @NotNull
    @NotEmpty(message = "Recipient email can`t be empty.")
    private String recipientEmail;

    @NotNull
    @NotEmpty(message = "Question can`t be empty.")
    private String question;

    private AnswerType answerType;
    private List<String> options;

    public static CreateQuestionDto fromQuestion(Question question, List<String> options) {
        CreateQuestionDto createQuestionDto = new CreateQuestionDto();
        createQuestionDto.setId(question.getId());
        createQuestionDto.setAuthorEmail(question.getAuthor().getEmail());
        createQuestionDto.setRecipientEmail(question.getRecipient().getEmail());
        createQuestionDto.setQuestion(question.getQuestion());
        createQuestionDto.setAnswerType(question.getAnswerEntity().getAnswerType());
        createQuestionDto.setOptions(options);
        return createQuestionDto;
    }
}
