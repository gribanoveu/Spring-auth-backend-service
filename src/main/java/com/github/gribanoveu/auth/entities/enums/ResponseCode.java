package com.github.gribanoveu.auth.entities.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Evgeny Gribanov
 * @version 02.10.2023
 */
@Getter
public enum ResponseCode {
    SERVER_ERROR("AUT-100","Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, StatusLevel.WARNING),
    ACCESS_DENIED("AUT-101","Access denied", HttpStatus.FORBIDDEN, StatusLevel.WARNING),
    UNAUTHORIZED("AUT-102","Unauthorized access", HttpStatus.UNAUTHORIZED, StatusLevel.WARNING),
    BAD_CREDENTIAL("AUT-103","Invalid credentials", HttpStatus.UNAUTHORIZED, StatusLevel.WARNING),
    MISSING_PARAM("AUT-104","Missing required query parameter", HttpStatus.BAD_REQUEST, StatusLevel.WARNING),
    TOKEN_NOT_VALID("AUT-105","Invalid token", HttpStatus.UNAUTHORIZED, StatusLevel.WARNING),

    PASSWORD_UPDATED("AUT-200","Password updated successfully", HttpStatus.OK, StatusLevel.SUCCESS),
    PASSWORD_NOT_EQUALS("AUT-201","Passwords do not match", HttpStatus.BAD_REQUEST, StatusLevel.WARNING),
    PASSWORD_EQUALS("AUT-202", "Password equals", HttpStatus.BAD_REQUEST, StatusLevel.WARNING),
    OLD_PASSWORD_NOT_MATCH("AUT-203","Old password does not match current password", HttpStatus.BAD_REQUEST, StatusLevel.WARNING),

    OTP_CODE_CREATED("AUT-300","OTP code created successfully", HttpStatus.CREATED, StatusLevel.SUCCESS),
    OTP_CODE_NOT_FOUND("AUT-301","OTP code not found", HttpStatus.NOT_FOUND, StatusLevel.WARNING),
    OTP_CODE_EXIST("AUT-302","The OTP code has already been sent to the email. Please wait and try again", HttpStatus.TOO_MANY_REQUESTS, StatusLevel.WARNING),

    USER_CREATED("AUT-400","User created successfully", HttpStatus.CREATED, StatusLevel.SUCCESS),
    USER_DELETED("AUT-401","User deleted successfully", HttpStatus.OK, StatusLevel.SUCCESS),
    USER_DISABLED("AUT-402","User disabled successfully", HttpStatus.OK, StatusLevel.SUCCESS),
    USER_ENABLED("AUT-403","User enabled successfully", HttpStatus.OK, StatusLevel.SUCCESS),
    USER_NOT_EXIST("AUT-404","User not found", HttpStatus.NOT_FOUND, StatusLevel.WARNING),
    USER_SET_DEFAULT_PASSWORD("AUT-405","Default password has been set to the user", HttpStatus.OK, StatusLevel.SUCCESS),
    USER_UPDATED("AUT-406","User updated successfully", HttpStatus.OK, StatusLevel.SUCCESS),
    USER_ALREADY_EXIST("AUT-407","User already exists", HttpStatus.CONFLICT, StatusLevel.WARNING),
    USER_NOT_UPDATED("AUT-408","User not updated", HttpStatus.BAD_REQUEST, StatusLevel.WARNING),

    PERMISSION_CREATED("AUT-500","Permission created successfully", HttpStatus.CREATED, StatusLevel.SUCCESS),
    PERMISSION_DELETED("AUT-501","Permission deleted successfully", HttpStatus.OK, StatusLevel.SUCCESS),
    PERMISSION_UPDATED("AUT-502","Permission updated successfully", HttpStatus.OK, StatusLevel.SUCCESS),
    PERMISSION_NOT_UPDATED("AUT-503","Permission not updated", HttpStatus.BAD_REQUEST, StatusLevel.WARNING),
    PERMISSION_EXIST("AUT-504","Permission already exists", HttpStatus.CONFLICT, StatusLevel.WARNING),
    PERMISSION_NOT_EXIST("AUT-505","Permission not found", HttpStatus.NOT_FOUND, StatusLevel.WARNING);

    private final String code;
    private final String message;
    private final HttpStatus status;
    private final StatusLevel statusLevel;

    ResponseCode(String code, String message, HttpStatus status, StatusLevel statusLevel) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.statusLevel = statusLevel;
    }
}
