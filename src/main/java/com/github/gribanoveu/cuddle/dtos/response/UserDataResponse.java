package com.github.gribanoveu.cuddle.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Evgeny Gribanov
 * @version 23.01.2024
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ответ c информацией о пользователе")
public class UserDataResponse {
    @Schema(description = "Идентификатор ULID пользователя", defaultValue = "01HMH24PM7S8E6XCN50PMWQBDQ")
    private String ulid;

    @Schema(description = "Email пользователя", defaultValue = "user@email.com")
    private String email;

    @Schema(description = "Дата рождения пользователя", defaultValue = "2001-01-01")
    private LocalDate birthDate;
}
