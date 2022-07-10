package com.example.softarex_demo_project.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * This class is a class for multiline answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
public class MultiLineAnswerEntity extends AnswerEntity {
    @Column(name = "multi_line_answer")
    private String answer;

    public MultiLineAnswerEntity() {
        this.answerType = AnswerType.MULTILINE_TEXT;
    }

    public MultiLineAnswerEntity(String answer) {
        this.answerType = AnswerType.MULTILINE_TEXT;
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
