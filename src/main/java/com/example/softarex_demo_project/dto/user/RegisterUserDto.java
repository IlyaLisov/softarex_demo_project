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

    public static RegisterUserDto fromUser(User user, String passwordConfirmation) {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        userDto.setPasswordConfirmation(passwordConfirmation);
        return userDto;
    }

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
