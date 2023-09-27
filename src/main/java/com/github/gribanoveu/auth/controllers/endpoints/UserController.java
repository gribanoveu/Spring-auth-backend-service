package com.github.gribanoveu.auth.controllers.endpoints;

import com.github.gribanoveu.auth.controllers.dtos.request.ChangePasswordDto;
import com.github.gribanoveu.auth.controllers.dtos.request.RegisterDto;
import com.github.gribanoveu.auth.controllers.dtos.request.ChangeEmailDto;
import com.github.gribanoveu.auth.controllers.facade.UserControllerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsersList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return userControllerFacade.getAllUsersList(pageNumber, pageSize);
    }
}