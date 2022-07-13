package com.example.softarex_demo_project.service.users;

import com.example.softarex_demo_project.dto.RegisterUserDto;
import com.example.softarex_demo_project.dto.UserDto;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * This interface is a base interface for actions with users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface UserService {
    UserDto register(RegisterUserDto user) throws UserAlreadyExistsException;

    List<User> getAll();

    Optional<User> getByUsername(String username);

    Optional<User> getById(Long id);

    boolean update(User user);

    void delete(Long id);
}
