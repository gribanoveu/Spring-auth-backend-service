package com.github.gribanoveu.auth.controllers.exeptions;

import com.github.gribanoveu.auth.constants.ValidationMessages;
import com.github.gribanoveu.auth.controllers.dtos.response.ErrorResponse;
import com.github.gribanoveu.auth.controllers.dtos.response.ResponseDetails;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.enums.StatusLevel;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author Evgeny Gribanov
 * @version 07.07.2023
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StatusResponse> handleBadCredentialsException(BadCredentialsException e) {
        var details = StatusResponse.create(ResponseCode.BAD_CREDENTIAL, e.getMessage());
        return ResponseEntity.status(UNAUTHORIZED).body(details);
    }

    @ExceptionHandler(CredentialEx.class)
    public ResponseEntity<StatusResponse> handleCredentialsException(CredentialEx e) {
        var details = StatusResponse.create(ResponseCode.BAD_CREDENTIAL, e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid handler
    public ResponseEntity<ErrorResponse> handleValidAnnotationException(MethodArgumentNotValidException e) {
        String VALIDATION_ERROR = "Validation error";
        var errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ResponseDetails(VALIDATION_ERROR, fieldError.getDefaultMessage(),
                        StatusLevel.WARNING)).toList();

        return ResponseEntity.badRequest().body(ErrorResponse.listError(HttpStatus.BAD_REQUEST, errors));
    }


    @ExceptionHandler(ConstraintViolationException.class) // database entity handler
    public ResponseEntity<ErrorResponse> handleDatabaseValidationException(ConstraintViolationException e) {
        var errorList = e.getConstraintViolations().stream()
                .map(violation -> new ResponseDetails(
                        String.format(ValidationMessages.EXCEPTION_VIOLATION_PATTERN,
                                violation.getPropertyPath(), violation.getMessage()),
                        violation.getMessage(), StatusLevel.WARNING)).toList();

        return ResponseEntity.badRequest().body(ErrorResponse.listError(HttpStatus.BAD_REQUEST, errorList));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class) // query param handler
    public ResponseEntity<StatusResponse> handleMissingParamException(MissingServletRequestParameterException e) {
        var details = StatusResponse.create(ResponseCode.MISSING_PARAM, e.getMessage());
        return ResponseEntity.badRequest().body(details);
    }

}
