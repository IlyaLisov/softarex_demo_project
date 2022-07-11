package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<Question> getAll() {
        List<Question> questions = questionRepository.findAll();
        log.info("IN QuestionService.getAll - {} questions were found.", questions.size());
        return questions;
    }

    @Override
    public Optional<Question> getById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            log.info("IN QuestionService.getById - Question {} was found.", question.get());
        } else {
            log.warn("IN QuestionService.getById - Question with id {} was not found.", id);
        }
        return question;
    }

    @Override
    public void save(Question question) {
        log.info("IN QuestionService.save - Question {} was saved.", question);
        questionRepository.save(question);
    }

    @Override
    public boolean update(Question question) {
        Optional<Question> questionFromDatabase = questionRepository.findById(question.getId());
        if (questionFromDatabase.isPresent()) {
            questionFromDatabase.get().setAuthor(question.getAuthor());
            questionFromDatabase.get().setRecipient(question.getRecipient());
            questionFromDatabase.get().setQuestion(question.getQuestion());
            questionFromDatabase.get().setAnswerEntity(question.getAnswerEntity());
            questionFromDatabase.get().setUpdated(new Date());
            questionRepository.save(questionFromDatabase.get());
            log.info("IN QuestionService.update - Question {} was updated.", question);
            return true;
        }
        log.warn("IN QuestionService.getById - Question {} was not updated.", question);
        return false;
    }

    @Override
    public void delete(Long id) {
        log.info("IN QuestionService.delete - Question with id {} was deleted.", id);
        questionRepository.deleteById(id);
    }
}
