package com.example.softarex_demo_project.service.users;

import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * This interface is a base interface for actions with users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface UserService {
    UserDto register(RegisterUserDto user) throws UserAlreadyExistsException;

    List<UserDto> getAll();

    UserDto getByUsername(String username) throws UserNotFoundException;

    UserDto getById(UUID id) throws UserNotFoundException;

    UserDto update(EditUserDto user) throws UserNotFoundException, DataNotValidException, UserAlreadyExistsException;

    void delete(UUID id) throws UserNotFoundException;
}
