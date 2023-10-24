package com.github.gribanoveu.cuddly.controllers.endpoints.admin;

import com.github.gribanoveu.cuddly.controllers.facade.admin.ModeratorControllerFacade;
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
