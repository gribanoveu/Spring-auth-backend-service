package com.github.gribanoveu.auth.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.auth.entities.tables.Permission;
import com.github.gribanoveu.auth.entities.tables.User;
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
public class UsersResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    private LocalDateTime timestamp;
    private int status;
    private String reason;
    private Collection<User> users;

    public static UsersResponse create(HttpStatus status, Collection<User> users) {
        return new UsersResponse(
                LocalDateTime.now(), status.value(), status.getReasonPhrase(), users);
    }
}
