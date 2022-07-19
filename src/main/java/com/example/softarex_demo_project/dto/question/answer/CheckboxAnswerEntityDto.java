package com.example.softarex_demo_project.dto.question.answer;

import com.example.softarex_demo_project.dto.question.AnswerEntityDto;
import com.example.softarex_demo_project.model.question.AnswerType;
import lombok.Data;

import java.util.List;

/**
 * This class is a DTO of CheckboxAnswerEntity class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class CheckboxAnswerEntityDto extends AnswerEntityDto {
    private List<String> options;
    private List<String> answer;

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.CHECKBOX;
    }
}
