package com.github.gribanoveu.cuddly.controllers.endpoints;

import com.github.gribanoveu.cuddly.controllers.dtos.request.ChangeEmailDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.ChangePasswordDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.GenerateOtpDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.RestorePasswordDto;
import com.github.gribanoveu.cuddly.controllers.facade.AccountControllerFacade;
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
