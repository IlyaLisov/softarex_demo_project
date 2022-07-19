package com.example.softarex_demo_project.model.question;

import com.example.softarex_demo_project.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class is a base class for answers.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Table(name = "answers")
@Entity
@Data
@NoArgsConstructor
public abstract class AnswerEntity extends BaseEntity {
    public abstract Object getAnswer();

    public abstract AnswerType getAnswerType();
}
