package com.github.gribanoveu.cuddly.controllers.endpoints.admin;

import com.github.gribanoveu.cuddly.controllers.facade.admin.AdminControllerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class AdminController {
    private final AdminControllerFacade adminControllerFacade;

    @GetMapping
    public ResponseEntity<?> getAllUsersList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return adminControllerFacade.getAllUsersList(pageNumber, pageSize);
    }

    @DeleteMapping("/{userId}") // only admin can delete user
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return adminControllerFacade.deleteUser(userId);
    }

    @PatchMapping("/{userId}/disable")
    public ResponseEntity<?> disableUser(@PathVariable Long userId) {
        return adminControllerFacade.disableUser(userId);
    }

    @PatchMapping("/{userId}/enable")
    public ResponseEntity<?> enableUser(@PathVariable Long userId) {
        return adminControllerFacade.enabledUser(userId);
    }

    @PatchMapping("/{userId}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long userId) {
        return adminControllerFacade.banUser(userId);
    }

    @PatchMapping("/{userId}/mercy")
    public ResponseEntity<?> mercyUser(@PathVariable Long userId) {
        return adminControllerFacade.mercyUser(userId);
    }
}
