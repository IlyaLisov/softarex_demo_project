package com.example.softarex_demo_project.dto.user;

import com.example.softarex_demo_project.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

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
    @Length(min = 4, max = 255, message = "Length must be in {min} - {max}")
    private String passwordConfirmation;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setRoles(roles);
        return user;
    }
}
