package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.secure.UserControllerImpl;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.dtos.response.UserDataResponse;
import com.github.gribanoveu.cuddle.utils.aspects.LogRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 * !! authorization service stores only user data for authentication
 * and manages his permissions, all data such as name and surname
 * are stored in other services by user's ulid !!
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
@Tag(name="Контроллер пользователя", description="Юзер управляет своим аккаунтом")
public class UserController {
    private final UserControllerImpl userControllerImpl;

    @Operation(summary = "Получить данные текущего пользователя")
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<UserDataResponse> getUserData(Authentication authentication) {
        return userControllerImpl.getUserData(authentication);
    }

    @Operation(summary = "Удалить текущего авторизованного пользователя")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping
    public ResponseEntity<StatusResponse> deleteUser(Authentication authentication) {
        return userControllerImpl.deleteUser(authentication);
    }
}