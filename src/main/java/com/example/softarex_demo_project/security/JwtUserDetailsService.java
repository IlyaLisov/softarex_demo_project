package com.example.softarex_demo_project.security;

import com.example.softarex_demo_project.dto.UserDto;
import com.example.softarex_demo_project.security.jwt.JwtUserFactory;
import com.example.softarex_demo_project.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDto> user = userService.getByUsername(username);
        return JwtUserFactory.create(user.get().toUser());
    }
}
