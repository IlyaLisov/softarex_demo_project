package com.example.softarex_demo_project.model.exceptions.question;

/**
 * This exception is a custom question exception.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
    }

    public QuestionNotFoundException(String message) {
        super(message);
    }
}
