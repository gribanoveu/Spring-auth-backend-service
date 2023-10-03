package com.github.gribanoveu.auth.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.auth.constants.ValidationMessages.PASSWORD_CANT_BE_EMPTY;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
public record ChangePasswordDto(
        @Size(max = 80)
        @NotBlank(message = PASSWORD_CANT_BE_EMPTY)
        String oldPassword,

        @Size(max = 80)
        @NotBlank(message = PASSWORD_CANT_BE_EMPTY)
        String password,

        @Size(max = 80)
        @NotBlank(message = PASSWORD_CANT_BE_EMPTY)
        String confirmPassword
) {}
