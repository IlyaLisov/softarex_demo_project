package com.example.softarex_demo_project.rest.admin;

import com.example.softarex_demo_project.dto.AdminUserDto;
import com.example.softarex_demo_project.model.User;
import com.example.softarex_demo_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is a controller for admins.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping(value = "users")
    public ResponseEntity<List<AdminUserDto>> getUsers() {
        List<User> users = userService.getAll();
        List<AdminUserDto> result = users.stream()
                .map(AdminUserDto::fromUser)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdminUserDto result = AdminUserDto.fromUser(user.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
