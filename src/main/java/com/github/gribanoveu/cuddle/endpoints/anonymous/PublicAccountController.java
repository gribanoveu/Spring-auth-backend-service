package com.github.gribanoveu.cuddle.endpoints.anonymous;

import com.github.gribanoveu.cuddle.controllers.anonymous.PublicAccountControllerImpl;
import com.github.gribanoveu.cuddle.dtos.request.GenerateOtpDto;
import com.github.gribanoveu.cuddle.dtos.request.RegisterDto;
import com.github.gribanoveu.cuddle.dtos.request.RestorePasswordDto;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/account")
@Tag(name="Управление аккаунтом без авторизации", description="Позволяет управлять данными учетной записи")
public class PublicAccountController {
    private final PublicAccountControllerImpl publicAccountControllerImpl;

    @PostMapping("/generate-code")
    @Operation(summary = "Сгенерировать OTP код")
    public ResponseEntity<StatusResponse> generateOtpCode(@Valid @RequestBody GenerateOtpDto request, HttpServletRequest http) {
        return publicAccountControllerImpl.generateOtpCode(request, http);
    }

    @PostMapping("/restore-password")
    @Operation(summary = "Восстановить пароль через OTP код")
    public ResponseEntity<StatusResponse> restorePassword(@Valid @RequestBody RestorePasswordDto request) {
        return publicAccountControllerImpl.restorePasswordByOtp(request);
    }

    @PostMapping("/create")
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<StatusResponse> registerUser(@Valid @RequestBody RegisterDto request) {
        return publicAccountControllerImpl.registerUser(request);
    }
}
