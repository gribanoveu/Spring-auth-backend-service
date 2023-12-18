package com.github.gribanoveu.cuddly.controllers.dtos.response.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Evgeny Gribanov
 * @version 25.09.2023
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {
    @Schema(description = "Время выполнения запроса")
    private LocalDateTime timestamp;

    @Schema(description = "Токен доступа")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "Тип токена", defaultValue = "Bearer")
    @JsonProperty("token_type")
    private String type;

    @Schema(description = "Время жизни токена в секундах", defaultValue = "900")
    @JsonProperty("expires_in")
    private Long accessLifetimeMin;

    @Schema(description = "Токен для обновления")
    @JsonProperty("refresh_token")
    private String refreshToken;

    public static TokenResponse create(
            Long accessTokenLifetime,
            String accessToken,
            String refreshToken
    ) {
        return new TokenResponse(LocalDateTime.now(), accessToken, "Bearer",
                accessTokenLifetime, refreshToken);
    }
}
