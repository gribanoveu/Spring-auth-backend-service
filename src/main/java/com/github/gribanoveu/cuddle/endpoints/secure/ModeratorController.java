package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.secure.ModeratorControllerImpl;
import com.github.gribanoveu.cuddle.dtos.request.RestrictionDto;
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
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/moderator")
@Tag(name="Контроллер для модерации", description="Управляет ограничениями пользователя")
public class ModeratorController {
    private final ModeratorControllerImpl moderatorControllerImpl;

    @Operation(summary = "Получить список всех пользователей")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/users-list")
    public ResponseEntity<?> getAllUsersList(@PageableDefault Pageable pageable) {
        return moderatorControllerImpl.getAllUsersList(pageable);
    }

    @Operation(summary = "Удалить пользователя")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        return moderatorControllerImpl.deleteUser(email);
    }

    @Operation(summary = "Отключить пользователя")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/disable")
    public ResponseEntity<?> disableUser(@RequestBody RestrictionDto request) {
        return moderatorControllerImpl.disableUser(request);
    }

    @Operation(summary = "Включить пользователя")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/enable")
    public ResponseEntity<?> enableUser(@RequestParam String email) {
        return moderatorControllerImpl.enabledUser(email);
    }

    @Operation(summary = "Выдать бан пользователю")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/ban")
    public ResponseEntity<?> banUserReq(@RequestBody RestrictionDto request) {
        return moderatorControllerImpl.banUser(request);
    }

    @Operation(summary = "Снять бан с пользователя")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/mercy")
    public ResponseEntity<?> mercyUser(@RequestParam String email) {
        return moderatorControllerImpl.mercyUser(email);
    }
}
