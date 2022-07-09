package com.example.softarex_demo_project.rest.user;

import com.example.softarex_demo_project.dto.UserDto;
import com.example.softarex_demo_project.model.User;
import com.example.softarex_demo_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * This class is a controller for users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    @Autowired
    private UserService userService;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto result = UserDto.fromUser(user.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
