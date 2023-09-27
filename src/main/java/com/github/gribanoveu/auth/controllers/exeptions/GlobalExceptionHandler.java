package com.github.gribanoveu.auth.controllers.exeptions;

import com.github.gribanoveu.auth.constants.ValidationMessages;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Evgeny Gribanov
 * @version 07.07.2023
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StatusResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(UNAUTHORIZED)
                .body(StatusResponse.create(UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(CredentialEx.class)
    public ResponseEntity<StatusResponse> handleCredentialsException(CredentialEx e) {
        return ResponseEntity.status(e.getStatus())
                .body(StatusResponse.create(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid handler
    public ResponseEntity<StatusResponse> handleValidAnnotationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        return ResponseEntity.status(e.getStatusCode())
                .body(StatusResponse.create(BAD_REQUEST, String.join(", ", errors)));
    }

    @ExceptionHandler(ConstraintViolationException.class) // database entity handler
    public ResponseEntity<StatusResponse> handleDatabaseValidationException(ConstraintViolationException e) {
        var errorList = e.getConstraintViolations().stream()
                .map(violation -> String.format(ValidationMessages.EXCEPTION_VIOLATION_PATTERN,
                        violation.getPropertyPath(), violation.getMessage())).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(StatusResponse.create(HttpStatus.BAD_REQUEST, errorList.toString()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class) // query param handler
    public ResponseEntity<StatusResponse> handleMissingParamException(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(StatusResponse.create(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

}
