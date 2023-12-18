package com.github.gribanoveu.cuddly.controllers.endpoints.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RegisterDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.controllers.facade.auth.UserControllerFacade;
import com.github.gribanoveu.cuddly.entities.tables.User;
import com.github.gribanoveu.cuddly.utils.aspects.LogRequest;
import com.github.gribanoveu.cuddly.utils.aspects.LogResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Контроллер пользователя", description="Юзер управляет своим аккаунтом")
public class UserController {
    private final UserControllerFacade userControllerFacade;

    @LogRequest
    @LogResponse
    @GetMapping
    @Operation(summary = "Получить данные текущего пользователя")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<User> getUserData(Authentication authentication) {
        return userControllerFacade.getUserData(authentication);
    }

    @LogRequest
    @LogResponse
    @PostMapping
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<StatusResponse> registerUser(@Valid @RequestBody RegisterDto request) {
        return userControllerFacade.registerUser(request);
    }

    @DeleteMapping
    @Operation(summary = "Удалить текущего авторизованного пользователя", description = "Пользователь удаляет сам себя")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<StatusResponse> deleteUser(Authentication authentication) {
        return userControllerFacade.deleteUser(authentication);
    }
}