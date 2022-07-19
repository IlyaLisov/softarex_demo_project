package com.example.softarex_demo_project.dto.question;

import com.example.softarex_demo_project.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * This class is a DTO of Question class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QuestionDto {
    private UUID id;

    @Valid
    private UserDto author;

    @Valid
    private UserDto recipient;

    @NotEmpty(message = "Question can`t be empty.")
    private String question;

    @Valid
    private AnswerEntityDto answerEntity;
}
