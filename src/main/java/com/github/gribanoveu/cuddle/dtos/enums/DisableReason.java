package com.github.gribanoveu.cuddle.dtos.enums;

import com.github.gribanoveu.cuddle.exeptions.errors.UserMessage;
import com.github.gribanoveu.cuddle.exeptions.responses.RestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Evgeny Gribanov
 * @version 27.12.2023
 */
@Getter
@AllArgsConstructor
public enum DisableReason {
    AGE_APPROVE("NA-1.1", "Требуется подтверждение возраста");

    private final String code;
    private final String message;

    public static DisableReason getDisableReasonByCode(String code) {
        for (var reason : values()) if (reason.code.equals(code)) return reason;
        throw new RestException(UserMessage.BAN_REASON_NOT_FOUND);
    }
}
