package com.example.softarex_demo_project.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.example.softarex_demo_project.config.ApplicationConstants.MAX_FIELD_LENGTH;
import static com.example.softarex_demo_project.config.ApplicationConstants.MIN_FIELD_LENGTH;

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

    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Length must be in {min} - {max}")
    @Email(message = "You must input email.")
    protected String username;

    @Length(max = MAX_FIELD_LENGTH, message = "Max length is {max}")
    protected String firstName;

    @Length(max = MAX_FIELD_LENGTH, message = "Max length is {max}")
    protected String lastName;

    @Length(max = MAX_FIELD_LENGTH, message = "Length must be in {min} - {max}")
    protected String phoneNumber;

    @NotNull(message = "Password is missing.")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Length must be in {min} - {max}")
    protected String password;

    protected List<String> roles;
}
