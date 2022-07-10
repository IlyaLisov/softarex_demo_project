package com.example.softarex_demo_project.rest.authentication;

import com.example.softarex_demo_project.dto.AuthenticationRequestDto;
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import com.example.softarex_demo_project.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * This class is a controller for authentication of users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public ResponseEntity login() {
        Map<Object, Object> response = new HashMap<>();
        response.put("email", "");
        response.put("password", "");
        return ResponseEntity.ok(response);
    }

    @PostMapping("login")
    public ResponseEntity doLogin(@RequestBody AuthenticationRequestDto requestDto) {
        Map<Object, Object> response = new HashMap<>();
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            Optional<User> user = userService.getByUsername(username);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("User with username: " + username + " not found.");
            }
            String token = jwtTokenProvider.createToken(username, user.get().getRoles());
            response.put("username", username);
            response.put("token", token);
        } catch (AuthenticationException e) {
            response.put("error", "Invalid username or password.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("register")
    public ResponseEntity register() {
        Map<Object, Object> response = new HashMap<>();
        response.put("email", "");
        response.put("password", "");
        response.put("confirmPassword", "");
        response.put("firstName", "");
        response.put("lastName", "");
        response.put("phoneNumber", "");
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity doRegister(HttpServletRequest request) {
        Map<Object, Object> response = new HashMap<>();
        User user = new User();
        try {
            if (request.getParameter("email") == null) {
                throw new Exception("Email is missing.");
            }
            if (request.getParameter("password") == null) {
                throw new Exception("Password is missing.");
            }
            if (request.getParameter("passwordConfirmation") == null) {
                throw new Exception("Password confirmation is missing.");
            }
            if (!Objects.equals(request.getParameter("password"), request.getParameter("passwordConfirmation"))) {
                throw new Exception("Passwords must be the same.");
            }
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setUsername(request.getParameter("email"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setPhoneNumber(request.getParameter("phoneNumber"));
            if(userService.register(user)) {
                response.put("message", "Successfully registered with username " + user.getUsername());
            } else {
                response.put("error", "User with such username already exists.");
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
