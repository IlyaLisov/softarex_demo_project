package com.example.softarex_demo_project.dto;

import lombok.Data;

/**
 * This class is a DTO for refresh token request.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Data
public class RefreshRequestDto {
    private String refreshToken;
}
