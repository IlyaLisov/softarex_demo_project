package com.example.softarex_demo_project.model.exceptions.user;

/**
 * This exception is a custom user exception.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
