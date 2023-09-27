package com.github.gribanoveu.auth.controllers.facade;

import com.github.gribanoveu.auth.controllers.dtos.request.ChangeEmailDto;
import com.github.gribanoveu.auth.controllers.dtos.request.ChangePasswordDto;
import com.github.gribanoveu.auth.controllers.dtos.request.GenerateOtpDto;
import com.github.gribanoveu.auth.controllers.dtos.request.RestorePasswordDto;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.services.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.github.gribanoveu.auth.constants.ErrorMessages.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@Service
@RequiredArgsConstructor
public class AccountControllerFacade {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // user change email from app, authenticated
    public ResponseEntity<?> changeEmail(ChangeEmailDto request, Authentication authentication) {
        if (!authentication.isAuthenticated()) throw new CredentialEx(FORBIDDEN, BAD_REQUEST);

        var userId = userService.findUserByEmail(authentication.getName()).getId();
        var updated = userService.updateEmail(userId, request.email());
        if (updated) return ResponseEntity.ok(StatusResponse.create(OK, USER_UPDATED));

        throw new CredentialEx(USER_NOT_UPDATED, BAD_REQUEST);
    }

    // check that password and confirm password match
    // find user, if not exist -> error
    // verify that the old password matches, if not -> error // todo make email code confirm
    // check that old password not equals new password, else -> error
    // change password
    public ResponseEntity<?> changePassword(ChangePasswordDto request, Authentication authentication) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(PASSWORD_NOT_EQUALS, BAD_REQUEST);

        var user = userService.findUserByEmail(authentication.getName());
        if (passwordEncoder.matches(request.oldPassword(), user.getPassword()))
            if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                var updated = userService.updatePassword(
                        authentication.getName(), passwordEncoder.encode(request.password()));

                if (updated) return ResponseEntity.ok(StatusResponse.create(OK, PASSWORD_UPDATED));
            } else throw new CredentialEx(PASSWORD_EQUALS, BAD_REQUEST);

        throw new CredentialEx(OLD_PASSWORD_NOT_MATCH, BAD_REQUEST);
    }

    // user open restore form and enter email
    // service find user by email, generate and save otp code to db (with lifetime) todo find db (mb redis)
    // send email with code
    public ResponseEntity<?> generateOtpCode(GenerateOtpDto request) {
        return null;
    }

    // next screen user enter otp code and new password and re-enter password again
    // service check that code exist and have valid lifetime
    // service find userId by otp code and change password in db
    // send successful email
    public ResponseEntity<?> restorePasswordByOtp(RestorePasswordDto request) {
        return null;
    }
}
