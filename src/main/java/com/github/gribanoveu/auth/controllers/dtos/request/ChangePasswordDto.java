package com.github.gribanoveu.auth.controllers.dtos.request;

import com.github.gribanoveu.auth.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.auth.constants.RegexpFormat.EMAIL_PATTERN;
import static com.github.gribanoveu.auth.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
public record ChangePasswordDto(
        @NotBlank(message = ValidationMessages.PASSWORD_CANT_BE_EMPTY)
        String oldPassword,

        @NotBlank(message = ValidationMessages.PASSWORD_CANT_BE_EMPTY)
        String password,

        @NotBlank(message = ValidationMessages.PASSWORD_CANT_BE_EMPTY)
        String confirmPassword
) {}
