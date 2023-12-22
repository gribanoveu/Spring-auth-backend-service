package com.github.gribanoveu.cuddle.dtos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Evgeny Gribanov
 * @version 02.10.2023
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    SERVER_ERROR(
            "AUT-100",
            "Internal server error",
            "Something went wrong",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    ACCESS_DENIED(
            "AUT-101",
            "Access denied",
            "Access denied. Please check your credentials and make sure you have the required access rights",
            HttpStatus.FORBIDDEN
    ),
    UNAUTHORIZED(
            "AUT-102",
            "Unauthorized access",
            "Authorization required. Log in to your account to access this page. If you have forgotten your credentials, please use the password recovery feature",
            HttpStatus.UNAUTHORIZED
    ),
    BAD_CREDENTIAL(
            "AUT-103",
            "Invalid credentials",
            "Incorrect credentials. Please verify that the information entered is correct and try again. If you have forgotten your credentials, please use the password recovery feature",
            HttpStatus.BAD_REQUEST
    ),
    MISSING_PARAM(
            "AUT-104",
            "Missing required query parameter",
            "Required query parameter is missing. Please make sure you have entered all required parameters and try again",
            HttpStatus.BAD_REQUEST
    ),
    TOKEN_NOT_VALID(
            "AUT-105",
            "Invalid token",
            "Invalid token. Please make sure you are using a valid token and try again",
            HttpStatus.UNAUTHORIZED
    ),
    VALIDATION_ERROR(
            "AUT-106",
            "Validation error",
            "Validation Error. Please check that the data is in the correct format and try again",
            HttpStatus.BAD_REQUEST
    ),
    VALIDATION_ERROR_DETAIL(
            "AUT-107",
            "Validation error",
            "Field '%s' - %s",
            HttpStatus.BAD_REQUEST
    ),
    RESOURCE_NOT_FOUND(
            "AUT-108",
            "Resource not found",
            "The requested resource could not be found. Please verify that the information entered is correct and try again",
            HttpStatus.NOT_FOUND
    ),
    ACCOUNT_BANNED(
            "AUT-109",
            "Account banned",
            "Your account has been banned due to a violation of the Terms of Use Policy",
            HttpStatus.UNAUTHORIZED
    ),
    ACCOUNT_DISABLED(
            "AUT-110",
            "Account disabled",
            "Your account has been disabled contact support to find out why",
            HttpStatus.UNAUTHORIZED
    ),
    AGE_NOT_VALID(
            "AUT-111",
            "Age not valid",
            "Age must be at least 18 years old, or have expected format",
            HttpStatus.BAD_REQUEST
    ),
    PASSWORD_UPDATED(
            "AUT-200",
            "Password updated",
            "Password updated successfully",
            HttpStatus.OK
    ),
    PASSWORD_NOT_EQUALS(
            "AUT-201",
            "Passwords do not match",
            "The passwords do not match. Please make sure you have entered the same passwords and try again",
            HttpStatus.BAD_REQUEST
    ),
    PASSWORD_EQUALS(
            "AUT-202",
            "Password equals",
            "The new password must be different from the old one. Please enter a different password and try again",
            HttpStatus.BAD_REQUEST
    ),
    OLD_PASSWORD_NOT_MATCH(
            "AUT-203",
            "Passwords do not match",
            "The old password does not match the current password. Please make sure you have entered the correct old password and try again",
            HttpStatus.BAD_REQUEST
    ),
    OTP_CODE_CREATED(
            "AUT-300",
            "OTP code created",
            "The one-time access code (OTP) has been successfully created. Please check your e-mail",
            HttpStatus.OK
    ),
    OTP_CODE_NOT_FOUND(
            "AUT-301",
            "OTP code not found",
            "The One-Time Access (OTP) code was not found. Please make sure you have entered the correct code and try again. If you do not have a valid code, please create a new code",
            HttpStatus.NOT_FOUND
    ),
    OTP_CODE_EXIST(
            "AUT-302",
            "OTP code exist",
            "The one-time access code (OTP) has already been created. Please check your email. If you have not received the code, please wait before creating a new code",
            HttpStatus.TOO_MANY_REQUESTS
    ),
    EMAIL_NOT_SEND(
            "AUT-303",
            "Error when send email",
            "An error occurred when sending an email to this email account",
            HttpStatus.TOO_MANY_REQUESTS
    ),
    USER_CREATED(
            "AUT-400",
            "User created",
            "User has been successfully created",
            HttpStatus.OK
    ),
    USER_DELETED(
            "AUT-401",
            "User deleted",
            "User has been successfully deleted",
            HttpStatus.OK
    ),
    USER_DISABLED(
            "AUT-402",
            "User disabled",
            "User has been successfully disabled",
            HttpStatus.OK
    ),
    USER_ENABLED(
            "AUT-403",
            "User enabled",
            "User has been successfully enabled",
            HttpStatus.OK
    ),
    USER_NOT_EXIST(
            "AUT-404",
            "User not found",
            "User not found. Please make sure you have entered the correct email and try again",
            HttpStatus.NOT_FOUND
    ),
    USER_DEFAULT_PASSWORD(
            "AUT-405",
            "Set default password",
            "Default password has been successfully set for the user",
            HttpStatus.OK
    ),
    USER_UPDATED(
            "AUT-406",
            "User updated successfully",
            "User has been successfully updated",
            HttpStatus.OK
    ),
    USER_ALREADY_EXIST(
            "AUT-407",
            "User already exists",
            "User already exists. Please make sure you have entered the correct email and try again",
            HttpStatus.CONFLICT
    ),
    USER_BANNED(
            "AUT-408",
            "User banned",
            "User has been successfully banned",
            HttpStatus.OK
    ),
    USER_UN_BANNED(
            "AUT-409",
            "User unbanned",
            "User has been successfully unbanned",
            HttpStatus.OK
    ),
    PERMISSION_CREATED(
            "AUT-500",
            "Permission created",
            "Permission has been successfully created",
            HttpStatus.OK
    ),
    PERMISSION_DELETED(
            "AUT-501",
            "Permission deleted",
            "Permission has been successfully deleted",
            HttpStatus.OK
    ),
    PERMISSION_UPDATED(
            "AUT-502",
            "Permission updated",
            "Permission has been successfully updated",
            HttpStatus.OK
    ),
    PERMISSION_EXIST(
            "AUT-504",
            "Permission already exists",
            "Permission already exists. Please make sure you have entered the correct permission name and try again",
            HttpStatus.CONFLICT
    ),
    PERMISSION_NOT_EXIST(
            "AUT-505",
            "Permission not found",
            "Permission not found. Please make sure you have entered the correct permission name and try again",
            HttpStatus.NOT_FOUND
    );

    private final String code;
    private final String type;
    private final String message;
    private final HttpStatus httpCode;
}
