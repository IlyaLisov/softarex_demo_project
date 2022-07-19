package com.example.softarex_demo_project.service.security;

import com.example.softarex_demo_project.dto.question.AnswerQuestionDto;
import com.example.softarex_demo_project.dto.question.CreateQuestionDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;
import com.example.softarex_demo_project.model.exceptions.security.AccessDeniedException;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import com.example.softarex_demo_project.service.questions.QuestionService;
import com.example.softarex_demo_project.service.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * This class is an implementation of SecurityService.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Service
@Slf4j
public class SecurityServiceImplementation implements SecurityService {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public UserDto register(RegisterUserDto user) throws UserAlreadyExistsException {
        return userService.register(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @Override
    public UserDto getByUsername(String username) throws UserNotFoundException {
        return userService.getByUsername(username);
    }

    @Override
    public UserDto getUserById(UUID id, HttpServletRequest request) throws UserNotFoundException {
        if (validateUserAccess(id, request)) {
            return userService.getById(id);
        } else {
            throw new AccessDeniedException();
        }
    }

    private boolean validateUserAccess(UUID userId, HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        String userIdFromToken = tokenProvider.getUserId(token);
        return userId.toString().equals(userIdFromToken);
    }

    @Override
    public UserDto updateUser(EditUserDto user, HttpServletRequest request) throws UserNotFoundException, DataNotValidException, UserAlreadyExistsException {
        if (validateUserAccess(user.getId(), request)) {
            return userService.update(user);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteUser(UUID id, HttpServletRequest request) throws UserNotFoundException {
        if (validateUserAccess(id, request)) {
            userService.delete(id);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionService.getAll();
    }

    @Override
    public List<QuestionDto> getAllQuestionsByRecipientId(UUID id, HttpServletRequest request) {
        if (validateUserAccess(id, request)) {
            return questionService.getAllByRecipientId(id);
        } else {
            throw new AccessDeniedException();
        }
    }

    private boolean validateQuestionAccess(UUID questionId, HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        String userIdFromToken = tokenProvider.getUserId(token);
        QuestionDto question = questionService.getById(questionId);
        return question.getAuthor().getId().toString().equals(userIdFromToken) || question.getRecipient().getId().toString().equals(userIdFromToken);
    }

    @Override
    public List<QuestionDto> getAllQuestionsByAuthorId(UUID id, HttpServletRequest request) {
        if (validateUserAccess(id, request)) {
            return questionService.getAllByAuthorId(id);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public QuestionDto getQuestionById(UUID id, HttpServletRequest request) throws QuestionNotFoundException {
        if (validateQuestionAccess(id, request)) {
            return questionService.getById(id);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public QuestionDto saveQuestion(CreateQuestionDto question) {
        return questionService.save(question);
    }

    @Override
    public QuestionDto updateQuestion(CreateQuestionDto question, HttpServletRequest request) throws QuestionNotFoundException {
        if (validateQuestionAccess(question.getId(), request)) {
            return questionService.update(question);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public QuestionDto answerQuestion(AnswerQuestionDto answerQuestionDto, HttpServletRequest request) throws QuestionNotFoundException {
        if (validateQuestionAccess(answerQuestionDto.getQuestionId(), request)) {
            return questionService.answerQuestion(answerQuestionDto);
        } else {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void deleteQuestion(UUID id, HttpServletRequest request) throws QuestionNotFoundException {
        if (validateQuestionAccess(id, request)) {
            questionService.delete(id);
        } else {
            throw new AccessDeniedException();
        }
    }
}
