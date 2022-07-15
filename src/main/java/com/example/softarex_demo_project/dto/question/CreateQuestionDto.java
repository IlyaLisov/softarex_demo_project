package com.example.softarex_demo_project.dto.question;

import com.example.softarex_demo_project.model.question.AnswerType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * This class is a DTO of Question class for creation.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class CreateQuestionDto {
    private UUID id;
    private String authorEmail;

    @NotNull
    @NotEmpty(message = "Recipient email can`t be empty.")
    private String recipientEmail;

    @NotNull
    @NotEmpty(message = "Question can`t be empty.")
    private String question;

    private AnswerType answerType;
    private List<String> options;
}
