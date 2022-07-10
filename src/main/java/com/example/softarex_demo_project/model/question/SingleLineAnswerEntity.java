package com.example.softarex_demo_project.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * This class is a class for single line answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
public class SingleLineAnswerEntity extends AnswerEntity {
    @Column(name = "single_line_answer")
    private String answer;

    public SingleLineAnswerEntity() {
        this.answerType = AnswerType.SINGLE_LINE_TEXT;
    }

    public SingleLineAnswerEntity(String answer) {
        this.answerType = AnswerType.SINGLE_LINE_TEXT;
        this.answer = answer;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
