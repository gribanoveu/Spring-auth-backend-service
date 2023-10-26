package com.github.gribanoveu.cuddly.controllers.dtos.request.auth;

import com.github.gribanoveu.cuddly.utils.validators.MinimalAge;
import jakarta.validation.constraints.*;

import static com.github.gribanoveu.cuddly.constants.RegexpFormat.EMAIL_PATTERN;
import static com.github.gribanoveu.cuddly.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
public record RegisterDto(
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Pattern(regexp = EMAIL_PATTERN, message = PATTERN_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String email,

        @MinimalAge(message = MINIMAL_AGE_EXCEPTION_MESSAGE)
        String birthDate, // todo write tests

        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String password,

        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String confirmPassword
) {}
