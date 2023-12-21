package com.github.gribanoveu.cuddle.controllers.endpoints.admin;

import com.github.gribanoveu.cuddle.controllers.facade.admin.ModeratorControllerFacade;
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
@Tag(name="Контороллер для модерации", description="Управляет ограничениями пользователя")
public class ModeratorController {
    private final ModeratorControllerFacade moderatorControllerFacade;

    @GetMapping("/users-list")
    public ResponseEntity<?> getAllUsersList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return moderatorControllerFacade.getAllUsersList(pageNumber, pageSize);
    }

    @DeleteMapping("/{userId}") // only admin can delete user
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return moderatorControllerFacade.deleteUser(userId);
    }

    @PatchMapping("/{userId}/disable")
    public ResponseEntity<?> disableUser(@PathVariable Long userId) {
        return moderatorControllerFacade.disableUser(userId);
    }

    @PatchMapping("/{userId}/enable")
    public ResponseEntity<?> enableUser(@PathVariable Long userId) {
        return moderatorControllerFacade.enabledUser(userId);
    }

    @PatchMapping("/{userId}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long userId) {
        return moderatorControllerFacade.banUser(userId);
    }

    @PatchMapping("/{userId}/mercy")
    public ResponseEntity<?> mercyUser(@PathVariable Long userId) {
        return moderatorControllerFacade.mercyUser(userId);
    }
}
