package com.example.softarex_demo_project.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Objects;

/**
 * This class is a class for date answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class DateAnswerEntity extends AnswerEntity {
    @Column(name = "date_answer")
    private Date answer;

    public DateAnswerEntity() {
        this.answerType = AnswerType.DATE;
    }

    public DateAnswerEntity(Date answer) {
        this.answer = answer;
        this.answerType = AnswerType.DATE;
    }

    @Override
    public Date getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DateAnswerEntity that = (DateAnswerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
