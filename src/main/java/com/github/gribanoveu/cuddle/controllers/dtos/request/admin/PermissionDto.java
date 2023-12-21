package com.github.gribanoveu.cuddle.controllers.dtos.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.cuddle.constants.RegexpFormat.PERMISSION_PATTERN;
import static com.github.gribanoveu.cuddle.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 25.09.2023
 */
public record PermissionDto(
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Pattern(regexp = PERMISSION_PATTERN, message = PATTERN_EXCEPTION_MESSAGE)
        @Size(max = 30, message = SIZE_EXCEPTION_MESSAGE)
        String permissionName
) {}
