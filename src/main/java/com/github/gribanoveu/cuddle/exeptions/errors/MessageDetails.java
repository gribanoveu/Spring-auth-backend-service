package com.github.gribanoveu.cuddle.exeptions.errors;

import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import org.springframework.http.HttpStatus;

/**
 * @author Evgeny Gribanov
 * @version 18.01.2024
 */
public interface MessageDetails {
    StatusLevel getStatus();
    String getCode();
    String getName();
    String getMessage();
    HttpStatus getHttpCode();
}
