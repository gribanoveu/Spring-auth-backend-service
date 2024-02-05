package com.github.gribanoveu.cuddle.exeptions.responses;

import com.github.gribanoveu.cuddle.exeptions.errors.MessageDetails;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Evgeny Gribanov
 * @version 18.01.2024
 */
@Getter
public class RestException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final RestResponse error;

    public RestException(MessageDetails responseCode) {
        this.httpStatus = responseCode.getHttpCode();
        this.error = RestResponse.create(responseCode);
    }
}
