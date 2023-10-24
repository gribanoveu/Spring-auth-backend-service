package com.github.gribanoveu.cuddly.controllers.endpoints.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RegisterDto;
import com.github.gribanoveu.cuddly.controllers.facade.auth.UserControllerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 * !! authorization service stores only user data for authentication
 * and manages his permissions, all data such as name and surname
 * are stored in other services by user's email !!
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private final UserControllerFacade userControllerFacade;

    @GetMapping
    public ResponseEntity<?> getUserData(Authentication authentication) {
        return userControllerFacade.getUserData(authentication);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto request) {
        return userControllerFacade.registerUser(request);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        return userControllerFacade.deleteUser(authentication);
    }
}