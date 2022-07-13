package com.example.softarex_demo_project.rest.users;

import com.example.softarex_demo_project.dto.user.EditUserDto;
import com.example.softarex_demo_project.dto.user.UserDto;
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
import java.util.Map;

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

    @GetMapping(value = idUrl)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(userService.getById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = editUrl)
    public ResponseEntity<UserDto> editUserById(@PathVariable(name = "id") Long id) {
        return getUserById(id);
    }

    @PutMapping(value = editUrl)
    public ResponseEntity doEditUserById(@PathVariable(name = "id") Long id, @RequestBody @Valid EditUserDto editUserDto) {
        Map<Object, Object> response = new HashMap<>();
        editUserDto.setId(id);
        response.put("user", userService.update(editUserDto));
        response.put("message", "User was edited successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(deleteUrl)
    public ResponseEntity doDelete(@PathVariable(name = "id") Long id) {
        Map<Object, Object> response = new HashMap<>();
        userService.delete(id);
        response.put("message", "User was successfully deleted.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        Map<String, String> result = new HashMap<>();
        result.put("error", e.getMessage());
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
