package com.example.softarex_demo_project.rest.users;

import com.example.softarex_demo_project.dto.question.QuestionDto;
import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.service.questions.QuestionService;
import com.example.softarex_demo_project.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.softarex_demo_project.rest.users.UserRestUrls.BASE_URL;

/**
 * This class is a controller for users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@RestController
@RequestMapping(value = BASE_URL)
public class UserRestControllerV1 implements UserRestUrls {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping(ID_URL)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(userService.getById(id).get(), HttpStatus.OK);
    }

    @GetMapping(EDIT_URL)
    public ResponseEntity<UserDto> editUserById(@PathVariable(name = "id") UUID id) {
        return getUserById(id);
    }

    @PutMapping(EDIT_URL)
    public ResponseEntity doEditUserById(@PathVariable(name = "id") UUID id, @RequestBody @Valid EditUserDto editUserDto) {
        Map<Object, Object> response = new HashMap<>();
        editUserDto.setId(id);
        response.put("user", userService.update(editUserDto));
        response.put("message", "User was edited successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(ID_URL)
    public ResponseEntity doDelete(@PathVariable(name = "id") UUID id) {
        Map<Object, Object> response = new HashMap<>();
        userService.delete(id);
        response.put("message", "User was successfully deleted.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(FROM_QUESTIONS_URL)
    public List<QuestionDto> fromUserQuestions(@PathVariable UUID id) {
        return questionService.getAllByAuthorId(id);
    }

    @GetMapping(TO_QUESTIONS_URL)
    public List<QuestionDto> toUserQuestions(@PathVariable UUID id) {
        return questionService.getAllByRecipientId(id);
    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        Map<String, String> result = new HashMap<>();
        result.put("error", e.getMessage());
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
