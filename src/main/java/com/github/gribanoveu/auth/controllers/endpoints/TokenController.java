package com.github.gribanoveu.auth.controllers.endpoints;

import com.github.gribanoveu.auth.controllers.dtos.request.LoginDto;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.controllers.dtos.response.TokenResponse;
import com.github.gribanoveu.auth.controllers.facade.TokenControllerFacade;
import jakarta.annotation.security.PermitAll;
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
@RequestMapping("/v1/token")
public class TokenController {
    private final TokenControllerFacade tokenControllerFacade;

    @PostMapping("/issue")
    public ResponseEntity<TokenResponse> authenticateUser(@Valid @RequestBody LoginDto request) {
        return tokenControllerFacade.authenticateUser(request);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request) {
        return tokenControllerFacade.refreshToken(request);
    }
}
