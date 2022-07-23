package com.example.softarex_demo_project.model.question;

import com.example.softarex_demo_project.model.BaseEntity;
import com.example.softarex_demo_project.model.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class is a question class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
public class Question extends BaseEntity {
    @ManyToOne(cascade = CascadeType.REMOVE)
    @NonNull
    private User author;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @NonNull
    private User recipient;

    @Column(name = "question")
    @NonNull
    private String question;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private AnswerEntity answerEntity;
}
