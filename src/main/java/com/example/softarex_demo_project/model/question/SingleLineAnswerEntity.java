package com.example.softarex_demo_project.model.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * This class is a class for single line answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleLineAnswerEntity extends AnswerEntity {
    @Column(name = "single_line_answer")
    private String answer;

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.SINGLE_LINE_TEXT;
    }
}
