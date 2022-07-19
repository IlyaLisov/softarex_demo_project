package com.example.softarex_demo_project.model.question;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a class for radio button answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Data
public class RadioButtonAnswerEntity extends AnswerEntity {
    @Column(name = "radiobox_options")
    @ElementCollection
    private List<String> options;

    @Column(name = "radiobox_answer")
    private String answer;

    public RadioButtonAnswerEntity() {
        this.options = new ArrayList<>();
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.RADIO_BUTTON;
    }
}
