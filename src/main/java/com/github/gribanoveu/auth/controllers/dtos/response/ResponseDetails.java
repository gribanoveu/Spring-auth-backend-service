package com.github.gribanoveu.auth.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.enums.StatusLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDetails {
    private String type;
    private String code;
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
}