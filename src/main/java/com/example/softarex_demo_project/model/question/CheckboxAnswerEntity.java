package com.example.softarex_demo_project.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

/**
 * This class is a class for checkbox answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class CheckboxAnswerEntity extends AnswerEntity {
    @Column(name = "checkbox_answer")
    private Boolean answer;

    public CheckboxAnswerEntity() {
        this.answerType = AnswerType.CHECKBOX;
    }

    public CheckboxAnswerEntity(Boolean answer) {
        this.answer = answer;
        this.answerType = AnswerType.CHECKBOX;
    }

    @Override
    public Object getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CheckboxAnswerEntity that = (CheckboxAnswerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
