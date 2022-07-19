package com.example.softarex_demo_project.rest.users;

import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.softarex_demo_project.rest.users.UserRestUrls.BASE_URL;
import static com.example.softarex_demo_project.rest.users.UserRestUrls.FROM_QUESTIONS_URL;
import static com.example.softarex_demo_project.rest.users.UserRestUrls.ID_URL;
import static com.example.softarex_demo_project.rest.users.UserRestUrls.TO_QUESTIONS_URL;
import static com.example.softarex_demo_project.rest.users.UserRestUrls.USER_EMAILS;

/**
 * This class is a controller for users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = BASE_URL)
public class UserRestControllerV1 {
    @Autowired
    private SecurityService securityService;

    @GetMapping(ID_URL)
    public UserDto getUserById(@PathVariable(name = "id") UUID id, HttpServletRequest request) {
        return securityService.getUserById(id, request);
    }

    @PutMapping(ID_URL)
    public UserDto editUserById(@PathVariable(name = "id") UUID id, @RequestBody @Valid EditUserDto editUserDto,
                                HttpServletRequest request) {
        editUserDto.setId(id);
        return securityService.updateUser(editUserDto, request);
    }

    @DeleteMapping(ID_URL)
    public void deleteUserById(@PathVariable(name = "id") UUID id, HttpServletRequest request) {
        securityService.deleteUser(id, request);
    }

    @GetMapping(FROM_QUESTIONS_URL)
    public List<QuestionDto> fromUserQuestions(@PathVariable UUID id, HttpServletRequest request) {
        return securityService.getAllQuestionsByAuthorId(id, request);
    }

    @GetMapping(TO_QUESTIONS_URL)
    public List<QuestionDto> toUserQuestions(@PathVariable UUID id, HttpServletRequest request) {
        return securityService.getAllQuestionsByRecipientId(id, request);
    }

    @GetMapping(USER_EMAILS)
    public List<String> getUserEmail() {
        return securityService.getAllUsers().stream()
                .map(UserDto::getUsername)
                .collect(Collectors.toList());
    }

}
