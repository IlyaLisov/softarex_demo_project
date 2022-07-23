package com.example.softarex_demo_project.dto.question;

import com.example.softarex_demo_project.model.question.AnswerType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.example.softarex_demo_project.config.ApplicationConstants.MAX_FIELD_LENGTH;
import static com.example.softarex_demo_project.config.ApplicationConstants.MIN_FIELD_LENGTH;

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
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Length must be in {min} - {max}")
    private String question;

    private AnswerType answerType;
    private List<String> options;
}
