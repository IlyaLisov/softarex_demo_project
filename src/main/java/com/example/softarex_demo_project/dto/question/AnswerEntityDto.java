package com.example.softarex_demo_project.dto.question;

import com.example.softarex_demo_project.model.question.AnswerType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

/**
 * This class is a DTO of AnswerEntity class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AnswerEntityDto {
    protected UUID id;

    protected AnswerType answerType;
}