package com.example.softarex_demo_project.dto;

/**
 * This class is a DTO of Authentication class with username and password.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public class AuthenticationRequestDto {
    private String username;
    private String password;

    public AuthenticationRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
