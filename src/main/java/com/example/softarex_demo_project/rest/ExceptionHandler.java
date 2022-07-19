package com.example.softarex_demo_project.rest;

import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;
import com.example.softarex_demo_project.model.exceptions.security.AccessDeniedException;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a controller for throwable exceptions.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@ControllerAdvice
@RestController
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionNotFoundException() {
        return "This question was not found.";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        return "You are not permitted to access this page.";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataNotValidException.class)
    public String handleDataNotValidException(DataNotValidException e) {
        return e.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException() {
        return "User with such email already exists.";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        return "This user was not found.";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public String handleBadCredentialsException() {
        return "Incorrect password or username.";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
