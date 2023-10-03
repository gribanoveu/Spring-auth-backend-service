package com.github.gribanoveu.auth.controllers.exeptions;

import com.github.gribanoveu.auth.controllers.dtos.response.ResponseDetails;
import com.github.gribanoveu.auth.entities.enums.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Evgeny Gribanov
 * @version 07.07.2023
 */
@Getter
public class CredentialEx extends BadCredentialsException {
    private final HttpStatus status;
    private final Collection<ResponseDetails> error;

    public CredentialEx(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.status = responseCode.getStatus();
        this.error = Collections.singletonList(new ResponseDetails(responseCode));
    }
}
