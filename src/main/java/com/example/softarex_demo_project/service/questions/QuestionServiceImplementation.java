package com.example.softarex_demo_project.service.questions;

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
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.repository.AnswerRepository;
import com.example.softarex_demo_project.repository.QuestionRepository;
import com.example.softarex_demo_project.service.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private ModelMapper modelMapper;

    @Override
    public List<QuestionDto> getAll() {
        List<Question> questions = questionRepository.findAll();
        log.info("IN QuestionService.getAll - {} questions were found.", questions.size());
        return questions.stream()
                .map(q -> modelMapper.map(q, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllByRecipientId(UUID id) {
        return questionRepository.findAllByRecipientId(id).stream()
                .map(q -> modelMapper.map(q, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllByAuthorId(UUID id) {
        return questionRepository.findAllByAuthorId(id).stream()
                .map(q -> modelMapper.map(q, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<QuestionDto> getById(UUID id) throws QuestionNotFoundException {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            log.info("IN QuestionService.getById - Question {} was found.", question.get());
            return Optional.of(modelMapper.map(question.get(), QuestionDto.class));
        } else {
            log.warn("IN QuestionService.getById - Question with id {} was not found.", id);
            throw new QuestionNotFoundException("Question " + id + " not found.");
        }
    }

    @Override
    public QuestionDto save(CreateQuestionDto questionDto) {
        Question question = new Question();
        question.setAuthor(modelMapper.map(userService.getByUsername(questionDto.getAuthorEmail()).get(), User.class));
        question.setRecipient(modelMapper.map(userService.getByUsername(questionDto.getRecipientEmail()).get(), User.class));
        question.setQuestion(questionDto.getQuestion());
        question.setCreated(new Date());
        question.setUpdated(new Date());
        question.setStatus(Status.ACTIVE);
        question.setAnswerEntity(resolveAnswerEntity(questionDto));
        log.info("IN QuestionService.save - Question {} was saved.", question);
        questionRepository.save(question);
        return modelMapper.map(question, QuestionDto.class);
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
    public QuestionDto update(CreateQuestionDto question) throws QuestionNotFoundException {
        Optional<Question> questionFromDatabase = questionRepository.findById(question.getId());
        if (questionFromDatabase.isPresent()) {
            AnswerEntity answerEntity = questionFromDatabase.get().getAnswerEntity();
            answerRepository.delete(answerEntity);
            questionFromDatabase.get().setAnswerEntity(resolveAnswerEntity(question));
            questionFromDatabase.get().setRecipient(modelMapper.map(userService.getByUsername(question.getRecipientEmail()).get(), User.class));
            questionFromDatabase.get().setQuestion(question.getQuestion());
            questionFromDatabase.get().setUpdated(new Date());
            questionRepository.save(questionFromDatabase.get());
            log.info("IN QuestionService.update - Question {} was updated.", question);
            return modelMapper.map(questionFromDatabase.get(), QuestionDto.class);
        } else {
            log.warn("IN QuestionService.getById - Question {} was not updated.", question);
            throw new QuestionNotFoundException("Question " + question.getId() + " not found.");
        }
    }

    @Override
    public QuestionDto answerQuestion(AnswerQuestionDto answerQuestionDto) throws QuestionNotFoundException {
        Optional<Question> question = questionRepository.findById(answerQuestionDto.getQuestionId());
        if (question.isPresent()) {
            AnswerEntity answerEntity = question.get().getAnswerEntity();
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
            question.get().setAnswerEntity(answerEntity);
            questionRepository.save(question.get());
            log.info("IN QuestionService.answerQuestion - Question {} was answered.", question);
            return modelMapper.map(question.get(), QuestionDto.class);
        } else {
            log.warn("IN QuestionService.answerQuestion - Question {} was answered.", question);
            throw new QuestionNotFoundException("Question " + answerQuestionDto.getQuestionId() + " not found.");
        }
    }

    @Override
    public void delete(UUID id) {
        if (questionRepository.findById(id).isPresent()) {
            log.info("IN QuestionService.delete - Question with id {} was deleted.", id);
            questionRepository.deleteById(id);
        } else {
            log.warn("IN QuestionService.delete - Question with id {} was not found.", id);
            throw new QuestionNotFoundException("Question " + id + " not found.");
        }
    }
}
