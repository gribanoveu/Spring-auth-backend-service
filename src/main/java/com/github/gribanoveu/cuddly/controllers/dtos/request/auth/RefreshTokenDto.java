package com.github.gribanoveu.cuddly.controllers.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;

import static com.github.gribanoveu.cuddly.constants.ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE;

/**
 * @author Evgeny Gribanov
 * @version 24.10.2023
 */
public record RefreshTokenDto(
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        String refreshToken
) {}
