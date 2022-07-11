package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.model.question.Question;

import java.util.List;
import java.util.Optional;

/**
 * This interface is a base interface for actions with questions.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface QuestionService {
    List<Question> getAll();

    Optional<Question> getById(Long id);

    void save(Question question);

    boolean update(Question question);

    void delete(Long id);
}
