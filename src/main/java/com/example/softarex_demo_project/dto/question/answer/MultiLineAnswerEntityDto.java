package com.example.softarex_demo_project.dto.question.answer;

import com.example.softarex_demo_project.dto.question.AnswerEntityDto;
import com.example.softarex_demo_project.model.question.AnswerType;
import lombok.Data;

/**
 * This class is a DTO of MultiLineAnswerEntity class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class MultiLineAnswerEntityDto extends AnswerEntityDto {
    private String answer;

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.MULTILINE_TEXT;
    }
}
