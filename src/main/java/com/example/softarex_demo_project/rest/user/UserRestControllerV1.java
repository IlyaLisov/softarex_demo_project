package com.example.softarex_demo_project.rest.user;

import com.example.softarex_demo_project.dto.UserDto;
import com.example.softarex_demo_project.model.User;
import com.example.softarex_demo_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto result = UserDto.fromUser(user.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/edit")
    public ResponseEntity<UserDto> editUserById(@PathVariable(name = "id") Long id) {
        return getUserById(id);
    }

    @PutMapping(value = "{id}/edit")
    public ResponseEntity<Object> doEditUserById(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        user = Optional.of(user.get().clone());
        Map<Object, Object> response = new HashMap<>();
        try {
            if (request.getParameter("currentPassword") == null || request.getParameter("currentPassword").isEmpty()) {
                throw new Exception("You must input current password.");
            }
            if (!passwordEncoder.matches(request.getParameter("currentPassword"), user.get().getPassword())) {
                throw new Exception("Current password is incorrect.");
            }
            if (request.getParameter("email") == null || request.getParameter("email").isEmpty()) {
                throw new Exception("Email is missing.");
            }
            if (!request.getParameter("email").equals(user.get().getEmail()) && userService.getByUsername(request.getParameter("email")).isPresent()) {
                throw new Exception("User with such email already exists.");
            }
            user.get().setFirstName(request.getParameter("firstName"));
            user.get().setLastName(request.getParameter("lastName"));
            user.get().setEmail(request.getParameter("email"));
            user.get().setPhoneNumber(request.getParameter("phoneNumber"));
            if (request.getParameter("newPassword") != null && !request.getParameter("newPassword").isEmpty()) {
                user.get().setPassword(request.getParameter("newPassword"));
            }
            if (userService.update(user.get())) {
                response.put("message", "User was edited successfully.");
            } else {
                throw new Exception("Something went wrong during update.");
            }
            user = userService.getById(id);
            response.put("user", UserDto.fromUser(user.get()));
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("user", UserDto.fromUser(user.get()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
