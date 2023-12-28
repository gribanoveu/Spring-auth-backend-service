package com.github.gribanoveu.cuddle.constants;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Evgeny Gribanov
 * @version 18.09.2023
 */
public class Constants {
    public static final DateTimeFormatter DEFAULT_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.forLanguageTag("ru"));

    public static final String USER_DISABLED_MESSAGE = "Пользователь отключен";
}
