package com.example.softarex_demo_project.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * This class is a DTO of Authentication class with username and password.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class AuthenticationRequestDto {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
