package com.example.softarex_demo_project.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * This class is a DTO of Authentication class with username and token.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class AuthenticationDto {
    @NotEmpty
    private String username;

    @NotEmpty
    private String token;

    public AuthenticationDto() {
    }

    public AuthenticationDto(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
