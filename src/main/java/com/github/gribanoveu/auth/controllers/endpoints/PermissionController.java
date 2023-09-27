package com.github.gribanoveu.auth.controllers.endpoints;

import com.github.gribanoveu.auth.controllers.dtos.request.PermissionDto;
import com.github.gribanoveu.auth.controllers.dtos.request.UpdatePermissionDto;
import com.github.gribanoveu.auth.controllers.facade.PermissionControllerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 08.09.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/permission")
public class PermissionController {
    private final PermissionControllerFacade permissionControllerFacade;

    @GetMapping()
    public ResponseEntity<?> getPermission(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return permissionControllerFacade.getAllPermissions(pageNumber, pageSize);
    }

    @PostMapping
    public ResponseEntity<?> addPermission(@Valid @RequestBody PermissionDto request) {
        return permissionControllerFacade.addPermission(request);
    }

    @PutMapping
    public ResponseEntity<?> updatePermission(@Valid @RequestBody UpdatePermissionDto request) {
        return permissionControllerFacade.updatePermission(request);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePermission(@RequestParam String permissionName) {
        return permissionControllerFacade.deletePermission(permissionName);
    }
}