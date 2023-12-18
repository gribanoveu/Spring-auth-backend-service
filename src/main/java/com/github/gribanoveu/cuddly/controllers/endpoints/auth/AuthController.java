package com.github.gribanoveu.cuddly.controllers.endpoints.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.LoginDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RefreshTokenDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.auth.TokenResponse;
import com.github.gribanoveu.cuddly.controllers.facade.auth.AuthControllerFacade;
import com.github.gribanoveu.cuddly.utils.aspects.LogRequest;
import com.github.gribanoveu.cuddly.utils.aspects.LogResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
public class AuthController {
    private final AuthControllerFacade authControllerFacade;

    @PostMapping
    @Operation(summary = "Получить токен", description = "Получение access token и refresh token")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody LoginDto request) {
        return authControllerFacade.authenticateUser(request);
    }

    @PatchMapping
    @Operation(summary = "Обновить токен", description = "Получение нового токена на основе refresh token")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenDto request) {
        return authControllerFacade.refreshToken(request);
    }
}
