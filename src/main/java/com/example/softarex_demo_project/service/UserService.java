package com.example.softarex_demo_project.service;

import com.example.softarex_demo_project.model.User;

import java.util.List;
import java.util.Optional;

/**
 * This interface is a base interface for actions with users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface UserService {
    boolean register(User user);

    List<User> getAll();

    Optional<User> getByUsername(String username);

    Optional<User> getById(Long id);

    void delete(Long id);
}
