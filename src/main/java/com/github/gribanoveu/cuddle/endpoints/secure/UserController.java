package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.secure.UserControllerFacade;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.entities.tables.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Tag(name="Контроллер пользователя", description="Юзер управляет своим аккаунтом")
public class UserController {
    private final UserControllerFacade userControllerFacade;

    @Operation(summary = "Получить данные текущего пользователя")
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<User> getUserData(Authentication authentication) {
        return userControllerFacade.getUserData(authentication);
    }

    @Operation(summary = "Удалить текущего авторизованного пользователя")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping
    public ResponseEntity<StatusResponse> deleteUser(Authentication authentication) {
        return userControllerFacade.deleteUser(authentication);
    }
}