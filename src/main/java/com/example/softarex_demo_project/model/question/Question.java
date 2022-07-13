package com.example.softarex_demo_project.model.question;

import com.example.softarex_demo_project.model.BaseEntity;
import com.example.softarex_demo_project.model.user.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This class is a question class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Table(name = "questions")
@Getter
@Setter
@ToString
public class Question extends BaseEntity {
    @ManyToOne
    @NonNull
    private User author;

    @ManyToOne
    @NonNull
    private User recipient;

    @Column(name = "question")
    @NonNull
    private String question;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private AnswerEntity answerEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
