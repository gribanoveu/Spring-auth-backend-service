package com.github.gribanoveu.auth.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Evgeny Gribanov
 * @version 06.07.2023
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    private LocalDateTime timestamp;
    private int status;
    private String reason;
    private String message;
    private String accessToken;
    private String refreshToken;

    public static StatusResponse create(HttpStatus status, String message) {
        return new StatusResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message,
                null, null);
    }
}

