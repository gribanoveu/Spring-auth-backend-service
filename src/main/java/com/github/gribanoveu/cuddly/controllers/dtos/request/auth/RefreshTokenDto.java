package com.github.gribanoveu.cuddly.controllers.dtos.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.github.gribanoveu.cuddly.constants.ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE;

/**
 * @author Evgeny Gribanov
 * @version 24.10.2023
 */
@Schema(description = "Сущность для получения нового access token")
public record RefreshTokenDto(
        @Schema(description = "Токен обновления (refresh token)")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        String refreshToken
) {}
