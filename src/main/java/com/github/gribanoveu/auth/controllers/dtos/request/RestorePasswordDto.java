package com.github.gribanoveu.auth.controllers.dtos.request;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
public record RestorePasswordDto(
        String email,
        Integer otpCode,
        String password,
        String confirmPassword
) {
}
