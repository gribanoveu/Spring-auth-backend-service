package com.github.gribanoveu.cuddle.endpoints.anonymous;

import com.github.gribanoveu.cuddle.dtos.request.auth.GenerateOtpDto;
import com.github.gribanoveu.cuddle.dtos.request.auth.RegisterDto;
import com.github.gribanoveu.cuddle.dtos.request.auth.RestorePasswordDto;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.controllers.anonymous.PublicAccountControllerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/account")
@Tag(name="Управление аккаунтом без авторизации", description="Позволяет управлять данными учетной записи")
public class PublicAccountController {
    private final PublicAccountControllerFacade publicAccountControllerFacade;

    @PostMapping("/generate-code")
    @Operation(summary = "Сгенерировать OTP код")
    public ResponseEntity<StatusResponse> generateOtpCode(@Valid @RequestBody GenerateOtpDto request, HttpServletRequest http) {
        return publicAccountControllerFacade.generateOtpCode(request, http);
    }

    @PostMapping("/restore-password")
    @Operation(summary = "Восстановить пароль через OTP код")
    public ResponseEntity<StatusResponse> restorePassword(@Valid @RequestBody RestorePasswordDto request) {
        return publicAccountControllerFacade.restorePasswordByOtp(request);
    }

    @PostMapping("/create")
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<StatusResponse> registerUser(@Valid @RequestBody RegisterDto request) {
        return publicAccountControllerFacade.registerUser(request);
    }
}
