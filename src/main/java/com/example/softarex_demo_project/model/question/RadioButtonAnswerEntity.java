package com.example.softarex_demo_project.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is a class for radio button answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class RadioButtonAnswerEntity extends AnswerEntity {
    @Column(name = "radiobox_options")
    @ElementCollection
    private List<String> options;

    @Column(name = "radiobox_answer")
    private String answer;

    public RadioButtonAnswerEntity() {
        this.options = new ArrayList<>();
        this.answerType = AnswerType.RADIO_BUTTON;
    }

    public RadioButtonAnswerEntity(List<String> options) {
        this.options = options;
        this.answerType = AnswerType.RADIO_BUTTON;
    }

    public RadioButtonAnswerEntity(List<String> options, String answer) {
        this.options = options;
        this.answer = answer;
        this.answerType = AnswerType.RADIO_BUTTON;
    }

    @Override
    public Object getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RadioButtonAnswerEntity that = (RadioButtonAnswerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
