package com.example.softarex_demo_project.model.exceptions.user;

/**
 * This exception is a custom user exception.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public class DataNotValidException extends RuntimeException {
    public DataNotValidException() {
    }

    public DataNotValidException(String message) {
        super(message);
    }
}
