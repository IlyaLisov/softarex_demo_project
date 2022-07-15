package com.example.softarex_demo_project.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

/**
 * This class is a class for multiline answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class MultiLineAnswerEntity extends AnswerEntity {
    @Column(name = "multiline_answer")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MultiLineAnswerEntity that = (MultiLineAnswerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
