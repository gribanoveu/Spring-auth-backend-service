package com.github.gribanoveu.cuddle.exeptions;

import com.github.gribanoveu.cuddle.constants.ValidationMessages;
import com.github.gribanoveu.cuddle.dtos.response.ResponseDetails;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.utils.aspects.LogResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Evgeny Gribanov
 * @version 07.07.2023
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @LogResponse
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        var details = StatusResponse.create(ResponseCode.VALIDATION_ERROR, StatusLevel.ERROR);
        return ResponseEntity.status(ResponseCode.VALIDATION_ERROR.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var details = StatusResponse.create(ResponseCode.VALIDATION_ERROR, StatusLevel.ERROR);
        return ResponseEntity.status(ResponseCode.VALIDATION_ERROR.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        var details = StatusResponse.create(ResponseCode.RESOURCE_NOT_FOUND, StatusLevel.ERROR);
        return ResponseEntity.status(ResponseCode.RESOURCE_NOT_FOUND.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StatusResponse> handleBadCredentialsException(BadCredentialsException e) {
        var details = StatusResponse.create(
                ResponseCode.BAD_CREDENTIAL, e.getMessage(), StatusLevel.ERROR);
        return ResponseEntity.status(ResponseCode.BAD_CREDENTIAL.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(CredentialEx.class)
    public ResponseEntity<?> handleCredentialsException(CredentialEx e) {
        var details = StatusResponse.create(e.getError(), StatusLevel.WARNING);
        return ResponseEntity.status(e.getStatus()).body(details);
    }

    @LogResponse(message = "Validation error")
    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid handler
    public ResponseEntity<?> handleValidAnnotationException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ResponseDetails(
                         ResponseCode.VALIDATION_ERROR_DETAIL,
                                String.format(ResponseCode.VALIDATION_ERROR_DETAIL.getMessage(),
                                        fieldError.getField(), fieldError.getDefaultMessage()))).toList();

        var details = StatusResponse.create(errors, StatusLevel.WARNING);
        return ResponseEntity.status(e.getStatusCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(ConstraintViolationException.class) // database entity handler
    public ResponseEntity<?> handleDatabaseValidationException(ConstraintViolationException e) {
        var errors = e.getConstraintViolations().stream()
                .map(violation -> new ResponseDetails(ResponseCode.VALIDATION_ERROR,
                        String.format(ValidationMessages.EXCEPTION_VIOLATION_PATTERN, violation.getPropertyPath(), violation.getMessage())))
                .toList();
        var details = StatusResponse.create( errors, StatusLevel.ERROR);
        return ResponseEntity.status(ResponseCode.VALIDATION_ERROR.getHttpCode()).body(details);

    }

    @LogResponse
    @ExceptionHandler(MissingServletRequestParameterException.class) // query param handler
    public ResponseEntity<StatusResponse> handleMissingParamException(MissingServletRequestParameterException e) {
        var details = StatusResponse.create(ResponseCode.MISSING_PARAM, StatusLevel.ERROR);
        return ResponseEntity.badRequest().body(details);
    }

}
