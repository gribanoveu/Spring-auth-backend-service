package com.github.gribanoveu.auth.entities.enums;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 * default permissions, need to be in database by default
 */
public enum Permissions {
    AU_PERMISSIONS_MANAGEMENT,
    AU_USERS_MANAGEMENT,
    AU_MAIN_INFO_VIEW;

    public String scope() {
        return "SCOPE_" + name();
    }
}
