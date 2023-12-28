package com.github.gribanoveu.cuddle.constants;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
public class RegexpFormat {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String OTP_PATTERN = "^\\d{6}$";
    public static final String BIRTHDAY_PATTERN = "^(19\\d{2}|20\\d{2}|2100)-(0[1-9]|1[0-2])-([0-2][1-9]|3[0-1])$";
    public static final String UNIX_DATE_TOKEN_PATTERN = "\\|(\\d+)";
    public static final String EMAIL_TOKEN_PATTERN = "^([^|]+)";
}
