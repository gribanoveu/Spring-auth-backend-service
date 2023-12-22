package com.github.gribanoveu.cuddle.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность для возврата статуса операции")
public class StatusResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    @Schema(description = "Время выполнения запроса")
    private LocalDateTime timestamp;

    @Schema(description = "Уровень статуса запроса", defaultValue = "INFO")
    private StatusLevel status;

    @Schema(description = "Детали запроса")
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

