package com.example.softarex_demo_project.service.users;

import com.example.softarex_demo_project.dto.EditUserDto;
import com.example.softarex_demo_project.dto.RegisterUserDto;
import com.example.softarex_demo_project.dto.UserDto;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;

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

    List<UserDto> getAll();

    Optional<UserDto> getByUsername(String username) throws UserNotFoundException;

    Optional<UserDto> getById(Long id) throws UserNotFoundException;

    UserDto update(EditUserDto user) throws UserNotFoundException, DataNotValidException, UserAlreadyExistsException;

    void delete(Long id) throws UserNotFoundException;
}
