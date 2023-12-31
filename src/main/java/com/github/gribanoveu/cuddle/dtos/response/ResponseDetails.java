package com.github.gribanoveu.cuddle.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Сущность для описания деталей операции")
public class ResponseDetails {
    @Schema(description = "Тип операции")
    private String type;

    @Schema(description = "Код операции")
    private String code;

    @Schema(description = "Подробное описание операции")
    private String message;

    public ResponseDetails(ResponseCode responseCode) {
        this.type = responseCode.getType();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public ResponseDetails(ResponseCode responseCode, String message) {
        this.code = responseCode.getCode();
        this.message = message;
        this.type = responseCode.getType();
    }

    public ResponseDetails(String message) {
        this.code = ResponseCode.SEND_MESSAGE.getCode();
        this.type = ResponseCode.SEND_MESSAGE.getType();
        this.message = message;
    }
}