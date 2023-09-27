package com.github.gribanoveu.auth.controllers.dtos.request;

/**
 * @author Evgeny Gribanov
 * @version 11.09.2023
 */
public record ChangeRoleDto(
        String email,
        String role
) {
}
