package com.github.gribanoveu.cuddly.controllers.dtos.response.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.cuddly.entities.enums.Role;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    private LocalDateTime timestamp;
    private StatusLevel status;
    private Collection<Role> permissions;

    public static PermissionsResponse create(HttpStatus status, Collection<Role> permissions) {
        return new PermissionsResponse(LocalDateTime.now(), StatusLevel.SUCCESS, permissions);
    }
}
