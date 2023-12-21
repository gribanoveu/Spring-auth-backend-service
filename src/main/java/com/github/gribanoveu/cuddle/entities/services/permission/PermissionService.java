package com.github.gribanoveu.cuddle.entities.services.permission;

import com.github.gribanoveu.cuddle.entities.enums.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Evgeny Gribanov
 * @version 21.09.2023
 */
public interface PermissionService {
    Role getDefaultUserPermission();
    List<Role> getAllPermissions(int pageNumber, int pageSize);
    Role findPermissionByName(String name);
    boolean permissionExistByName(String name);
    @Transactional void save(Role permission);
    @Transactional void deletePermissionByName(String roleName);
    @Transactional
    void updatePermissionName(String roleName, String newName);
}
