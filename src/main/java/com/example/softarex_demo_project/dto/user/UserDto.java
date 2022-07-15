package com.example.softarex_demo_project.dto.user;

import com.example.softarex_demo_project.model.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * This class is a DTO of User class for ROLE_USER users.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    protected UUID id;
    protected String username;

    @Length(max = 255, message = "Max length is {max}")
    protected String firstName;

    @Length(max = 255, message = "Max length is {max}")
    protected String lastName;

    @NotNull(message = "Email is missing.")
    @Length(min = 4, max = 255, message = "Length must be in {min} - {max}")
    @Email(message = "You must input email.")
    protected String email;

    @Length(max = 16, message = "Length must be in {min} - {max}")
    protected String phoneNumber;

    @NotNull(message = "Password is missing.")
    @Length(min = 4, max = 255, message = "Length must be in {min} - {max}")
    protected String password;

    protected List<Role> roles;
}
