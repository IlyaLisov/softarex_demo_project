package com.example.softarex_demo_project.service;

import com.example.softarex_demo_project.model.Role;
import com.example.softarex_demo_project.model.Status;
import com.example.softarex_demo_project.model.User;
import com.example.softarex_demo_project.repository.RoleRepository;
import com.example.softarex_demo_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is an implementation of UserService.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
