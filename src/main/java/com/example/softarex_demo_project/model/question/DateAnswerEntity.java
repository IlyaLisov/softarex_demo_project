package com.example.softarex_demo_project.model.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * This class is a class for date answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateAnswerEntity extends AnswerEntity {
    @Column(name = "date_answer")
    private Date answer;

    @Override
    public Date getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.DATE;
    }
}
