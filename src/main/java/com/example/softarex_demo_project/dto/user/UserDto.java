package com.example.softarex_demo_project.dto.user;

import com.example.softarex_demo_project.model.user.Role;
import com.example.softarex_demo_project.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
    protected Long id;
    protected String username;

    @Length(max = 255, message = "Max length is {max}")
    protected String firstName;

    @Length(max = 255, message = "Max length is {max}")
    protected String lastName;

    @NotNull(message = "Email is missing.")
    @Length(min = 4, max = 255, message = "Length must be in {min} - {max}")
    @Email(message = "You must input email.")
    protected String email;

    @Length(min = 5, max = 16, message = "Length must be in {min} - {max}")
    protected String phoneNumber;

    @NotNull(message = "Password is missing.")
    @Length(min = 4, max = 255, message = "Length must be in {min} - {max}")
    protected String password;

    protected List<Role> roles;

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
