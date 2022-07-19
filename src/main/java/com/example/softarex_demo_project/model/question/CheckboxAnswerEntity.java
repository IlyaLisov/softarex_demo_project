package com.example.softarex_demo_project.model.question;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a class for checkbox answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Data
public class CheckboxAnswerEntity extends AnswerEntity {
    @Column(name = "checkbox_options")
    @ElementCollection
    private List<String> options;

    @Column(name = "checkbox_answer")
    @ElementCollection
    private List<String> answer;

    public CheckboxAnswerEntity() {
        this.options = new ArrayList<>();
    }

    @Override
    public List<String> getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.CHECKBOX;
    }
}
