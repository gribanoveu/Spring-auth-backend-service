package com.github.gribanoveu.auth.constants;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
public class ValidationMessages {
    public static final String PASSWORD_CANT_BE_EMPTY = "Field 'password' cannot be empty";
    public static final String EMAIL_SIZE_MESSAGE = "Field 'email' - size should be no more than 30";
    public static final String PERMISSION_SIZE_MESSAGE = "Field 'permission name' - size should be no more than 30";

    public static final String EMAIL_FORMAT_MESSAGE = "Field 'email' - must be a well-formed email address";
    public static final String PERMISSION_FORMAT_MESSAGE = "Field 'role' - must be a well-formed role name";
    public static final String EXCEPTION_VIOLATION_PATTERN = "Field '%s' - %s";
    public static final String OTP_FORMAT_MESSAGE = "Field 'otpCode' - must be a well-formed OTP code";
}
