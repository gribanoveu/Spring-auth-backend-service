package com.github.gribanoveu.cuddle.endpoints.anonymous;

import com.github.gribanoveu.cuddle.controllers.anonymous.AuthenticationControllerImpl;
import com.github.gribanoveu.cuddle.dtos.request.LoginDto;
import com.github.gribanoveu.cuddle.dtos.request.RefreshTokenDto;
import com.github.gribanoveu.cuddle.dtos.response.TokenResponse;
import com.nimbusds.jose.jwk.JWKSet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/open/v1/auth")
@Tag(name="Аутентификация пользователя", description="Позволяет получить и обновить токен")
public class AuthenticationController {
    private final AuthenticationControllerImpl authenticationControllerImpl;
    private final JWKSet jwkSet;

    @PostMapping
    @Operation(summary = "Получить токен", description = "Получение access token и refresh token")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody LoginDto request) {
        return authenticationControllerImpl.authenticateUser(request);
    }

    @PatchMapping
    @Operation(summary = "Обновить токен", description = "Получение нового токена на основе refresh token")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenDto request) {
        return authenticationControllerImpl.refreshToken(request);
    }

    @GetMapping("/jwk")
    @Operation(summary = "Получить JWK Set", description = "Получить JWK Set для проверки токена на валидность")
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }
}
