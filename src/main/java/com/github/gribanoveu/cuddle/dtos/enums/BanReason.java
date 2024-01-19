package com.github.gribanoveu.cuddle.dtos.enums;

import com.github.gribanoveu.cuddle.exeptions.CredentialEx;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Evgeny Gribanov
 * @version 25.12.2023
 */
@Getter
@AllArgsConstructor
public enum BanReason {
    USERS_ABUSE("CR-1.1", "Оскорбление пользователей", LocalDateTime.now().plusMinutes(10));

    private final String code;
    private final String message;
    private final LocalDateTime banExpiration;

    public static BanReason getBanExpirationByCode(String code) {
        for (var reason : values()) if (reason.code.equals(code)) return reason;
        throw new CredentialEx(ResponseCode.BAN_REASON_NOT_FOUND);
    }

    public static String getBanMessageByCode(String code) {
        for (var reason : values()) {
            if (reason.code.equals(code)) {
                return reason.message;
            }
        }
        throw new CredentialEx(ResponseCode.BAN_REASON_NOT_FOUND);
    }
}
