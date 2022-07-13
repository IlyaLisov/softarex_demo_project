package com.example.softarex_demo_project.rest.users;

import com.example.softarex_demo_project.dto.UserDto;
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.softarex_demo_project.rest.users.UserRestUrls.baseUrl;

/**
 * This class is a controller for users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = baseUrl)
public class UserRestControllerV1 implements UserRestUrls {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = idUrl)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto result = UserDto.fromUser(user.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = editUrl)
    public ResponseEntity<UserDto> editUserById(@PathVariable(name = "id") Long id) {
        return getUserById(id);
    }

    @PutMapping(value = editUrl)
    public ResponseEntity<Object> doEditUserById(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @DeleteMapping(deleteUrl)
    public ResponseEntity doDelete(@PathVariable(name = "id") Long id) {
        Map<Object, Object> response = new HashMap<>();
        Optional<User> user = userService.getById(id);
        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        response.put("message", "User " + user.get().getUsername() + " was successfully deleted.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
