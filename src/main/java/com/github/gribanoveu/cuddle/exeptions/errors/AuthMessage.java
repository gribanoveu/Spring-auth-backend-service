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
public enum AuthMessage implements MessageDetails {
    ACCESS_DENIED( // Auth Internal Message
            StatusLevel.ERROR,
            HttpStatus.FORBIDDEN,
            "AIM-01",
            "Доступ запрещен",
            "Убедитесь, что у вас есть необходимые права доступа"
    ),
    UNAUTHORIZED(
            StatusLevel.ERROR,
            HttpStatus.UNAUTHORIZED,
            "AIM-02",
            "Требуется авторизация",
            "Войдите в свою учетную запись, чтобы получить доступ к этой странице. Если вы забыли свои учетные данные, воспользуйтесь функцией восстановления пароля"
    ),
    BAD_CREDENTIAL(
            StatusLevel.WARNING,
            HttpStatus.BAD_REQUEST,
            "AIM-03",
            "Неверные учетные данные",
            "Пожалуйста, проверьте правильность введенной информации и повторите попытку. Если вы забыли свои учетные данные, воспользуйтесь функцией восстановления пароля"
    ),
    TOKEN_NOT_VALID(
            StatusLevel.WARNING,
            HttpStatus.UNAUTHORIZED,
            "AIM-04",
            "Неверный токен",
            "Убедитесь, что вы используете действительный токен, и повторите попытку"
    );


    private final StatusLevel status;
    private final HttpStatus httpCode;
    private final String code;
    private final String name;
    private final String message;
}
