package com.example.softarex_demo_project.dto;

import lombok.Data;

/**
 * This class is a DTO of Authentication class with username and password.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
