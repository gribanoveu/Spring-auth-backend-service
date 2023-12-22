package com.github.gribanoveu.cuddle.endpoints.anonymous;

import com.github.gribanoveu.cuddle.dtos.request.auth.LoginDto;
import com.github.gribanoveu.cuddle.dtos.request.auth.RefreshTokenDto;
import com.github.gribanoveu.cuddle.dtos.response.auth.TokenResponse;
import com.github.gribanoveu.cuddle.controllers.anonymous.AuthenticationControllerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Tag(name="Аутентификация пользователя", description="Позволяет получить и обновить токен")
public class AuthenticationController {
    private final AuthenticationControllerFacade authenticationControllerFacade;

    @PostMapping
    @Operation(summary = "Получить токен", description = "Получение access token и refresh token")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody LoginDto request) {
        return authenticationControllerFacade.authenticateUser(request);
    }

    @PatchMapping
    @Operation(summary = "Обновить токен", description = "Получение нового токена на основе refresh token")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenDto request) {
        return authenticationControllerFacade.refreshToken(request);
    }
}
