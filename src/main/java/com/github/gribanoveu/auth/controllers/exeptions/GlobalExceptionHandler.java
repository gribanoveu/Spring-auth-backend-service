package com.github.gribanoveu.auth.controllers.exeptions;

import com.github.gribanoveu.auth.constants.ValidationMessages;
import com.github.gribanoveu.auth.controllers.dtos.response.ResponseDetails;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.enums.StatusLevel;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author Evgeny Gribanov
 * @version 07.07.2023
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        var details = StatusResponse.create(ResponseCode.RESOURCE_NOT_FOUND, StatusLevel.ERROR);
        return ResponseEntity.status(UNAUTHORIZED).body(details);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StatusResponse> handleBadCredentialsException(BadCredentialsException e) {
        var details = StatusResponse.create(
                ResponseCode.BAD_CREDENTIAL, e.getMessage(), StatusLevel.ERROR);
        return ResponseEntity.status(UNAUTHORIZED).body(details);
    }

    @ExceptionHandler(CredentialEx.class)
    public ResponseEntity<?> handleCredentialsException(CredentialEx e) {
        var details = StatusResponse.create(e.getError(), StatusLevel.ERROR);
        return ResponseEntity.status(e.getStatus()).body(details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid handler
    public ResponseEntity<?> handleValidAnnotationException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ResponseDetails(ResponseCode.VALIDATION_ERROR)).toList();

        var details = StatusResponse.create( errors, StatusLevel.ERROR);
        return ResponseEntity.status(e.getStatusCode()).body(details);
    }


    @ExceptionHandler(ConstraintViolationException.class) // database entity handler
    public ResponseEntity<?> handleDatabaseValidationException(ConstraintViolationException e) {
        var errors = e.getConstraintViolations().stream()
                .map(violation -> new ResponseDetails(ResponseCode.VALIDATION_ERROR,
                        String.format(ValidationMessages.EXCEPTION_VIOLATION_PATTERN, violation.getPropertyPath(), violation.getMessage())))
                .toList();
        var details = StatusResponse.create( errors, StatusLevel.ERROR);
        return ResponseEntity.status(ResponseCode.VALIDATION_ERROR.getHttpCode()).body(details);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class) // query param handler
    public ResponseEntity<StatusResponse> handleMissingParamException(MissingServletRequestParameterException e) {
        var details = StatusResponse.create(ResponseCode.MISSING_PARAM, StatusLevel.ERROR);
        return ResponseEntity.badRequest().body(details);
    }

}
