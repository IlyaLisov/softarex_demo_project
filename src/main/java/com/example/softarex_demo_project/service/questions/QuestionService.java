package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.dto.QuestionDto;
import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * This interface is a base interface for actions with questions.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface QuestionService {
    List<QuestionDto> getAll();

    Optional<QuestionDto> getById(Long id) throws QuestionNotFoundException;

    void save(QuestionDto question);

    QuestionDto update(QuestionDto question) throws QuestionNotFoundException;

    void delete(Long id) throws QuestionNotFoundException;
}
