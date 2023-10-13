package com.github.gribanoveu.cuddly.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.cuddly.constants.ValidationMessages.*;

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
