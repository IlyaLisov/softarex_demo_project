package com.example.softarex_demo_project.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.example.softarex_demo_project.config.ApplicationConstants.MAX_FIELD_LENGTH;
import static com.example.softarex_demo_project.config.ApplicationConstants.MIN_FIELD_LENGTH;

/**
 * This class is a DTO of User class for registration.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterUserDto extends UserDto {
    @NotNull(message = "Password confirmation is missing.")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Length must be in {min} - {max}")
    private String passwordConfirmation;
}
