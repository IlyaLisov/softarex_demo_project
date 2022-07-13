package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.dto.QuestionDto;
import com.example.softarex_demo_project.model.exceptions.question.QuestionNotFoundException;
import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.repository.QuestionRepository;
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

    @Override
    public List<QuestionDto> getAll() {
        List<Question> questions = questionRepository.findAll();
        log.info("IN QuestionService.getAll - {} questions were found.", questions.size());
        return questions.stream()
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
    public void save(QuestionDto question) {
        log.info("IN QuestionService.save - Question {} was saved.", question);
        questionRepository.save(question.toQuestion());
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
