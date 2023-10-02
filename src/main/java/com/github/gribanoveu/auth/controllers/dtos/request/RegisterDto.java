package com.github.gribanoveu.auth.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.auth.constants.RegexpFormat.EMAIL_PATTERN;
import static com.github.gribanoveu.auth.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
public record RegisterDto(
        @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_FORMAT_MESSAGE)
        @Size(max = 30, message = EMAIL_SIZE_MESSAGE)
        String email,

        @Size(max = 80)
        @NotBlank(message = PASSWORD_CANT_BE_EMPTY)
        String password,

        @Size(max = 80)
        @NotBlank(message = PASSWORD_CANT_BE_EMPTY)
        String confirmPassword
) {}
