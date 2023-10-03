package com.github.gribanoveu.auth.controllers.facade;

import com.github.gribanoveu.auth.controllers.dtos.request.PermissionDto;
import com.github.gribanoveu.auth.controllers.dtos.request.UpdatePermissionDto;
import com.github.gribanoveu.auth.controllers.dtos.response.PermissionsResponse;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import com.github.gribanoveu.auth.entities.services.contract.PermissionService;
import com.github.gribanoveu.auth.entities.tables.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Evgeny Gribanov
 * @version 25.09.2023
 */
@Service
@RequiredArgsConstructor
public class PermissionControllerFacade {
    private final PermissionService permissionService;

    public ResponseEntity<PermissionsResponse> getAllPermissions(int pageNumber, int pageSize) {
        return ResponseEntity.ok(PermissionsResponse.create(
                HttpStatus.OK, permissionService.getAllPermissions(pageNumber, pageSize)));
    }

    public ResponseEntity<StatusResponse> addPermission(PermissionDto request) {
        var permissionName = request.permissionName().toUpperCase();
        if (permissionService.permissionExistByName(permissionName))
            throw new CredentialEx(ResponseCode.PERMISSION_EXIST);

        var permission = new Permission();
        permission.setName(permissionName);

        permissionService.save(permission);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.PERMISSION_CREATED));
    }

    public ResponseEntity<StatusResponse> deletePermission(String permissionName) {
        permissionService.deletePermissionByName(permissionName);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.PERMISSION_DELETED));
    }

    public ResponseEntity<StatusResponse> updatePermission(UpdatePermissionDto request) {
        var permissionId = permissionService.findPermissionByName(request.permissionName()).getId();
        var updated = permissionService.updatePermissionName(permissionId, request.newName());
        if (updated) return ResponseEntity.ok(StatusResponse.create(ResponseCode.PERMISSION_UPDATED));
        throw new CredentialEx(ResponseCode.PERMISSION_NOT_UPDATED);
    }
}
