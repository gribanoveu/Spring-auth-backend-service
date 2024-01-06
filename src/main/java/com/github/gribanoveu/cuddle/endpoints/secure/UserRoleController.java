package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.secure.UserRoleControllerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping
    public ResponseEntity<?> getUserRole(@RequestParam String email) {
        return userRoleControllerImpl.getUserRole(email);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить список всех модераторов")
    @GetMapping("/moders")
    public ResponseEntity<?> getModersList(@PageableDefault Pageable pageable) {
        return userRoleControllerImpl.getModerList(pageable);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до модератора")
    @PatchMapping
    public ResponseEntity<?> updateToModerator(@RequestParam String email) {
        return userRoleControllerImpl.updateToModerator(email);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до пользователя")
    @DeleteMapping
    public ResponseEntity<?> updateToUser(@RequestParam String email) {
        return userRoleControllerImpl.updateToUser(email);
    }
}
