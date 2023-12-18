package com.github.gribanoveu.cuddly.controllers.dtos.response.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.cuddly.entities.enums.Role;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionsResponse {
    @Schema(description = "Время выполнения запроса")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    private LocalDateTime timestamp;

    @Schema(description = "Уровень статуса запроса", defaultValue = "INFO")
    private StatusLevel status;

    @Schema(description = "Информация о ролях пользователя")
    private Collection<Role> permissions;

    public static PermissionsResponse create(HttpStatus status, Collection<Role> permissions) {
        return new PermissionsResponse(LocalDateTime.now(), StatusLevel.SUCCESS, permissions);
    }
}
