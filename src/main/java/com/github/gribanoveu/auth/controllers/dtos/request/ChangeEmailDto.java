package com.github.gribanoveu.auth.controllers.dtos.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.auth.constants.RegexpFormat.EMAIL_PATTERN;
import static com.github.gribanoveu.auth.constants.ValidationMessages.EMAIL_FORMAT_MESSAGE;
import static com.github.gribanoveu.auth.constants.ValidationMessages.EMAIL_SIZE_MESSAGE;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
public record ChangeEmailDto(
        @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_FORMAT_MESSAGE)
        @Size(max = 30, message = EMAIL_SIZE_MESSAGE)
        String email
) { }
