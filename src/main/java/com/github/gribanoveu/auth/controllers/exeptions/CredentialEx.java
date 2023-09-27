package com.github.gribanoveu.auth.controllers.exeptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author Evgeny Gribanov
 * @version 07.07.2023
 */
@Getter
public class CredentialEx extends BadCredentialsException {
    private final HttpStatus status;

    public CredentialEx(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
