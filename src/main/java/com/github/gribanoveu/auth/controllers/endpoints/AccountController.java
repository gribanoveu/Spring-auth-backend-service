package com.github.gribanoveu.auth.controllers.endpoints;

import com.github.gribanoveu.auth.controllers.dtos.request.ChangeEmailDto;
import com.github.gribanoveu.auth.controllers.dtos.request.ChangePasswordDto;
import com.github.gribanoveu.auth.controllers.dtos.request.GenerateOtpDto;
import com.github.gribanoveu.auth.controllers.dtos.request.RestorePasswordDto;
import com.github.gribanoveu.auth.controllers.facade.AccountControllerFacade;
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
        return userControllerFacade.generateOtpCode(request); // todo implement
    }

    @PostMapping("/restore-password") // anonymous access  todo restore by otp email code
    public ResponseEntity<?> restorePassword(@Valid @RequestBody RestorePasswordDto request) {
        return userControllerFacade.restorePasswordByOtp(request); // todo implement
    }
}
