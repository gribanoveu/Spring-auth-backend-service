package com.github.gribanoveu.auth.controllers.facade;

import com.github.gribanoveu.auth.constants.ErrorMessages;
import com.github.gribanoveu.auth.controllers.dtos.request.ChangeEmailDto;
import com.github.gribanoveu.auth.controllers.dtos.request.ChangePasswordDto;
import com.github.gribanoveu.auth.controllers.dtos.request.GenerateOtpDto;
import com.github.gribanoveu.auth.controllers.dtos.request.RestorePasswordDto;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.services.contract.RedisService;
import com.github.gribanoveu.auth.entities.services.contract.UserService;
import com.github.gribanoveu.auth.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.github.gribanoveu.auth.constants.ErrorMessages.*;
import static org.springframework.http.HttpStatus.*;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@Service
@RequiredArgsConstructor
public class AccountControllerFacade {
    @Value("${time-variable.otpCodeLifetime}")
    private Duration otpCodeLifeTime;
    private final UserService userService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final JsonUtils jsonUtils;

    // user change email from app, authenticated
    public ResponseEntity<?> changeEmail(ChangeEmailDto request, Authentication authentication) {
        if (!authentication.isAuthenticated()) throw new CredentialEx(ErrorMessages.FORBIDDEN, BAD_REQUEST);

        var userId = userService.findUserByEmail(authentication.getName()).getId();
        var updated = userService.updateEmail(userId, request.email());
        if (updated) return ResponseEntity.ok(StatusResponse.create(OK, USER_UPDATED));

        throw new CredentialEx(USER_NOT_UPDATED, BAD_REQUEST);
    }

    // check that password and confirm password match
    // find user, if not exist -> error
    // verify that the old password matches, if not -> error
    // check that old password not equals new password, else -> error
    // change password
    public ResponseEntity<?> changePassword(ChangePasswordDto request, Authentication authentication) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(PASSWORD_NOT_EQUALS, BAD_REQUEST);

        var user = userService.findUserByEmail(authentication.getName());
        if (passwordEncoder.matches(request.oldPassword(), user.getPassword()))
            if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                var updated = userService.updatePasswordByEmail(
                        authentication.getName(), passwordEncoder.encode(request.password()));

                if (updated) return ResponseEntity.ok(StatusResponse.create(OK, PASSWORD_UPDATED));
            } else throw new CredentialEx(PASSWORD_EQUALS, BAD_REQUEST);

        throw new CredentialEx(OLD_PASSWORD_NOT_MATCH, BAD_REQUEST);
    }

    // user open restore form and enter email
    // service find user by email,
    // if user not found -> error
    // service find otp code
    // if exist -> error
    // else create new code
    // save otp code to db (with lifetime)
    // todo send email with code
    public ResponseEntity<?> generateOtpCode(GenerateOtpDto request) {
        var userExist = userService.userExistByEmail(request.email());
        if (!userExist) throw new CredentialEx(USER_NOT_EXIST, BAD_REQUEST);
        var otpCode = jsonUtils.generateRandomOtpCode();
        redisService.saveOptCode(request.email(), otpCode, otpCodeLifeTime);
        return ResponseEntity.ok(StatusResponse.create(OK, OTP_CODE_CREATED));
    }

    // next screen user enter otp code and new password and re-enter password again
    // service check that code exist and have valid lifetime
    // service find userId by otp code and change password in db
    // send successful email
    public ResponseEntity<?> restorePasswordByOtp(RestorePasswordDto request) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(PASSWORD_NOT_EQUALS, BAD_REQUEST);
        if (!redisService.otpCodeValid(request.email(), request.otpCode()))
            throw new CredentialEx(OTP_CODE_NOT_FOUND, NOT_FOUND);

        var user = userService.findUserByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            var updated = userService.updatePasswordByEmail(request.email(), passwordEncoder.encode(request.password()));
            redisService.deleteOtpCode(request.email());
            if (updated) return ResponseEntity.ok(StatusResponse.create(OK, PASSWORD_UPDATED));
        }
        throw new CredentialEx(PASSWORD_EQUALS, BAD_REQUEST);
    }
}
