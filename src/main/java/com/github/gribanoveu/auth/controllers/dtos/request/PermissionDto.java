package com.github.gribanoveu.auth.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.auth.constants.RegexpFormat.PERMISSION_PATTERN;
import static com.github.gribanoveu.auth.constants.ValidationMessages.PERMISSION_FORMAT_MESSAGE;
import static com.github.gribanoveu.auth.constants.ValidationMessages.PERMISSION_SIZE_MESSAGE;

/**
 * @author Evgeny Gribanov
 * @version 25.09.2023
 */
public record PermissionDto(
        @NotBlank
        @Pattern(regexp = PERMISSION_PATTERN, message = PERMISSION_FORMAT_MESSAGE)
        @Size(max = 30, message = PERMISSION_SIZE_MESSAGE)
        String permissionName
) {}
