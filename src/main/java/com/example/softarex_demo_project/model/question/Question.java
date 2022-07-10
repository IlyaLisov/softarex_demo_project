package com.example.softarex_demo_project.model.question;

import com.example.softarex_demo_project.model.BaseEntity;
import com.example.softarex_demo_project.model.user.User;

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
public class Question extends BaseEntity {
    @ManyToOne
    private User author;

    @ManyToOne
    private User recipient;

    @Column(name = "question")
    private String question;

    @OneToOne
    private AnswerEntity answerEntity;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerEntity getAnswerEntity() {
        return answerEntity;
    }

    public void setAnswerEntity(AnswerEntity answerEntity) {
        this.answerEntity = answerEntity;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", created=" + created +
                ", author=" + author +
                ", recipient=" + recipient +
                ", question='" + question + '\'' +
                ", answerEntity=" + answerEntity +
                '}';
    }
}
