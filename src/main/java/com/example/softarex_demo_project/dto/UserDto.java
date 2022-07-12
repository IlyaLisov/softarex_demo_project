package com.example.softarex_demo_project.dto;

import com.example.softarex_demo_project.model.user.Role;
import com.example.softarex_demo_project.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * This class is a DTO of User class for ROLE_USER users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<Role> roles;

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setRoles(roles);
        return user;
    }
}
