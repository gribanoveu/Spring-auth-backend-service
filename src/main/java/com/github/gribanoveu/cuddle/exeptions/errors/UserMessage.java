package com.github.gribanoveu.cuddle.exeptions.errors;

import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Evgeny Gribanov
 * @version 02.10.2023
 */
@Getter
@AllArgsConstructor
public enum UserMessage implements MessageDetails {
    ACCOUNT_BANNED( // User Internal Message
            StatusLevel.WARNING,
            HttpStatus.UNAUTHORIZED,
            "UIM-01",
            "Учетная запись заблокирована",
            "Ваш аккаунт был заблокирован из-за нарушения правил. Подробная информация отправлена на ваш электронный адрес"
    ),
    ACCOUNT_DISABLED(
            StatusLevel.WARNING,
            HttpStatus.UNAUTHORIZED,
            "UIM-02",
            "Учетная запись заморожена",
            "Действие вашей учетной записи приостановлено. Подробная информация отправлена на ваш электронный адрес"
    ),
    NO_RESTRICTIONS(
            StatusLevel.SUCCESS,
            HttpStatus.NOT_FOUND,
            "UIM-03",
            "Нет ограничений",
            "На вашей учетной записи нет никаких ограничений"
    ),
    PROFILE_NOT_CREATED(
            StatusLevel.SUCCESS,
            HttpStatus.NOT_FOUND,
            "UIM-04",
            "Создать профиль",
            "Для продолжения требуется заполнить профиль"
    ),
    PASSWORD_UPDATED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "UIM-05",
            "Пароль обновлен",
            "Используйте новый пароль для входа в учетную запись"
    ),
    PASSWORD_NOT_EQUALS(
            StatusLevel.WARNING,
            HttpStatus.BAD_REQUEST,
            "UIM-06",
            "Пароли не совпадают",
            "Убедитесь, что вы ввели одинаковые пароли, и повторите попытку"
    ),
    PASSWORD_EQUALS(
            StatusLevel.WARNING,
            HttpStatus.BAD_REQUEST,
            "UIM-07",
            "Совпадение паролей",
            "Новый пароль должен отличаться от старого, введите другой пароль"
    ),
    OLD_PASSWORD_NOT_MATCH(
            StatusLevel.WARNING,
            HttpStatus.BAD_REQUEST,
            "UIM-08",
            "Пароли не совпадают",
            "Убедитесь, что вы ввели правильный старый пароль, и повторите попытку"
    ),
    OTP_CODE_CREATED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "UIM-09",
            "OTP код создан",
            "Одноразовый код доступа (OTP) был успешно создан. Пожалуйста, проверьте свою электронную почту"
    ),
    OTP_CODE_NOT_FOUND(
            StatusLevel.WARNING,
            HttpStatus.NOT_FOUND,
            "UIM-10",
            "Не найден код OTP",
            "Код одноразового доступа (OTP) не найден или истек. Если у вас нет действующего кода, создайте новый"
    ),
    OTP_CODE_EXIST(
            StatusLevel.WARNING,
            HttpStatus.TOO_MANY_REQUESTS,
            "UIM-11",
            "Код OTP уже создан",
            "Одноразовый код доступа (OTP) уже создан. Пожалуйста, проверьте свою электронную почту. Если вы не получили код, пожалуйста, подождите, прежде чем создавать новый код"
    ),
    USER_CREATED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "UIM-12",
            "Успешная регистрация",
            "Учетная запись успешно зарегистрирована. Выполните вход для продолжения"
    ),
    USER_DELETED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "UIM-13",
            "Пользователь удален",
            "Пользователь был успешно удален"
    ),
    USER_NOT_EXIST(
            StatusLevel.WARNING,
            HttpStatus.NOT_FOUND,
            "UIM-14",
            "Пользователь не найден",
            "Пожалуйста, убедитесь, что вы ввели правильный адрес электронной почты, и повторите попытку"
    ),
    USER_UPDATED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "UIM-15",
            "Пользователь успешно обновлен",
            "Учетная запись пользователя успешно обновлена"
    ),
    USER_ALREADY_EXIST(
            StatusLevel.WARNING,
            HttpStatus.CONFLICT,
            "UIM-16",
            "Пользователь уже существует",
            "Пожалуйста, убедитесь, что вы ввели правильный адрес электронной почты, и повторите попытку"
    ),
    EMAIL_ALREADY_EXIST(
            StatusLevel.WARNING,
            HttpStatus.CONFLICT,
            "UIM-17",
            "Электронная почта уже существует",
            "Пожалуйста, убедитесь, что вы ввели отличный от текущего адрес электронной почты, и повторите попытку"
    ),
    BAN_REASON_NOT_FOUND(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "UIM-18",
            "Информация",
            "Ограничения на учетной записи не найдены или отсутствуют"
    ),
    USERS_NOT_FOUND(
            StatusLevel.WARNING,
            HttpStatus.NOT_FOUND,
            "UIM-19",
            "Пользователи не найдены",
            "Пожалуйста, убедитесь, что вы ввели правильный адрес электронной почты, и повторите попытку"
    );

    private final StatusLevel status;
    private final HttpStatus httpCode;
    private final String code;
    private final String name;
    private final String message;
}
