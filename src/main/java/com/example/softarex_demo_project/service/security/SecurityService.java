package com.example.softarex_demo_project.service.security;

import com.example.softarex_demo_project.dto.question.AnswerQuestionDto;
import com.example.softarex_demo_project.dto.question.CreateQuestionDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * This interface is a base interface for actions with users and questions with filter.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface SecurityService {
    UserDto register(RegisterUserDto user) throws UserAlreadyExistsException;

    boolean confirmUserPassword(UUID id, String password, HttpServletRequest request);

    List<UserDto> getAllUsers();

    UserDto getByUsername(String username) throws UserNotFoundException;

    UserDto getUserById(UUID id, HttpServletRequest request) throws UserNotFoundException;

    UserDto updateUser(EditUserDto user, HttpServletRequest request) throws UserNotFoundException, DataNotValidException, UserAlreadyExistsException;

    void deleteUser(UUID id, HttpServletRequest request) throws UserNotFoundException;

    List<QuestionDto> getAllQuestions();

    List<QuestionDto> getAllQuestionsByRecipientId(UUID id, HttpServletRequest request);

    List<QuestionDto> getAllQuestionsByAuthorId(UUID id, HttpServletRequest request);

    QuestionDto getQuestionById(UUID id, HttpServletRequest request) throws QuestionNotFoundException;

    QuestionDto saveQuestion(CreateQuestionDto question);

    QuestionDto updateQuestion(CreateQuestionDto question, HttpServletRequest request) throws QuestionNotFoundException;

    QuestionDto answerQuestion(AnswerQuestionDto answerQuestionDto, HttpServletRequest request) throws QuestionNotFoundException;

    void deleteQuestion(UUID id, HttpServletRequest request) throws QuestionNotFoundException;
}
