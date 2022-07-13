package com.example.softarex_demo_project.rest.questions;

import com.example.softarex_demo_project.dto.question.CreateQuestionDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.question.AnswerType;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import com.example.softarex_demo_project.service.questions.QuestionService;
import com.example.softarex_demo_project.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public List<QuestionDto> getAllQuestions() {
        return questionService.getAll();
    }

    @GetMapping(value = idUrl)
    private ResponseEntity<QuestionDto> getQuestionById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(questionService.getById(id).get(), HttpStatus.OK);
    }

    @GetMapping(createUrl)
    private ResponseEntity createQuestion() {
        Map<String, Object> result = new HashMap<>();
        result.put("answerTypes", AnswerType.values());
        result.put("userEmails", userService.getAll().stream()
                .map(UserDto::getEmail)
                .collect(Collectors.toList()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(createUrl)
    private ResponseEntity<QuestionDto> doCreateQuestion(@RequestBody @Valid CreateQuestionDto questionDto, HttpServletRequest request) {
        String authorUsername = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        questionDto.setAuthorEmail(authorUsername);
        return ResponseEntity.ok(questionService.save(questionDto));
    }

    @GetMapping(userQuestionsUrl)
    private List<QuestionDto> userQuestions(@PathVariable Long id) {
        return questionService.getAllByRecipientId(id);
    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        Map<String, String> result = new HashMap<>();
        result.put("error", e.getMessage());
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
