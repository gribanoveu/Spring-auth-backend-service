package com.github.gribanoveu.cuddly.entities.services.contract;

import com.github.gribanoveu.cuddly.entities.tables.Permission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Evgeny Gribanov
 * @version 21.09.2023
 */
public interface PermissionService {
    Permission getDefaultUserPermission();
    List<Permission> getAllPermissions(int pageNumber, int pageSize);
    Permission findPermissionByName(String name);
    boolean permissionExistByName(String name);
    @Transactional void save(Permission permission);
    @Transactional void deletePermissionByName(String permissionName);
    @Transactional Permission updatePermissionName(String permissionName, String newName);
}
