package com.github.gribanoveu.cuddle.constants;

/**
 * @author Evgeny Gribanov
 * @version 27.11.2023
 */
public class EmailMessages {
    public static final String sendFrom = "noreply@codecow.pw";
    public static final String restorePasswordSubject = "Восстановление пароля";
    public static final String restorePasswordTemplate = "otp-code-template";

    public static final String passwordChangedSubject = "Пароль успешно изменен";
    public static final String passwordChangedTemplate = "password-changed";

    public static final String userRegisteredSubject = "Успешная регистрация";
    public static final String userRegisteredTemplate = "successfully-registration";

    public static final String loginSubject = "В ваш аккаунт выполнен вход";
    public static final String loginTemplate = "successfully-login";

    public static final String changeEmailSubject = "Электронная почта изменена";
    public static final String changeEmailTemplate = "email-changed";
}
