package com.github.gribanoveu.auth.controllers.endpoints;

import com.github.gribanoveu.auth.controllers.dtos.request.RegisterDto;
import com.github.gribanoveu.auth.controllers.facade.ManagementControllerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/manage-user")
public class ManagementController {
    private final ManagementControllerFacade managementControllerFacade;

    @PostMapping("/add")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto request) {
        return managementControllerFacade.registerUser(request);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return managementControllerFacade.deleteUser(userId);
    }

    @PatchMapping("/{userId}/block")
    public ResponseEntity<?> blockUser(@PathVariable Long userId) {
        return managementControllerFacade.blockUser(userId);
    }

    @PatchMapping("/{userId}/unlock")
    public ResponseEntity<?> unblockUser(@PathVariable Long userId) {
        return managementControllerFacade.unlockUser(userId);
    }

    @PatchMapping("/{userId}/reset-password")
    public ResponseEntity<?> resetUserPasswordToDefault(@PathVariable Long userId) {
        return managementControllerFacade.resetUserPasswordToDefault(userId);
    }
}
