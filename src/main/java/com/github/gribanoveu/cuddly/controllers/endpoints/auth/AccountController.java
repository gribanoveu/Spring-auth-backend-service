package com.github.gribanoveu.cuddly.controllers.endpoints.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.ChangeEmailDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.ChangePasswordDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.GenerateOtpDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RestorePasswordDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.controllers.facade.auth.AccountControllerFacade;
import com.github.gribanoveu.cuddly.utils.aspects.LogRequest;
import com.github.gribanoveu.cuddly.utils.aspects.LogResponse;
import jakarta.servlet.http.HttpServletRequest;
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

    @LogRequest
    @LogResponse
    @PatchMapping("/change-email")
    public ResponseEntity<StatusResponse> changeEmail(@Valid @RequestBody ChangeEmailDto request, Authentication auth) {
        return userControllerFacade.changeEmail(request, auth);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<StatusResponse> changePassword(@Valid @RequestBody ChangePasswordDto request, Authentication auth) {
        return userControllerFacade.changePassword(request, auth);
    }

    @LogRequest
    @LogResponse
    @PostMapping("/generate-code") // anonymous access
    public ResponseEntity<StatusResponse> generateOtpCode(@Valid @RequestBody GenerateOtpDto request, HttpServletRequest http) {
        return userControllerFacade.generateOtpCode(request, http);
    }

    @PostMapping("/restore-password") // anonymous access
    public ResponseEntity<StatusResponse> restorePassword(@Valid @RequestBody RestorePasswordDto request) {
        return userControllerFacade.restorePasswordByOtp(request);
    }
}
