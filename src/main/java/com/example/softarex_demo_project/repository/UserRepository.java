package com.example.softarex_demo_project.repository;

import com.example.softarex_demo_project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface is a repository for accessing users in database.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
