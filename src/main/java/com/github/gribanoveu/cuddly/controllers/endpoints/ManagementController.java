package com.github.gribanoveu.cuddly.controllers.endpoints;

import com.github.gribanoveu.cuddly.controllers.dtos.request.RegisterDto;
import com.github.gribanoveu.cuddly.controllers.facade.ManagementControllerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-manage")
public class ManagementController {
    private final ManagementControllerFacade managementControllerFacade;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsersList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return managementControllerFacade.getAllUsersList(pageNumber, pageSize);
    }

    @PostMapping("/add") // only admin can add new users
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto request) {
        return managementControllerFacade.registerUser(request);
    }

    @DeleteMapping("/{userId}") // only admin can delete user
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return managementControllerFacade.deleteUser(userId);
    }

    @PatchMapping("/{userId}/disable")
    public ResponseEntity<?> disableUser(@PathVariable Long userId) {
        return managementControllerFacade.disableUser(userId);
    }

    @PatchMapping("/{userId}/enable")
    public ResponseEntity<?> enableUser(@PathVariable Long userId) {
        return managementControllerFacade.enabledUser(userId);
    }

    @PatchMapping("/{userId}/reset-password")
    public ResponseEntity<?> resetUserPasswordToDefault(@PathVariable Long userId) {
        return managementControllerFacade.resetUserPasswordToDefault(userId);
    }
}
