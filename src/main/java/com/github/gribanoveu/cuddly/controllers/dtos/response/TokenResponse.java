package com.github.gribanoveu.cuddly.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Evgeny Gribanov
 * @version 25.09.2023
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {
    private LocalDateTime timestamp;
    private StatusLevel status;
    private Long tokenLifetimeMin;
    private String accessToken;
    private String refreshToken;

    public static TokenResponse create(HttpStatus status, Long accessTokenLifetime, String accessToken, String refreshToken) {
        return new TokenResponse(LocalDateTime.now(), StatusLevel.SUCCESS,
                accessTokenLifetime, accessToken, refreshToken);
    }
}