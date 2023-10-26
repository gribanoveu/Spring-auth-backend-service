package com.github.gribanoveu.cuddly.controllers.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.cuddly.constants.RegexpFormat.EMAIL_PATTERN;
import static com.github.gribanoveu.cuddly.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
public record ChangeEmailDto(
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Pattern(regexp = EMAIL_PATTERN, message = PATTERN_EXCEPTION_MESSAGE)
        @Size(max = 30, message = SIZE_EXCEPTION_MESSAGE)
        String email
) { }