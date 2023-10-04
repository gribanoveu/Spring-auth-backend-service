package com.github.gribanoveu.auth.constants;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
public class RegexpFormat {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PERMISSION_PATTERN = "^[A-Z]{2}_[A-Z_]+$";
    public static final String OTP_PATTERN = "^\\d{6}$";
}
