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
public enum ServerMessage implements MessageDetails {
    SERVER_ERROR( // Server Internal Message
            StatusLevel.ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR,
            "SIM-01",
            "Внутренняя ошибка сервера",
            "Что-то пошло не так"
    ),
    MISSING_PARAM(
            StatusLevel.ERROR,
            HttpStatus.BAD_REQUEST,
            "SIM-02",
            "Неверный параметр запроса",
            "Убедитесь, что вы корректно ввели все необходимые параметры, и повторите попытку"
    ),
    VALIDATION_ERROR(
            StatusLevel.ERROR,
            HttpStatus.BAD_REQUEST,
            "SIM-03",
            "Ошибка валидации",
            "Убедитесь, что данные представлены в правильном формате, и повторите попытку"
    ),
    RESOURCE_NOT_FOUND(
            StatusLevel.ERROR,
            HttpStatus.NOT_FOUND,
            "SIM-04",
            "Ресурс не найден",
            "Запрашиваемый ресурс не найден. Пожалуйста, проверьте правильность введенной информации и повторите попытку"
    ),
    EMAIL_NOT_SEND(
            StatusLevel.ERROR,
            HttpStatus.TOO_MANY_REQUESTS,
            "SIM-05",
            "Ошибка при отправке письма",
            "Произошла ошибка при отправке письма электронной почты, связанной с данной учетной записью"
    );

    private final StatusLevel status;
    private final HttpStatus httpCode;
    private final String code;
    private final String name;
    private final String message;
}
