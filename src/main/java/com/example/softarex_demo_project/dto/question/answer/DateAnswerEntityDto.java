package com.example.softarex_demo_project.dto.question.answer;

import com.example.softarex_demo_project.dto.question.AnswerEntityDto;
import com.example.softarex_demo_project.model.question.AnswerType;
import lombok.Data;

import java.util.Date;

/**
 * This class is a DTO of DateAnswerEntity class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class DateAnswerEntityDto extends AnswerEntityDto {
    private Date answer;

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.DATE;
    }
}
