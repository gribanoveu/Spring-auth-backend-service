package com.github.gribanoveu.cuddly.entities.services.implementation;

import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.Permissions;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.repositories.PermissionRepository;
import com.github.gribanoveu.cuddly.entities.services.contract.PermissionService;
import com.github.gribanoveu.cuddly.entities.tables.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Evgeny Gribanov
 * @version 21.09.2023
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public Permission getDefaultUserPermission() {
        return permissionRepository.findByName(Permissions.AU_MAIN_INFO_VIEW.name())
                .orElseGet(() -> permissionRepository.save(
                        new Permission(Permissions.AU_USERS_MANAGEMENT.name())));
    }

    @Override
    public List<Permission> getAllPermissions(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return permissionRepository.findAll(pageable);
    }

    @Override
    public Permission findPermissionByName(String name) {
        return permissionRepository.findByName(name).orElseThrow(() ->
                new CredentialEx(ResponseCode.PERMISSION_NOT_EXIST));
    }

    @Override
    public void deletePermissionByName(String permissionName) {
        var permission = permissionName.toUpperCase();
        if (permissionRepository.existsByName(permission)) permissionRepository.deleteByName(permission);
        else throw new CredentialEx(ResponseCode.PERMISSION_NOT_EXIST);
    }

    @Override
    public Permission updatePermissionName(String permissionName, String newName) {
        var permission = findPermissionByName(permissionName);
        permission.setName(newName);
        return permissionRepository.save(permission);
    }

    @Override
    public boolean permissionExistByName(String name) {
        return permissionRepository.existsByName(name);
    }

    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }
}
