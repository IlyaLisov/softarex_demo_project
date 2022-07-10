package com.example.softarex_demo_project.rest.questions;

import com.example.softarex_demo_project.dto.QuestionDto;
import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.service.questions.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is a controller for questions.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/questions/")
public class QuestionsRestControllerV1 {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<Question> questions = questionService.getAll();
        List<QuestionDto> result = questions.stream()
                .map(QuestionDto::fromQuestion)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    private ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name = "id") Long id) {
        Optional<Question> question = questionService.getById(id);
        if (!question.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        QuestionDto result = QuestionDto.fromQuestion(question.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
