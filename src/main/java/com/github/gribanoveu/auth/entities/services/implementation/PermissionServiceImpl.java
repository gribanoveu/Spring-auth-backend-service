package com.github.gribanoveu.auth.entities.services.implementation;

import com.github.gribanoveu.auth.constants.ErrorMessages;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.enums.Permissions;
import com.github.gribanoveu.auth.entities.repositories.PermissionRepository;
import com.github.gribanoveu.auth.entities.services.contract.PermissionService;
import com.github.gribanoveu.auth.entities.tables.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
                new CredentialEx(ErrorMessages.PERMISSION_NOT_EXIST, HttpStatus.NOT_FOUND));
    }

    @Override
    public void deletePermissionByName(String permissionName) {
        var permission = permissionName.toUpperCase();
        if (permissionRepository.existsByName(permission)) permissionRepository.deleteByName(permission);
        else throw new CredentialEx(ErrorMessages.PERMISSION_NOT_EXIST, HttpStatus.NOT_FOUND);
    }

    @Override
    public Boolean updatePermissionName(Long id, String permissionName) {
        return permissionRepository.updatePermissionName(id, permissionName) > 0;
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

