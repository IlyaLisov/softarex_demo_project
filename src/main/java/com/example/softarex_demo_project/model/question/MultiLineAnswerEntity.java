package com.example.softarex_demo_project.model.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * This class is a class for multiline answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiLineAnswerEntity extends AnswerEntity {
    @Column(name = "multiline_answer")
    private String answer;

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.MULTILINE_TEXT;
    }
}
