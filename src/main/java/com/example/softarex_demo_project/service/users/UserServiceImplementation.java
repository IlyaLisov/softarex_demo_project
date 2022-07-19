package com.example.softarex_demo_project.service.users;

import com.example.softarex_demo_project.dto.mappers.RegisterUserMapper;
import com.example.softarex_demo_project.dto.mappers.UserMapper;
import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.Status;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class is an implementation of UserService.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Service
@Slf4j
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterUserDto registerUserDto) throws UserAlreadyExistsException, DataNotValidException {
        if (!Objects.equals(registerUserDto.getPassword(), registerUserDto.getPasswordConfirmation())) {
            throw new DataNotValidException("Passwords must be the same.");
        }
        Optional<User> userFromDatabase = userRepository.findByUsername(registerUserDto.getUsername());
        if (!userFromDatabase.isPresent()) {
            List<String> userRoles = new ArrayList<>();
            userRoles.add("ROLE_USER");
            User user = RegisterUserMapper.INSTANCE.registerUserDtoToUser(registerUserDto);
            user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            user.setRoles(userRoles);
            user.setStatus(Status.ACTIVE);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            userRepository.save(user);
            log.info("IN register - User {} was registered.", registerUserDto);
            return UserMapper.INSTANCE.userToUserDto(userRepository.findByUsername(registerUserDto.getUsername()).get());
        } else {
            log.warn("IN UserService.register - User {} was not registered - username already exists.", registerUserDto);
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        log.info("IN UserService.getAll - {} users were found.", users.size());
        return users.stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> {
                    log.info("IN UserService.getByUsername - User {} was found.", username);
                    return UserMapper.INSTANCE.userToUserDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("IN UserService.getByUsername - User {} was not found.", username);
                    return new UserNotFoundException();
                });
    }

    @Override
    public UserDto getById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> {
                    log.info("IN UserService.getById - User {} was found.", user);
                    return UserMapper.INSTANCE.userToUserDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("IN UserService.getById - User {} was not found.", id);
                    return new UserNotFoundException();
                });
    }

    @Override
    public UserDto update(EditUserDto editUserDto) throws UserNotFoundException, DataNotValidException, UserAlreadyExistsException {
        return userRepository.findById(editUserDto.getId())
                .map(user -> {
                    if (!passwordEncoder.matches(editUserDto.getPassword(), user.getPassword())) {
                        throw new DataNotValidException("Current password is incorrect.");
                    }
                    UserDto userWithNewEmail = null;
                    try {
                        userWithNewEmail = getByUsername(editUserDto.getUsername());
                    } catch (UserNotFoundException ignored) {
                    }
                    if (!editUserDto.getUsername().equals(user.getUsername()) && userWithNewEmail != null) {
                        throw new UserAlreadyExistsException();
                    }
                    user.setFirstName(editUserDto.getFirstName());
                    user.setLastName(editUserDto.getLastName());
                    user.setUsername(editUserDto.getUsername());
                    user.setPhoneNumber(editUserDto.getPhoneNumber());
                    if (editUserDto.getNewPassword() != null && !editUserDto.getNewPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(editUserDto.getNewPassword()));
                    }
                    user.setUpdated(new Date());
                    userRepository.save(user);
                    log.info("IN UserService.update - User {} was updated.", editUserDto);
                    return UserMapper.INSTANCE.userToUserDto(user);
                }).orElseThrow(() -> {
                    log.warn("IN UserService.update - User {} was not updated.", editUserDto);
                    return new UserAlreadyExistsException();
                });
    }

    @Override
    public void delete(UUID id) throws UserNotFoundException {
        userRepository.findById(id)
                .map(user -> {
                    log.info("IN UserService.delete - User with id {} was deleted.", id);
                    userRepository.deleteById(id);
                    return user;
                })
                .orElseThrow(() -> {
                    log.info("IN UserService.delete - User with id {} was not deleted.", id);
                    return new UserNotFoundException();
                });
    }
}
