package com.example.softarex_demo_project.rest.questions;

import com.example.softarex_demo_project.dto.QuestionDto;
import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.service.questions.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.softarex_demo_project.rest.questions.QuestionsRestUrls.baseUrl;

/**
 * This class is a controller for questions.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = baseUrl)
public class QuestionsRestControllerV1 implements QuestionsRestUrls {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionService.getAll();
        return questions.stream()
                .map(QuestionDto::fromQuestion)
                .collect(Collectors.toList());
    }

    @GetMapping(value = idUrl)
    private ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name = "id") Long id) {
        Optional<Question> question = questionService.getById(id);
        if (!question.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        QuestionDto result = QuestionDto.fromQuestion(question.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        Map<String, String> result = new HashMap<>();
        result.put("error", e.getMessage());
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
