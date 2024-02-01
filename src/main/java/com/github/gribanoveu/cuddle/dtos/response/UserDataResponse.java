package com.github.gribanoveu.cuddle.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.entities.tables.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

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
