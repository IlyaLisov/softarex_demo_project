package com.example.softarex_demo_project.service.users;

import com.example.softarex_demo_project.dto.RegisterUserDto;
import com.example.softarex_demo_project.dto.UserDto;
import com.example.softarex_demo_project.model.Status;
import com.example.softarex_demo_project.model.exceptions.user.UserAlreadyExistsException;
import com.example.softarex_demo_project.model.user.Role;
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.repository.RoleRepository;
import com.example.softarex_demo_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            return UserDto.fromUser(userRepository.findByUsername(registerUserDto.getUsername()));
        } else {
            log.warn("IN UserService.register - User {} was not registered - username already exists.", registerUserDto);
            throw new UserAlreadyExistsException("User with email " + registerUserDto.getEmail() + " already exists.");
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("IN UserService.getAll - {} users were found.", users.size());
        return users;
    }

    public Optional<User> getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("IN UserService.getByUsername - User {} was not found.", username);
        } else {
            log.info("IN UserService.getByUsername - User {} was found.", username);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("IN UserService.getById - User {} was found.", user.get());
        } else {
            log.warn("IN UserService.getById - User with id {} was not found.", id);
        }
        return user;
    }

    public boolean update(User user) {
        Optional<User> userFromDatabase = userRepository.findById(user.getId());
        if (userFromDatabase.isPresent()) {
            userFromDatabase.get().setFirstName(user.getFirstName());
            userFromDatabase.get().setLastName(user.getLastName());
            userFromDatabase.get().setEmail(user.getEmail());
            userFromDatabase.get().setUsername(user.getEmail());
            userFromDatabase.get().setPhoneNumber(user.getPhoneNumber());
            //if password hasn`t changed OR CHANGED TO ITS HASH then it must be encoded
            if (!user.getPassword().equals(userFromDatabase.get().getPassword())) {
                userFromDatabase.get().setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userFromDatabase.get().setUpdated(new Date());
            userRepository.save(userFromDatabase.get());
            log.info("IN UserService.update - User {} was updated.", user);
            return true;
        }
        log.info("IN UserService.update - User {} was not updated.", user);
        return false;
    }

    @Override
    public void delete(Long id) {
        log.info("IN UserService.delete - User with id {} was deleted.", id);
        userRepository.deleteById(id);
    }
}
