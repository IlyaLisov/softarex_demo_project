package com.example.softarex_demo_project.security;

import com.example.softarex_demo_project.dto.mappers.UserMapper;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;
import com.example.softarex_demo_project.security.jwt.JwtUserFactory;
import com.example.softarex_demo_project.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * This class is a service for JWT users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        UserDto user = userService.getByUsername(username);
        return JwtUserFactory.create(UserMapper.INSTANCE.userDtoToUser(user));
    }
}
