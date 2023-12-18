package com.github.gribanoveu.cuddly.controllers.dtos.request.auth;

import com.github.gribanoveu.cuddly.utils.validators.MinimalAge;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import static com.github.gribanoveu.cuddly.constants.RegexpFormat.EMAIL_PATTERN;
import static com.github.gribanoveu.cuddly.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Schema(description = "Сущность для регистрации нового пользователя")
public record RegisterDto(
        @Schema(description = "Электронная почта", example = "user@email.com")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Pattern(regexp = EMAIL_PATTERN, message = PATTERN_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String email,

        @Schema(description = "Дата рождения", example = "2001-12-13")
        @MinimalAge(message = MINIMAL_AGE_EXCEPTION_MESSAGE)
        String birthDate, // todo write tests

        @Schema(description = "Пароль")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String password,

        @Schema(description = "Пароль повторно")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Size(max = 80, message = SIZE_EXCEPTION_MESSAGE)
        String confirmPassword
) {}
