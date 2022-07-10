package com.example.softarex_demo_project.service.questions;

import com.example.softarex_demo_project.model.question.Question;
import com.example.softarex_demo_project.repository.QuestionRepository;
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
public class QuestionServiceImplementation implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public void save(Question question) {
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
            return true;
        }
        return false;
    }

    @Override
    public void delete(Question question) {
        questionRepository.deleteById(question.getId());
    }
}
