package com.example.softarex_demo_project.rest.authentication;

import com.example.softarex_demo_project.dto.AuthenticationDto;
import com.example.softarex_demo_project.dto.AuthenticationRequestDto;
import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import com.example.softarex_demo_project.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.BASE_URL;
import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.LOGIN_ULR;
import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.REGISTER_URL;

/**
 * This class is a controller for authentication of users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = BASE_URL)
public class AuthenticationRestControllerV1 {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SecurityService securityService;

    @PostMapping(LOGIN_ULR)
    public AuthenticationDto login(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        UserDto user = securityService.getByUsername(username);
        authenticationDto.setUsername(username);
        authenticationDto.setToken(jwtTokenProvider.createToken(user.getId(), username, user.getRoles()));
        authenticationDto.setUserId(user.getId());
        return authenticationDto;
    }

    @PostMapping(REGISTER_URL)
    public UserDto register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return securityService.register(registerUserDto);
    }
}
