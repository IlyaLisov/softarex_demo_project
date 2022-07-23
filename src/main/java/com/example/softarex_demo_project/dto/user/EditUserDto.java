package com.example.softarex_demo_project.dto.user;

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
}
