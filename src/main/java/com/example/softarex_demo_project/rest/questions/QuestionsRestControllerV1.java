package com.example.softarex_demo_project.rest.questions;

import com.example.softarex_demo_project.dto.question.AnswerQuestionDto;
import com.example.softarex_demo_project.dto.question.CreateQuestionDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.model.question.AnswerType;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import com.example.softarex_demo_project.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.softarex_demo_project.rest.questions.QuestionsRestUrls.ANSWER_TYPES;
import static com.example.softarex_demo_project.rest.questions.QuestionsRestUrls.ANSWER_URL;
import static com.example.softarex_demo_project.rest.questions.QuestionsRestUrls.BASE_URL;
import static com.example.softarex_demo_project.rest.questions.QuestionsRestUrls.CREATE_URL;
import static com.example.softarex_demo_project.rest.questions.QuestionsRestUrls.ID_URL;

/**
 * This class is a controller for questions.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = BASE_URL)
public class QuestionsRestControllerV1 {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public List<QuestionDto> getAllQuestions() {
        return securityService.getAllQuestions();
    }

    @GetMapping(ID_URL)
    public QuestionDto getQuestionById(@PathVariable(name = "id") UUID id, HttpServletRequest request) {
        return securityService.getQuestionById(id, request);
    }

    @PutMapping(ID_URL)
    public QuestionDto editQuestion(@PathVariable UUID id, @RequestBody @Valid CreateQuestionDto createQuestionDto,
                                    HttpServletRequest request) {
        createQuestionDto.setId(id);
        return securityService.updateQuestion(createQuestionDto, request);
    }

    @PostMapping(CREATE_URL)
    public QuestionDto createQuestion(@RequestBody @Valid CreateQuestionDto questionDto, HttpServletRequest request) {
        String authorUsername = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request));
        questionDto.setAuthorEmail(authorUsername);
        return securityService.saveQuestion(questionDto);
    }

    @PostMapping(ANSWER_URL)
    public QuestionDto answerQuestion(@PathVariable UUID id, @RequestBody @Valid AnswerQuestionDto answerQuestionDto,
                                      HttpServletRequest request) {
        answerQuestionDto.setQuestionId(id);
        return securityService.answerQuestion(answerQuestionDto, request);
    }

    @PutMapping(ANSWER_URL)
    public QuestionDto editAnswerQuestion(@PathVariable UUID id, @RequestBody @Valid AnswerQuestionDto answerQuestionDto,
                                          HttpServletRequest request) {
        answerQuestionDto.setQuestionId(id);
        return securityService.answerQuestion(answerQuestionDto, request);
    }

    @DeleteMapping(ID_URL)
    public void deleteQuestion(@PathVariable UUID id, HttpServletRequest request) {
        securityService.deleteQuestion(id, request);
    }

    @GetMapping(ANSWER_TYPES)
    public List<String> getAnswerTypes() {
        return Arrays.stream(AnswerType.values())
                .map(AnswerType::toString)
                .collect(Collectors.toList());
    }
}
