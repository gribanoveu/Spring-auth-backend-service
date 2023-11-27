package com.github.gribanoveu.cuddly.controllers.endpoints.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.LoginDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RefreshTokenDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.auth.TokenResponse;
import com.github.gribanoveu.cuddly.controllers.facade.auth.AuthControllerFacade;
import com.github.gribanoveu.cuddly.utils.aspects.LogRequest;
import com.github.gribanoveu.cuddly.utils.aspects.LogResponse;
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
public class AuthController {
    private final AuthControllerFacade authControllerFacade;

    @PostMapping
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody LoginDto request) {
        return authControllerFacade.authenticateUser(request);
    }

    @PatchMapping
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenDto request) {
        return authControllerFacade.refreshToken(request);
    }
}
