package com.example.softarex_demo_project.security;

import com.example.softarex_demo_project.rest.users.UserRestUrls;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
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

@Component
@Slf4j
public class UserControllerFilter implements Filter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uuidFromRequest = ((HttpServletRequest) servletRequest).getRequestURI().substring(UserRestUrls.BASE_URL.length());
        String token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);
        String userIdFromToken = tokenProvider.getUserId(token);
        if (uuidFromRequest.startsWith(userIdFromToken)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.warn("User " + userIdFromToken + " was rejected from access to " + ((HttpServletRequest) servletRequest).getQueryString());
            ((HttpServletResponse) servletResponse).setStatus(403);
        }
    }
}