package com.github.gribanoveu.auth.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private int status;
    private String reason;
    private Collection<ResponseDetails> details;

    public static StatusResponse create(ResponseCode responseCode) {
        return new StatusResponse(LocalDateTime.now(),
                responseCode.getStatus().value(), responseCode.getStatus().getReasonPhrase(),
                Collections.singletonList(new ResponseDetails(responseCode)));
    }

    public static StatusResponse create(ResponseCode responseCode, String message) {
        return new StatusResponse(LocalDateTime.now(),
                responseCode.getStatus().value(), responseCode.getStatus().getReasonPhrase(),
                Collections.singletonList(new ResponseDetails(responseCode, message)));
    }
}

