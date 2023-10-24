package com.github.gribanoveu.cuddly.controllers.endpoints.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.ChangeEmailDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.ChangePasswordDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.GenerateOtpDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RestorePasswordDto;
import com.github.gribanoveu.cuddly.controllers.facade.auth.AccountControllerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountControllerFacade userControllerFacade;

    @PatchMapping("/change-email")
    public ResponseEntity<?> changeEmail(@Valid @RequestBody ChangeEmailDto request, Authentication authentication) {
        return userControllerFacade.changeEmail(request, authentication);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto request, Authentication authentication) {
        return userControllerFacade.changePassword(request, authentication);
    }

    @PostMapping("/generate-code") // anonymous access
    public ResponseEntity<?> generateOtpCode(@Valid @RequestBody GenerateOtpDto request) {
        return userControllerFacade.generateOtpCode(request);
    }

    @PostMapping("/restore-password") // anonymous access
    public ResponseEntity<?> restorePassword(@Valid @RequestBody RestorePasswordDto request) {
        return userControllerFacade.restorePasswordByOtp(request);
    }
}
