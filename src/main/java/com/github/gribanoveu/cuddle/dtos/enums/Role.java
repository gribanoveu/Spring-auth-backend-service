package com.github.gribanoveu.cuddle.dtos.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 * default permissions, need to be in database by default
 */
public enum Role implements GrantedAuthority {
    USER,
    MODERATOR,
    ADMIN;

    public String scope() {
        return "SCOPE_" + name();
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
