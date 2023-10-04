package com.github.gribanoveu.auth.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.enums.StatusLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

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
    private StatusLevel status;
    private Collection<ResponseDetails> details;

    public static StatusResponse create(Collection<ResponseDetails> responseDetails, StatusLevel status) {
        return new StatusResponse(LocalDateTime.now(), status, responseDetails);
    }

    public static StatusResponse create(ResponseCode responseCode, StatusLevel status) {
        return new StatusResponse(LocalDateTime.now(), status,
                Collections.singletonList(new ResponseDetails(responseCode)));
    }

    public static StatusResponse create(ResponseCode responseCode, String message, StatusLevel status) {
        return new StatusResponse(LocalDateTime.now(), status,
                Collections.singletonList(new ResponseDetails(responseCode, message)));
    }
}

