package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.secure.UserRoleControllerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 21.12.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/role")
@Tag(name="Контроллер для назначения модераторов", description="Управляет ролями пользователя")
public class UserRoleController {
    private final UserRoleControllerImpl userRoleControllerImpl;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить роль пользователя")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserRole(@PathVariable Long userId) {
        return userRoleControllerImpl.getUserRole(userId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до модератора")
    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateToModerator(@PathVariable Long userId) {
        return userRoleControllerImpl.updateToModerator(userId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до пользователя")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> updateToUser(@PathVariable Long userId) {
        return userRoleControllerImpl.updateToUser(userId);
    }
}
