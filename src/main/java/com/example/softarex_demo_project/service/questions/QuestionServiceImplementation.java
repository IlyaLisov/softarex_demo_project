package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.dto.mappers.QuestionMapperImplementation;
import com.example.softarex_demo_project.dto.mappers.UserMapper;
import com.example.softarex_demo_project.dto.question.AnswerQuestionDto;
import com.example.softarex_demo_project.dto.question.CreateQuestionDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.model.Status;
import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;
import com.example.softarex_demo_project.model.question.AnswerEntity;
import com.example.softarex_demo_project.model.question.CheckboxAnswerEntity;
import com.example.softarex_demo_project.model.question.DateAnswerEntity;
import com.example.softarex_demo_project.model.question.MultiLineAnswerEntity;
import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.model.question.RadioButtonAnswerEntity;
import com.example.softarex_demo_project.model.question.SingleLineAnswerEntity;
import com.example.softarex_demo_project.repository.AnswerRepository;
import com.example.softarex_demo_project.repository.QuestionRepository;
import com.example.softarex_demo_project.service.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class is an implementation of QuestionService.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Service
@Slf4j
public class QuestionServiceImplementation implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionMapperImplementation questionMapperImplementation;

    @Override
    public List<QuestionDto> getAll() {
        return questionRepository.findAll().stream()
                .map(questionMapperImplementation::questionToQuestionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllByRecipientId(UUID id) {
        return questionRepository.findAllByRecipientId(id).stream()
                .map(questionMapperImplementation::questionToQuestionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllByAuthorId(UUID id) {
        return questionRepository.findAllByAuthorId(id).stream()
                .map(questionMapperImplementation::questionToQuestionDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDto getById(UUID id) throws QuestionNotFoundException {
        return questionRepository.findById(id)
                .map((question) -> {
                    log.info("IN QuestionService.getById - Question {} was found.", question);
                    return questionMapperImplementation.questionToQuestionDto(question);
                })
                .orElseThrow(() -> {
                    log.warn("IN QuestionService.getById - Question with id {} was not found.", id);
                    return new QuestionNotFoundException();
                });
    }

    @Override
    public QuestionDto save(CreateQuestionDto questionDto) {
        Question question = new Question();
        question.setAuthor(UserMapper.INSTANCE.userDtoToUser(userService.getByUsername(questionDto.getAuthorEmail())));
        question.setRecipient(UserMapper.INSTANCE.userDtoToUser(userService.getByUsername(questionDto.getRecipientEmail())));
        question.setQuestion(questionDto.getQuestion());
        question.setCreated(new Date());
        question.setUpdated(new Date());
        question.setStatus(Status.ACTIVE);
        question.setAnswerEntity(resolveAnswerEntity(questionDto));
        log.info("IN QuestionService.save - Question {} was saved.", question);
        questionRepository.save(question);
        return questionMapperImplementation.questionToQuestionDto(question);
    }

    private AnswerEntity resolveAnswerEntity(CreateQuestionDto questionDto) {
        AnswerEntity answerEntity;
        switch (questionDto.getAnswerType()) {
            case SINGLE_LINE_TEXT:
                answerEntity = new SingleLineAnswerEntity();
                break;
            case MULTILINE_TEXT:
                answerEntity = new MultiLineAnswerEntity();
                break;
            case RADIO_BUTTON:
                RadioButtonAnswerEntity radioButtonAnswerEntity = new RadioButtonAnswerEntity();
                radioButtonAnswerEntity.setOptions(questionDto.getOptions());
                answerEntity = radioButtonAnswerEntity;
                break;
            case CHECKBOX:
                CheckboxAnswerEntity checkboxAnswerEntity = new CheckboxAnswerEntity();
                checkboxAnswerEntity.setOptions(questionDto.getOptions());
                answerEntity = checkboxAnswerEntity;
                break;
            case DATE:
                answerEntity = new DateAnswerEntity();
                break;
            default:
                return null;
        }
        answerEntity.setCreated(new Date());
        answerEntity.setUpdated(new Date());
        answerEntity.setStatus(Status.ACTIVE);
        return answerEntity;
    }

    @Override
    public QuestionDto update(CreateQuestionDto questionDto) throws QuestionNotFoundException {
        return questionRepository.findById(questionDto.getId())
                .map((question) -> {
                    AnswerEntity answerEntity = question.getAnswerEntity();
                    answerRepository.delete(answerEntity);
                    question.setAnswerEntity(resolveAnswerEntity(questionDto));
                    question.setRecipient(UserMapper.INSTANCE.userDtoToUser(userService.getByUsername(questionDto.getRecipientEmail())));
                    question.setQuestion(question.getQuestion());
                    question.setUpdated(new Date());
                    questionRepository.save(question);
                    log.info("IN QuestionService.update - Question {} was updated.", question);
                    return questionMapperImplementation.questionToQuestionDto(question);
                })
                .orElseThrow(() -> {
                    log.warn("IN QuestionService.getById - Question {} was not updated.", questionDto);
                    return new QuestionNotFoundException();
                });
    }

    @Override
    public QuestionDto answerQuestion(AnswerQuestionDto answerQuestionDto) throws QuestionNotFoundException {
        return questionRepository.findById(answerQuestionDto.getQuestionId())
                .map((question) -> {
                    AnswerEntity answerEntity = question.getAnswerEntity();
                    switch (answerEntity.getAnswerType()) {
                        case SINGLE_LINE_TEXT:
                            ((SingleLineAnswerEntity) answerEntity).setAnswer(answerQuestionDto.getStringAnswer());
                            break;
                        case MULTILINE_TEXT:
                            ((MultiLineAnswerEntity) answerEntity).setAnswer(answerQuestionDto.getStringAnswer());
                            break;
                        case CHECKBOX:
                            ((CheckboxAnswerEntity) answerEntity).setAnswer(answerQuestionDto.getAnswerList());
                            break;
                        case RADIO_BUTTON:
                            RadioButtonAnswerEntity radioButtonAnswerEntity = (RadioButtonAnswerEntity) answerEntity;
                            radioButtonAnswerEntity.setAnswer(radioButtonAnswerEntity.getOptions().get(answerQuestionDto.getOptionIndex()));
                            break;
                        case DATE:
                            ((DateAnswerEntity) answerEntity).setAnswer(answerQuestionDto.getDate());
                            break;
                    }
                    question.setAnswerEntity(answerEntity);
                    questionRepository.save(question);
                    log.info("IN QuestionService.answerQuestion - Question {} was answered.", question);
                    return questionMapperImplementation.questionToQuestionDto(question);
                })
                .orElseThrow(() -> {
                    log.warn("IN QuestionService.answerQuestion - Question {} was not answered.", answerQuestionDto);
                    return new QuestionNotFoundException();
                });
    }

    @Override
    public void delete(UUID id) throws QuestionNotFoundException {
        questionRepository.findById(id)
                .map((user) -> {
                    log.info("IN QuestionService.delete - Question with id {} was deleted.", id);
                    questionRepository.deleteById(id);
                    return user;
                })
                .orElseThrow(() -> {
                    log.warn("IN QuestionService.delete - Question with id {} was not found.", id);
                    return new QuestionNotFoundException("Question " + id + " not found.");
                });
    }
}
