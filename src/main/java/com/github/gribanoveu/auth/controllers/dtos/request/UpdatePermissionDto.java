package com.github.gribanoveu.auth.controllers.dtos.request;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 */
public record UpdatePermissionDto(
        String permissionName,
        String newName
) {
    @Override
    public String permissionName() {
        return permissionName.toUpperCase();
    }

    @Override
    public String newName() {
        return newName.toUpperCase();
    }
}
