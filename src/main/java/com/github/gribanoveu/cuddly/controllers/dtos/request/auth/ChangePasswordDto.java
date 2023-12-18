package com.github.gribanoveu.cuddly.controllers.dtos.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.cuddly.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
@Schema(description = "Сущность для смены пароля авторизованным пользователем")
public record ChangePasswordDto(
        @Schema(description = "Старый пароль", example = "Qwerty123")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String oldPassword,

        @Schema(description = "Новый пароль", example = "Qwerty1234")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String password,

        @Schema(description = "Повторный ввод нового пароля", example = "Qwerty1234")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String confirmPassword
) {}
