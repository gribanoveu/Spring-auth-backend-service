package com.github.gribanoveu.cuddly.controllers.facade.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.ChangeEmailDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.ChangePasswordDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.GenerateOtpDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RestorePasswordDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddly.entities.services.email.EmailService;
import com.github.gribanoveu.cuddly.entities.services.otp.RedisOtpService;
import com.github.gribanoveu.cuddly.entities.services.user.UserService;
import com.github.gribanoveu.cuddly.utils.JsonUtils;
import com.github.gribanoveu.cuddly.utils.emails.RestorePasswordEmailTemplates;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountControllerFacade {
    @Value("${time-variable.otpCodeLifetime}")
    private Duration otpCodeLifeTime;
    private final UserService userService;
    private final RedisOtpService redisOtpService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JsonUtils jsonUtils;

    // user change email from app, authenticated
    public ResponseEntity<StatusResponse> changeEmail(ChangeEmailDto request, Authentication authentication) {
        var user = userService.findUserByEmail(authentication.getName());
        userService.updateEmail(user, request.email());
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_UPDATED, StatusLevel.SUCCESS));
    }

    // check that password and confirm password match
    // find user, if not exist -> error
    // verify that the old password matches, if not -> error
    // check that old password not equals new password, else -> error
    // change password
    public ResponseEntity<StatusResponse> changePassword(ChangePasswordDto request, Authentication authentication) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(ResponseCode.PASSWORD_NOT_EQUALS);

        var user = userService.findUserByEmail(authentication.getName());
        if (passwordEncoder.matches(request.oldPassword(), user.getPassword()))
            if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                userService.updatePasswordByEmail(user, passwordEncoder.encode(request.password()));
                return ResponseEntity.ok(StatusResponse.create(ResponseCode.PASSWORD_UPDATED, StatusLevel.SUCCESS));
            } else throw new CredentialEx(ResponseCode.PASSWORD_EQUALS);

        throw new CredentialEx(ResponseCode.OLD_PASSWORD_NOT_MATCH);
    }

    // user open restore form and enter email
    // service find user by email, if user not found -> error
    // service find otp code, if exist -> error
    // else create new code
    // send email, if not -> error
    // save otp code to db (with lifetime)
    public ResponseEntity<StatusResponse> generateOtpCode(GenerateOtpDto request, HttpServletRequest http) {
        var userExist = userService.userExistByEmail(request.email());
        if (!userExist) throw new CredentialEx(ResponseCode.USER_NOT_EXIST);
        var otpCode = jsonUtils.generateRandomOtpCode().toString();
        log.info("RequestId: {}. Generate OTP code. Email {}, Code {}", http.getRequestId(), request.email(), otpCode);

        emailService.sendMail(RestorePasswordEmailTemplates.generateOtpEmail(request.email(), otpCode, otpCodeLifeTime));
        log.info("RequestId: {}. Email with code send to: {}", http.getRequestId(), request.email());

        redisOtpService.saveOptCode(request.email(), otpCode, otpCodeLifeTime);
        log.info("RequestId: {}. OTP code to email: {} saved", http.getRequestId(), request.email());

        return ResponseEntity.ok(StatusResponse.create(ResponseCode.OTP_CODE_CREATED, StatusLevel.SUCCESS));
    }

    // next screen user enter otp code and new password and re-enter password again
    // service check that code exist and have valid lifetime
    // service find userId by otp code and change password in db
    // send successful email
    public ResponseEntity<StatusResponse> restorePasswordByOtp(RestorePasswordDto request) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(ResponseCode.PASSWORD_NOT_EQUALS);
        if (!redisOtpService.otpCodeValid(request.email(), request.otpCode())) throw new CredentialEx(ResponseCode.OTP_CODE_NOT_FOUND);

        var user = userService.findUserByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            userService.updatePasswordByEmail(user, passwordEncoder.encode(request.password()));
            redisOtpService.deleteOtpCode(request.email());
            emailService.sendMail(RestorePasswordEmailTemplates.passwordChangedEmail(request.email()));
            log.info("Password change message sent to: {}", request.email());
            return ResponseEntity.ok(StatusResponse.create(ResponseCode.PASSWORD_UPDATED, StatusLevel.SUCCESS));
        }
        throw new CredentialEx(ResponseCode.PASSWORD_EQUALS);
    }
}
