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

    public static final String deleteSubject = "Ваш аккаунт удален";
    public static final String deleteModerTemplate = "user-deleted-moder";

    public static final String disabledSubject = "Действие вашего аккаунта приостановлено";
    public static final String disabledTemplate = "account-disabled";

    public static final String enabledSubject = "Действие вашего аккаунта возобновлено";
    public static final String enabledTemplate = "account-enabled";

    public static final String banSubject = "Ваш аккаунт временно заблокирован";
    public static final String banTemplate = "account-banned";
}
