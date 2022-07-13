package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.dto.question.CreateQuestionDto;
import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.model.Status;
import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;
import com.example.softarex_demo_project.model.question.AnswerEntity;
import com.example.softarex_demo_project.model.question.CheckboxAnswerEntity;
import com.example.softarex_demo_project.model.question.ComboboxAnswerEntity;
import com.example.softarex_demo_project.model.question.DateAnswerEntity;
import com.example.softarex_demo_project.model.question.MultiLineAnswerEntity;
import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.model.question.RadioButtonAnswerEntity;
import com.example.softarex_demo_project.model.question.SingleLineAnswerEntity;
import com.example.softarex_demo_project.repository.QuestionRepository;
import com.example.softarex_demo_project.service.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private UserService userService;

    @Override
    public List<QuestionDto> getAll() {
        List<Question> questions = questionRepository.findAll();
        log.info("IN QuestionService.getAll - {} questions were found.", questions.size());
        return questions.stream()
                .map(QuestionDto::fromQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllByRecipientId(Long id) {
        return questionRepository.findAllByRecipientId(id).stream()
                .map(QuestionDto::fromQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<QuestionDto> getById(Long id) throws QuestionNotFoundException {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            log.info("IN QuestionService.getById - Question {} was found.", question.get());
            return Optional.of(QuestionDto.fromQuestion(question.get()));
        } else {
            log.warn("IN QuestionService.getById - Question with id {} was not found.", id);
            throw new QuestionNotFoundException("Question " + id + " not found.");
        }
    }

    @Override
    public QuestionDto save(CreateQuestionDto questionDto) {
        Question question = new Question();
        question.setAuthor(userService.getByUsername(questionDto.getAuthorEmail()).get().toUser());
        question.setRecipient(userService.getByUsername(questionDto.getRecipientEmail()).get().toUser());
        question.setQuestion(questionDto.getQuestion());
        question.setCreated(new Date());
        question.setUpdated(new Date());
        question.setStatus(Status.ACTIVE);
        question.setAnswerEntity(resolveAnswerEntity(questionDto));
        log.info("IN QuestionService.save - Question {} was saved.", question);
        questionRepository.save(question);
        return QuestionDto.fromQuestion(question);
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
            case COMBOBOX:
                ComboboxAnswerEntity comboboxAnswerEntity = new ComboboxAnswerEntity();
                comboboxAnswerEntity.setOptions(questionDto.getOptions());
                answerEntity = comboboxAnswerEntity;
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
    public QuestionDto update(QuestionDto question) throws QuestionNotFoundException {
        Optional<Question> questionFromDatabase = questionRepository.findById(question.getId());
        if (questionFromDatabase.isPresent()) {
            questionFromDatabase.get().setAuthor(question.getAuthor().toUser());
            questionFromDatabase.get().setRecipient(question.getRecipient().toUser());
            questionFromDatabase.get().setQuestion(question.getQuestion());
            questionFromDatabase.get().setAnswerEntity(question.getAnswerEntity());
            questionFromDatabase.get().setUpdated(new Date());
            questionRepository.save(questionFromDatabase.get());
            log.info("IN QuestionService.update - Question {} was updated.", question);
            return QuestionDto.fromQuestion(questionFromDatabase.get());
        } else {
            log.warn("IN QuestionService.getById - Question {} was not updated.", question);
            throw new QuestionNotFoundException("Question " + question.getId() + " not found.");
        }
    }

    @Override
    public void delete(Long id) {
        if (questionRepository.findById(id).isPresent()) {
            log.info("IN QuestionService.delete - Question with id {} was deleted.", id);
            questionRepository.deleteById(id);
        } else {
            throw new QuestionNotFoundException("Question " + id + " not found.");
        }
    }
}
