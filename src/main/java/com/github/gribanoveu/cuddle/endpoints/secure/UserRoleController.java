package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.dtos.response.auth.TokenResponse;
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

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить роль пользователя")
    @GetMapping("/{userId}")
    public ResponseEntity<TokenResponse> getUserRole(@PathVariable Long userId) {
        return null;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до модератора")
    @PatchMapping("/{userId}")
    public ResponseEntity<TokenResponse> updateToModerator(@PathVariable Long userId) {
        return null;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до пользователя")
    @DeleteMapping("/{userId}")
    public ResponseEntity<TokenResponse> updateToUser(@PathVariable Long userId) {
        return null;
    }
}
