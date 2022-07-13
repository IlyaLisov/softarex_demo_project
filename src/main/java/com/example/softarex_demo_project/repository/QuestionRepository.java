package com.example.softarex_demo_project.repository;

import com.example.softarex_demo_project.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This interface is a repository for accessing questions in database.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByRecipientId(Long id);
}
