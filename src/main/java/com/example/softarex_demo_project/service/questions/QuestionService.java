package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.dto.question.AnswerQuestionDto;
import com.example.softarex_demo_project.dto.question.CreateQuestionDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This interface is a base interface for actions with questions.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface QuestionService {
    List<QuestionDto> getAll();

    List<QuestionDto> getAllByRecipientId(UUID id);

    List<QuestionDto> getAllByAuthorId(UUID id);

    Optional<QuestionDto> getById(UUID id) throws QuestionNotFoundException;

    QuestionDto save(CreateQuestionDto question);

    QuestionDto update(CreateQuestionDto question) throws QuestionNotFoundException;

    QuestionDto answerQuestion(AnswerQuestionDto answerQuestionDto) throws QuestionNotFoundException;

    void delete(UUID id) throws QuestionNotFoundException;
}
