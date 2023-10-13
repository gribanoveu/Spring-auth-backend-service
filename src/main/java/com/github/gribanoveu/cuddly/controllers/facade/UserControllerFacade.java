package com.github.gribanoveu.cuddly.controllers.facade;

import com.github.gribanoveu.cuddly.entities.services.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Service
@RequiredArgsConstructor
public class UserControllerFacade {
    private final UserService userService;

    public ResponseEntity<?> getUserData(Authentication authentication) {
        var userData = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(userData);
    }
}

