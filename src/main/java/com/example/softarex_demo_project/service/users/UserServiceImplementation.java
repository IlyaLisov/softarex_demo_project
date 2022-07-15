package com.example.softarex_demo_project.service.users;

import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.Status;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.exceptions.user.UserNotFoundException;
import com.example.softarex_demo_project.model.user.Role;
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.repository.RoleRepository;
import com.example.softarex_demo_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto register(RegisterUserDto registerUserDto) throws UserAlreadyExistsException {
        registerUserDto.setUsername(registerUserDto.getEmail());
        User userFromDatabase = userRepository.findByUsername(registerUserDto.getUsername());
        if (userFromDatabase == null) {
            Role roleUser = roleRepository.findByName("ROLE_USER");
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleUser);
            User user = registerUserDto.toUser();
            user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            user.setRoles(userRoles);
            user.setStatus(Status.ACTIVE);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            userRepository.save(user);
            log.info("IN register - User {} was registered.", registerUserDto);
            return modelMapper.map(userRepository.findByUsername(registerUserDto.getUsername()), UserDto.class);
        } else {
            log.warn("IN UserService.register - User {} was not registered - username already exists.", registerUserDto);
            throw new UserAlreadyExistsException("User with email " + registerUserDto.getEmail() + " already exists.");
        }
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        log.info("IN UserService.getAll - {} users were found.", users.size());
        return users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("IN UserService.getByUsername - User {} was not found.", username);
            throw new UserNotFoundException("User " + username + " not found.");
        } else {
            log.info("IN UserService.getByUsername - User {} was found.", username);
            return Optional.of(modelMapper.map(user, UserDto.class));
        }
    }

    @Override
    public Optional<UserDto> getById(UUID id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("IN UserService.getById - User {} was found.", user.get());
            return Optional.of(modelMapper.map(user.get(), UserDto.class));
        } else {
            log.warn("IN UserService.getById - User with id {} was not found.", id);
            throw new UserNotFoundException("User " + id + " not found.");
        }
    }

    @Override
    public UserDto update(EditUserDto editUserDto) throws UserNotFoundException, DataNotValidException, UserAlreadyExistsException {
        Optional<User> userFromDatabase = userRepository.findById(editUserDto.getId());
        if (userFromDatabase.isPresent()) {
            if (!passwordEncoder.matches(editUserDto.getPassword(), userFromDatabase.get().getPassword())) {
                throw new DataNotValidException("Current password is incorrect.");
            }
            if (!editUserDto.getEmail().equals(userFromDatabase.get().getEmail()) && getByUsername(editUserDto.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User with such email already exists.");
            }
            userFromDatabase.get().setFirstName(editUserDto.getFirstName());
            userFromDatabase.get().setLastName(editUserDto.getLastName());
            userFromDatabase.get().setEmail(editUserDto.getEmail());
            userFromDatabase.get().setUsername(editUserDto.getEmail());
            userFromDatabase.get().setPhoneNumber(editUserDto.getPhoneNumber());
            if (editUserDto.getNewPassword() != null && !editUserDto.getNewPassword().isEmpty()) {
                userFromDatabase.get().setPassword(passwordEncoder.encode(editUserDto.getNewPassword()));
            }
            userFromDatabase.get().setUpdated(new Date());
            userRepository.save(userFromDatabase.get());
            log.info("IN UserService.update - User {} was updated.", editUserDto);
            return modelMapper.map(userFromDatabase.get(), UserDto.class);
        } else {
            log.warn("IN UserService.update - User {} was not updated.", editUserDto);
            throw new UserAlreadyExistsException("User with email " + editUserDto.getEmail() + " already exists.");
        }
    }

    @Override
    public void delete(UUID id) throws UserNotFoundException {
        if (userRepository.findById(id).isPresent()) {
            log.info("IN UserService.delete - User with id {} was deleted.", id);
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User " + id + " not found.");
        }
    }
}
