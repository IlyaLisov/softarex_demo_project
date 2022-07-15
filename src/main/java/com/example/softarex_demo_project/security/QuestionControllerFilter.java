package com.example.softarex_demo_project.security;

import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import com.example.softarex_demo_project.service.questions.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.example.softarex_demo_project.rest.questions.QuestionsRestUrls.BASE_URL;

@Component
public class QuestionControllerFilter implements Filter {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String questionId = ((HttpServletRequest) servletRequest).getRequestURI().substring(BASE_URL.length());
        String token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);
        String userIdFromToken = tokenProvider.getUserId(token);
        Optional<QuestionDto> question = questionService.getById(UUID.fromString(questionId));
        if (question.isPresent()) {
            if (!question.get().getAuthor().getId().toString().equals(userIdFromToken) && !question.get().getRecipient().getId().toString().equals(userIdFromToken)) {
                ((HttpServletResponse) servletResponse).setStatus(403);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
