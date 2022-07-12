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
 * This class is a class for combobox answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class ComboboxAnswerEntity extends AnswerEntity {
    @Column(name = "combobox_options")
    @ElementCollection
    private List<String> options;

    @Column(name = "combobox_answer")
    @ElementCollection
    private List<String> answer;

    public ComboboxAnswerEntity() {
        this.options = new ArrayList<>();
        this.answer = new ArrayList<>();
        this.answerType = AnswerType.COMBOBOX;
    }

    public ComboboxAnswerEntity(List<String> options) {
        this.options = options;
        this.answer = new ArrayList<>();
        this.answerType = AnswerType.COMBOBOX;
    }

    public ComboboxAnswerEntity(List<String> options, List<String> answer) {
        this.options = options;
        this.answer = answer;
        this.answerType = AnswerType.COMBOBOX;
    }

    @Override
    public Object getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ComboboxAnswerEntity that = (ComboboxAnswerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
