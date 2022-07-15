package com.example.softarex_demo_project.dto.question;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This class is a DTO of Question class for answering.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class AnswerQuestionDto {
    private UUID questionId;
    private String stringAnswer;
    private int optionIndex;
    private List<String> answerList;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
