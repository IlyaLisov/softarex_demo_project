package com.example.softarex_demo_project.security.jwt;

/**
 * This exception is a custom JWT exception.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
