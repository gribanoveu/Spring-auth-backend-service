package com.github.gribanoveu.auth.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Evgeny Gribanov
 * @version 02.10.2023
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    private LocalDateTime timestamp;
    private int status;
    private String reason;
    private Collection<ResponseDetails> details;

    public static ErrorResponse listError(HttpStatusCode status, Collection<ResponseDetails> details) {
        return new ErrorResponse(LocalDateTime.now(), status.value(),
                status.toString().substring(4) , details);
    }
}
