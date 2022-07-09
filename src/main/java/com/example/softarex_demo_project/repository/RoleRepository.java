package com.example.softarex_demo_project.repository;

import com.example.softarex_demo_project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface is a repository for accessing roles in database.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
