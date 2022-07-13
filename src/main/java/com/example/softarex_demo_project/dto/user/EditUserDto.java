package com.example.softarex_demo_project.dto.user;

import com.example.softarex_demo_project.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class is a DTO of User class for editing.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EditUserDto extends UserDto {
    private String newPassword;

    public static EditUserDto fromUser(User user, String newPassword) {
        EditUserDto userDto = new EditUserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        userDto.setNewPassword(newPassword);
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
        user.setPassword(newPassword);
        user.setRoles(roles);
        return user;
    }
}
