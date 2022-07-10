package com.example.softarex_demo_project.model.question;

import com.example.softarex_demo_project.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * This class is a base class for answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Table(name = "answers")
@Entity
public class AnswerEntity extends BaseEntity {
    @Column(name = "answer_type")
    @Enumerated(value = EnumType.STRING)
    protected AnswerType answerType;

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public Object getAnswer() {
        return null;
    }
}
