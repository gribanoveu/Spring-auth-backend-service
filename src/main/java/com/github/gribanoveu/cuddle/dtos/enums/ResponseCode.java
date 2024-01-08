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
            "Внутренняя ошибка сервера",
            "Что-то пошло не так",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    ACCESS_DENIED(
            "AUT-101",
            "Доступ запрещен",
            "Пожалуйста, проверьте свои учетные данные и убедитесь, что у вас есть необходимые права доступа",
            HttpStatus.FORBIDDEN
    ),
    UNAUTHORIZED(
            "AUT-102",
            "Несанкционированный доступ",
            "Требуется авторизация. Войдите в свою учетную запись, чтобы получить доступ к этой странице. Если вы забыли свои учетные данные, воспользуйтесь функцией восстановления пароля",
            HttpStatus.UNAUTHORIZED
    ),
    BAD_CREDENTIAL(
            "AUT-103",
            "Неверные учетные данные",
            "Пожалуйста, проверьте правильность введенной информации и повторите попытку. Если вы забыли свои учетные данные, воспользуйтесь функцией восстановления пароля",
            HttpStatus.BAD_REQUEST
    ),
    MISSING_PARAM(
            "AUT-104",
            "Неверный параметр запроса",
            "Убедитесь, что вы корректно ввели все необходимые параметры, и повторите попытку",
            HttpStatus.BAD_REQUEST
    ),
    TOKEN_NOT_VALID(
            "AUT-105",
            "Неверный токен",
            "Убедитесь, что вы используете действительный токен, и повторите попытку",
            HttpStatus.UNAUTHORIZED
    ),
    VALIDATION_ERROR(
            "AUT-106",
            "Ошибка валидации",
            "Убедитесь, что данные представлены в правильном формате, и повторите попытку",
            HttpStatus.BAD_REQUEST
    ),
    VALIDATION_ERROR_DETAIL(
            "AUT-107",
            "Ошибка валидации",
            "Поле '%s' - %s",
            HttpStatus.BAD_REQUEST
    ),
    RESOURCE_NOT_FOUND(
            "AUT-108",
            "Ресурс не найден",
            "Запрашиваемый ресурс не найден. Пожалуйста, проверьте правильность введенной информации и повторите попытку",
            HttpStatus.NOT_FOUND
    ),
    ACCOUNT_BANNED(
            "AUT-109",
            "Учетная запись заблокирована",
            "Ваш аккаунт был заблокирован из-за нарушения правил. Подробная информация отправлена на ваш электронный адрес",
            HttpStatus.UNAUTHORIZED
    ),
    ACCOUNT_DISABLED(
            "AUT-110",
            "Учетная запись заморожена",
            "Действие вашей учетной записи приостановлено. Подробная информация отправлена на ваш электронный адрес",
            HttpStatus.UNAUTHORIZED
    ),
    NO_RESTRICTIONS(
            "AUT-111",
            "Нет ограничений",
            "На вашей учетной записи нет никаких ограничений",
            HttpStatus.NOT_FOUND
    ),
    PASSWORD_UPDATED(
            "AUT-200",
            "Пароль обновлен",
            "Используйте новый пароль для входа в учетную запись",
            HttpStatus.OK
    ),
    PASSWORD_NOT_EQUALS(
            "AUT-201",
            "Пароли не совпадают",
            "Убедитесь, что вы ввели одинаковые пароли, и повторите попытку",
            HttpStatus.BAD_REQUEST
    ),
    PASSWORD_EQUALS(
            "AUT-202",
            "Совпадение паролей",
            "Новый пароль должен отличаться от старого. Пожалуйста, введите другой пароль и повторите попытку",
            HttpStatus.BAD_REQUEST
    ),
    OLD_PASSWORD_NOT_MATCH(
            "AUT-203",
            "Пароли не совпадают",
            "Старый пароль не совпадает с текущим. Убедитесь, что вы ввели правильный старый пароль, и повторите попытку",
            HttpStatus.BAD_REQUEST
    ),
    OTP_CODE_CREATED(
            "AUT-300",
            "OTP код создан",
            "Одноразовый код доступа (OTP) был успешно создан. Пожалуйста, проверьте свою электронную почту",
            HttpStatus.OK
    ),
    OTP_CODE_NOT_FOUND(
            "AUT-301",
            "Не найден код OTP",
            "Код одноразового доступа (OTP) не найден или истек. Убедитесь, что вы ввели правильный код, и повторите попытку. Если у вас нет действующего кода, создайте новый",
            HttpStatus.NOT_FOUND
    ),
    OTP_CODE_EXIST(
            "AUT-302",
            "Код OTP уже создан",
            "Одноразовый код доступа (OTP) уже создан. Пожалуйста, проверьте свою электронную почту. Если вы не получили код, пожалуйста, подождите, прежде чем создавать новый код",
            HttpStatus.TOO_MANY_REQUESTS
    ),
    EMAIL_NOT_SEND(
            "AUT-303",
            "Ошибка при отправке письма",
            "Произошла ошибка при отправке письма электронной почты, связанной с данной учетной записью",
            HttpStatus.TOO_MANY_REQUESTS
    ),
    USER_CREATED(
            "AUT-400",
            "Успешная регистрация",
            "Учетная запись успешно зарегистрирована. Выполните вход для продолжения",
            HttpStatus.OK
    ),
    USER_DELETED(
            "AUT-401",
            "Пользователь удален",
            "Пользователь был успешно удален",
            HttpStatus.OK
    ),
    USER_DISABLED(
            "AUT-402",
            "Пользователь отключен",
            "Учетная запись пользователя приостановила свое действие",
            HttpStatus.OK
    ),
    USER_ENABLED(
            "AUT-403",
            "Пользователь включен",
            "Действие учетной записи пользователя успешно восстановлено",
            HttpStatus.OK
    ),
    USER_NOT_EXIST(
            "AUT-404",
            "Пользователь не найден",
            "Пожалуйста, убедитесь, что вы ввели правильный адрес электронной почты, и повторите попытку",
            HttpStatus.NOT_FOUND
    ),
    USER_UPDATED(
            "AUT-405",
            "Пользователь успешно обновлен",
            "Учетная запись пользователя успешно обновлена",
            HttpStatus.OK
    ),
    USER_ALREADY_EXIST(
            "AUT-406",
            "Пользователь уже существует",
            "Пожалуйста, убедитесь, что вы ввели правильный адрес электронной почты, и повторите попытку",
            HttpStatus.CONFLICT
    ),
    EMAIL_ALREADY_EXIST(
            "AUT-407",
            "Электронная почта уже существует",
            "Пожалуйста, убедитесь, что вы ввели отличный от текущего адрес электронной почты, и повторите попытку",
            HttpStatus.CONFLICT
    ),
    USER_BANNED(
            "AUT-408",
            "Пользователь заблокирован",
            "Действие учетной записи пользователя заблокировано",
            HttpStatus.OK
    ),
    USER_UN_BANNED(
            "AUT-409",
            "Пользователь разблокирован",
            "Ограничения с учетной записи пользователя сняты",
            HttpStatus.OK
    ),
    USERS_NOT_FOUND(
            "AUT-410",
            "Пользователи отсутствуют",
            "Пользователей с такими ролями не найдены",
            HttpStatus.NOT_FOUND
    ),
    PERMISSION_UPDATED_MODERATOR(
            "AUT-500",
            "Доступ повышен",
            "Учетная запись теперь является модератором",
            HttpStatus.OK
    ),
    PERMISSION_UPDATED_USER(
            "AUT-501",
            "Доступ понижен",
            "Учетная запись теперь является пользователем",
            HttpStatus.OK
    ),
    SEND_MESSAGE(
            "INF-100",
            "Информация",
            "%s",
            HttpStatus.OK
    ),
    BAN_REASON_NOT_FOUND(
            "MOD-100",
            "Информация",
            "Ограничения на учетной записи не найдены или отсутствуют",
            HttpStatus.OK
    );

    private final String code;
    private final String type;
    private final String message;
    private final HttpStatus httpCode;
}
