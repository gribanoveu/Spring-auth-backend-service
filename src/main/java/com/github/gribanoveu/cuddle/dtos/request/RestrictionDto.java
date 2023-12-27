package com.github.gribanoveu.cuddle.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.github.gribanoveu.cuddle.constants.RegexpFormat.EMAIL_PATTERN;
import static com.github.gribanoveu.cuddle.constants.ValidationMessages.*;

/**
 * @author Evgeny Gribanov
 * @version 22.12.2023
 */
@Schema(description = "Сущность для установки ограничений на пользователя")
public record RestrictionDto(
        @Schema(description = "Email", example = "user@email.com")
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        @Pattern(regexp = EMAIL_PATTERN, message = PATTERN_EXCEPTION_MESSAGE)
        @Size(max = 30, message = SIZE_EXCEPTION_MESSAGE)
        String userEmail,

        @Schema(description = "Пункт правил причины", example = "CR-1.1")
        @Size(max = 15, message = SIZE_EXCEPTION_MESSAGE)
        @NotBlank(message = NOT_BLANK_EXCEPTION_MESSAGE)
        String reasonCode
) {
}
