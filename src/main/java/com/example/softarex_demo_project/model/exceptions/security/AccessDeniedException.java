package com.example.softarex_demo_project.model.exceptions.security;

/**
 * This exception is a custom security exception.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
