package com.github.gribanoveu.auth.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.auth.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
public record ChangePasswordDto(
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String oldPassword,

        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String password,

        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String confirmPassword
) {}
