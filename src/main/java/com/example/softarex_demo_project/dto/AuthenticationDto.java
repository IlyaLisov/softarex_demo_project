package com.example.softarex_demo_project.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * This class is a DTO of Authentication class with username and token.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class AuthenticationDto {
    @NotEmpty
    private UUID userId;

    @NotEmpty
    private String username;

    @NotEmpty
    private String token;

    public AuthenticationDto() {
    }
}
