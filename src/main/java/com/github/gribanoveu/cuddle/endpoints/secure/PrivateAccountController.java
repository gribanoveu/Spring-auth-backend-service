package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.anonymous.PublicAccountControllerImpl;
import com.github.gribanoveu.cuddle.dtos.request.ChangeEmailDto;
import com.github.gribanoveu.cuddle.dtos.request.ChangePasswordDto;
import com.github.gribanoveu.cuddle.exeptions.responses.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evgeny Gribanov
 * @version 22.12.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/account")
@Tag(name="Управление аккаунтом с авторизацией", description="Позволяет управлять данными учетной записи")
public class PrivateAccountController {
    private final PublicAccountControllerImpl userControllerFacade;

    @Operation(summary = "Сменить Email")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/change-email")
    public ResponseEntity<RestResponse> changeEmail(@Valid @RequestBody ChangeEmailDto request, Authentication auth) {
        return userControllerFacade.changeEmail(request, auth);
    }

    @Operation(summary = "Сменить пароль")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/change-password")
    public ResponseEntity<RestResponse> changePassword(@Valid @RequestBody ChangePasswordDto request, Authentication auth) {
        return userControllerFacade.changePassword(request, auth);
    }
}
