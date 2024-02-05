package com.github.gribanoveu.cuddle.exeptions.responses;

import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.exeptions.errors.MessageDetails;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.github.gribanoveu.cuddle.exeptions.errors.ModeratorMessage.SEND_MESSAGE;

/**
 * @author Evgeny Gribanov
 * @version 18.01.2024
 */
@Schema(description = "Сущность для возврата статуса операции")
public record RestResponse(
        @Schema(description = "Код операции")
        String code,

        @Schema(description = "Статус операции")
        StatusLevel status,

        @Schema(description = "Детали операции")
        Details details
) {
    public record Details(
            @Schema(description = "Имя операции")
            String name,

            @Schema(description = "Подробное описание операции")
            String message
    ) {}


    public static RestResponse create(MessageDetails details) {
        return new RestResponse(details.getCode(), details.getStatus(),
                new Details(details.getName(), details.getMessage()));
    }

    public static RestResponse create(String details) {
        return new RestResponse(SEND_MESSAGE.getCode(), SEND_MESSAGE.getStatus(),
                new Details(SEND_MESSAGE.getName(), details));
    }
}
