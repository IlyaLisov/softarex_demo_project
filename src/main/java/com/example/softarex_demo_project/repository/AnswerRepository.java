package com.example.softarex_demo_project.repository;

import com.example.softarex_demo_project.model.question.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * This interface is a repository for accessing answers in database.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {
}
