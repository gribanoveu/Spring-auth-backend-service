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
public enum ModeratorMessage implements MessageDetails {
    USER_DISABLED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "AUT-402",
            "Пользователь отключен",
            "Учетная запись пользователя приостановила свое действие"
    ),
    USER_ENABLED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "AUT-403",
            "Пользователь включен",
            "Действие учетной записи пользователя успешно восстановлено"
    ),
    USER_BANNED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "AUT-408",
            "Пользователь заблокирован",
            "Действие учетной записи пользователя заблокировано"
    ),
    USER_UN_BANNED(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "AUT-409",
            "Пользователь разблокирован",
            "Ограничения с учетной записи пользователя сняты"
    ),
    USERS_NOT_FOUND(
            StatusLevel.WARNING,
            HttpStatus.NOT_FOUND,
            "AUT-410",
            "Пользователи отсутствуют",
            "Пользователей с такими ролями не найдены"
    ),
    PERMISSION_UPDATED_MODERATOR(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "AUT-500",
            "Доступ повышен",
            "Учетная запись теперь является модератором"
    ),
    PERMISSION_UPDATED_USER(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "AUT-501",
            "Доступ понижен",
            "Учетная запись теперь является пользователем"
    ),
    SEND_MESSAGE(
            StatusLevel.SUCCESS,
            HttpStatus.OK,
            "INF-100",
            "Информация",
            "%s"
    );


    private final StatusLevel status;
    private final HttpStatus httpCode;
    private final String code;
    private final String name;
    private final String message;
}
