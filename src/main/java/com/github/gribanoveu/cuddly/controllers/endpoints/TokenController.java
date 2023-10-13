package com.github.gribanoveu.cuddly.controllers.endpoints;

import com.github.gribanoveu.cuddly.controllers.dtos.request.LoginDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.TokenResponse;
import com.github.gribanoveu.cuddly.controllers.facade.TokenControllerFacade;
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
