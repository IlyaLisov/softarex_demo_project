package com.example.softarex_demo_project.rest.authentication;

import com.example.softarex_demo_project.dto.AuthenticationDto;
import com.example.softarex_demo_project.dto.AuthenticationRequestDto;
import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.exceptions.user.DataNotValidException;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import com.example.softarex_demo_project.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.BASE_URL;

/**
 * This class is a controller for authentication of users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = BASE_URL)
public class AuthenticationRestControllerV1 implements AuthenticationRestUrls {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping(LOGIN_ULR)
    public ResponseEntity<AuthenticationDto> doLogin(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        Optional<UserDto> user = userService.getByUsername(username);
        authenticationDto.setUsername(username);
        authenticationDto.setToken(jwtTokenProvider.createToken(username, user.get().getRoles()));
        return ResponseEntity.ok(authenticationDto);
    }

    @PostMapping(REGISTER_URL)
    public ResponseEntity<UserDto> doRegister(@RequestBody @Valid RegisterUserDto registerUserDto) throws DataNotValidException {
        if (!Objects.equals(registerUserDto.getPassword(), registerUserDto.getPasswordConfirmation())) {
            throw new DataNotValidException("Passwords must be the same.");
        }
        UserDto userDto = userService.register(registerUserDto);
        return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        Map<String, String> result = new HashMap<>();
        result.put("error", e.getMessage());
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
