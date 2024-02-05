package com.github.gribanoveu.cuddle.exeptions;

import com.github.gribanoveu.cuddle.exeptions.errors.AuthMessage;
import com.github.gribanoveu.cuddle.exeptions.errors.ServerMessage;
import com.github.gribanoveu.cuddle.exeptions.responses.RestException;
import com.github.gribanoveu.cuddle.exeptions.responses.RestResponse;
import com.github.gribanoveu.cuddle.utils.aspects.LogResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evgeny Gribanov
 * @version 07.07.2023
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @LogResponse
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage());
        var details = RestResponse.create(ServerMessage.VALIDATION_ERROR);
        return ResponseEntity.status(ServerMessage.VALIDATION_ERROR.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        var details = RestResponse.create(ServerMessage.VALIDATION_ERROR);
        return ResponseEntity.status(ServerMessage.VALIDATION_ERROR.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage());
        var details = RestResponse.create(ServerMessage.RESOURCE_NOT_FOUND);
        return ResponseEntity.status(ServerMessage.RESOURCE_NOT_FOUND.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestResponse> handleBadCredentialsException(BadCredentialsException e) {
        log.error(e.getMessage());
        var details = RestResponse.create(AuthMessage.BAD_CREDENTIAL);
        return ResponseEntity.status(AuthMessage.BAD_CREDENTIAL.getHttpCode()).body(details);
    }

    @LogResponse
    @ExceptionHandler(RestException.class)
    public ResponseEntity<?> handleCredentialsException(RestException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getError());
    }

    @LogResponse(message = "Validation error")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidAnnotationException(MethodArgumentNotValidException e) {
        log.error("Incoming request validation error: {}", e.getMessage());
        var errorsList = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
        String errors = String.join("; ", errorsList);
        log.error("Validation error: {}", errors);
        var details = RestResponse.create(ServerMessage.VALIDATION_ERROR);
        return ResponseEntity.status(e.getStatusCode()).body(details);
    }



    @LogResponse
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleDatabaseValidationException(ConstraintViolationException e) {
        log.error("Database validation error: {}", e.getMessage());
        var errorsList = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).toList();
        log.error("Validation errors: {}", errorsList);
        var details = RestResponse.create(ServerMessage.VALIDATION_ERROR);
        return ResponseEntity.badRequest().body(details);
    }


    @LogResponse
    @ExceptionHandler(MissingServletRequestParameterException.class) // query param handler
    public ResponseEntity<RestResponse> handleMissingParamException(MissingServletRequestParameterException e) {
        log.error(e.getMessage());
        var details = RestResponse.create(ServerMessage.MISSING_PARAM);
        return ResponseEntity.badRequest().body(details);
    }

    @LogResponse
    @ExceptionHandler(InvalidDataAccessApiUsageException.class) // query param handler
    public ResponseEntity<RestResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        log.error(e.getMessage());
        var details = RestResponse.create(ServerMessage.MISSING_PARAM);
        return ResponseEntity.badRequest().body(details);
    }

    @LogResponse
    @ExceptionHandler(PropertyReferenceException.class) // query param handler
    public ResponseEntity<RestResponse> handlePropertyReferenceException(PropertyReferenceException e) {
        log.error(e.getMessage());
        var details = RestResponse.create(ServerMessage.MISSING_PARAM);
        return ResponseEntity.badRequest().body(details);
    }
}
