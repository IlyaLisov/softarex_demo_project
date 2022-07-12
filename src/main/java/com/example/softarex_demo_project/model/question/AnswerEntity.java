package com.example.softarex_demo_project.model.question;

import com.example.softarex_demo_project.model.BaseEntity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This class is a base class for answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Table(name = "answers")
@Entity
@Getter
@Setter
@ToString
public abstract class AnswerEntity extends BaseEntity {
    @Column(name = "answer_type")
    @Enumerated(value = EnumType.STRING)
    @NonNull
    protected AnswerType answerType;

    public abstract Object getAnswer();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnswerEntity that = (AnswerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
