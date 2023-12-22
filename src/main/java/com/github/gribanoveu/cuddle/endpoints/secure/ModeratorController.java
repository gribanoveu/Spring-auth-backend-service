package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.secure.ModeratorControllerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/moderator")
@Tag(name="Контроллер для модерации", description="Управляет ограничениями пользователя")
public class ModeratorController {
    private final ModeratorControllerFacade moderatorControllerFacade;

    @Operation(summary = "Получить список всех пользователей")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/users-list")
    public ResponseEntity<?> getAllUsersList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return moderatorControllerFacade.getAllUsersList(pageNumber, pageSize);
    }

    @Operation(summary = "Удалить пользователя")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{userId}") // only admin can delete user
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return moderatorControllerFacade.deleteUser(userId);
    }

    @Operation(summary = "Отключить пользователя")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{userId}/disable")
    public ResponseEntity<?> disableUser(@PathVariable Long userId) {
        return moderatorControllerFacade.disableUser(userId);
    }

    @Operation(summary = "Включить пользователя")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{userId}/enable")
    public ResponseEntity<?> enableUser(@PathVariable Long userId) {
        return moderatorControllerFacade.enabledUser(userId);
    }

    @Operation(summary = "Выдать бан пользователю")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{userId}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long userId) {
        return moderatorControllerFacade.banUser(userId);
    }

    @Operation(summary = "Снять бан с пользователя")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{userId}/mercy")
    public ResponseEntity<?> mercyUser(@PathVariable Long userId) {
        return moderatorControllerFacade.mercyUser(userId);
    }
}
