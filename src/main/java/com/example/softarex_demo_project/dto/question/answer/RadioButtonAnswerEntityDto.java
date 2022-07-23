package com.example.softarex_demo_project.dto.question.answer;

import com.example.softarex_demo_project.dto.question.AnswerEntityDto;
import com.example.softarex_demo_project.model.question.AnswerType;
import lombok.Data;

import java.util.List;

/**
 * This class is a DTO of RadioButtonAnswerEntity class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class RadioButtonAnswerEntityDto extends AnswerEntityDto {
    private List<String> options;

    private String answer;

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.RADIO_BUTTON;
    }
}
