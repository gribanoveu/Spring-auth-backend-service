package com.github.gribanoveu.auth.controllers.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.enums.StatusLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDetails {
    private String code;
    private String message;
    private StatusLevel statusLevel;

    public ResponseDetails(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.statusLevel = responseCode.getStatusLevel();
    }

    public ResponseDetails(ResponseCode responseCode, String message) {
        this.code = responseCode.getCode();
        this.message = message;
        this.statusLevel = responseCode.getStatusLevel();
    }
}