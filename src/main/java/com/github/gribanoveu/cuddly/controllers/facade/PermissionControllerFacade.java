package com.github.gribanoveu.cuddly.controllers.facade;

import com.github.gribanoveu.cuddly.controllers.dtos.request.PermissionDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.UpdatePermissionDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.PermissionsResponse;
import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddly.entities.services.contract.PermissionService;
import com.github.gribanoveu.cuddly.entities.tables.Permission;
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
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.PERMISSION_CREATED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<StatusResponse> deletePermission(String permissionName) {
        permissionService.deletePermissionByName(permissionName);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.PERMISSION_DELETED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<StatusResponse> updatePermission(UpdatePermissionDto request) {
        permissionService.updatePermissionName(request.permissionName(), request.newName());
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.PERMISSION_UPDATED, StatusLevel.SUCCESS));
    }
}
