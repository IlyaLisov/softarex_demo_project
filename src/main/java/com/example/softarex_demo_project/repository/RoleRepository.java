package com.example.softarex_demo_project.repository;

import com.example.softarex_demo_project.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * This interface is a repository for accessing roles in database.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
