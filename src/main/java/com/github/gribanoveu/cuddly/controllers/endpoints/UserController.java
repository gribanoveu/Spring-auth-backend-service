package com.github.gribanoveu.cuddly.controllers.endpoints;

import com.github.gribanoveu.cuddly.controllers.facade.UserControllerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}