package com.github.gribanoveu.cuddle.controllers.anonymous;

import com.github.gribanoveu.cuddle.constants.Constants;
import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.enums.BanReason;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.Role;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.dtos.request.*;
import com.github.gribanoveu.cuddle.dtos.response.ResponseDetails;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.entities.services.EmailService;
import com.github.gribanoveu.cuddle.entities.services.RedisOtpService;
import com.github.gribanoveu.cuddle.entities.services.UserService;
import com.github.gribanoveu.cuddle.entities.tables.User;
import com.github.gribanoveu.cuddle.exeptions.CredentialEx;
import com.github.gribanoveu.cuddle.utils.JsonUtils;
import com.github.gribanoveu.cuddle.utils.emails.EmailTemplates;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Evgeny Gribanov
 * @version 22.09.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PublicAccountControllerImpl {
    @Value("${time-variable.otpCodeLifetime}")
    private Duration otpCodeLifeTime;
    private final UserService userService;
    private final RedisOtpService redisOtpService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JsonUtils jsonUtils;

    public ResponseEntity<StatusResponse> registerUser(RegisterDto request) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(ResponseCode.PASSWORD_NOT_EQUALS);
        if (userService.userExistByEmail(request.email())) throw new CredentialEx(ResponseCode.USER_ALREADY_EXIST);

        var user = new User();
        user.setEmail(request.email());
        user.setBirthDate(LocalDate.parse(request.birthDate(), DateTimeFormatter.ISO_LOCAL_DATE)); // yyyy-MM-dd
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        userService.saveUser(user);
        log.info("User saved: {}", user.getEmail());

        var location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/v1/user/{id}")
                .buildAndExpand(user.getId()).toUri();

        emailService.sendMail(EmailTemplates.simpleEmail(request.email(),
                EmailMessages.userRegisteredSubject, EmailMessages.userRegisteredTemplate));
        log.info("Register confirm send to email: {}", request.email());

        return ResponseEntity.created (location).body(StatusResponse.create(
                ResponseCode.USER_CREATED, StatusLevel.SUCCESS));
    }

    // user change email from app, authenticated
	public ResponseEntity<StatusResponse> changeEmail(ChangeEmailDto request, Authentication authentication) {
		var user = userService.findUserByEmail(authentication.getName());
		var oldEmail = user.getEmail();
        if (request.email().equals(oldEmail)) throw new CredentialEx(ResponseCode.EMAIL_ALREADY_EXIST);
		userService.updateEmail(user, request.email());
		emailService.sendMail(EmailTemplates.emailChanged(oldEmail, request.email()));
		log.info("Email change old email: {}, new email: {}", oldEmail, request.email());
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

                emailService.sendMail(EmailTemplates.simpleEmail(user.getEmail(),
                        EmailMessages.passwordChangedSubject, EmailMessages.passwordChangedTemplate));
                log.info("Password change message sent to: {}", user.getEmail());

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

        redisOtpService.saveOptCode(request.email(), otpCode, otpCodeLifeTime);
        log.info("RequestId: {}. OTP code to email: {} saved", http.getRequestId(), request.email());

        emailService.sendMail(EmailTemplates.generateOtpEmail(request.email(), otpCode, otpCodeLifeTime));
        log.info("RequestId: {}. Email with code send to: {}", http.getRequestId(), request.email());

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
            emailService.sendMail(EmailTemplates.simpleEmail(request.email(),
                    EmailMessages.passwordChangedSubject, EmailMessages.passwordChangedTemplate));
            log.info("Password change message sent to: {}", request.email());
            return ResponseEntity.ok(StatusResponse.create(ResponseCode.PASSWORD_UPDATED, StatusLevel.SUCCESS));
        }
        throw new CredentialEx(ResponseCode.PASSWORD_EQUALS);
    }

    public ResponseEntity<StatusResponse> getRestrictionsReason(String email) {
        var userExist = userService.userExistByEmail(email);
        if (!userExist) throw new CredentialEx(ResponseCode.USER_NOT_EXIST);
        var user = userService.findUserByEmail(email);
        if (user.getBanExpiration() == null) throw new CredentialEx(ResponseCode.NO_RESTRICTIONS);
        var reason = BanReason.getBanMessageByCode(user.getRestrictionReason());
        var responseText = String.format("Ваша учетная запись заблокирована по причине: %1$s. Блокировка истекает: %2$s",
                reason, user.getBanExpiration().format(Constants.DEFAULT_TIME_FORMAT));

        return ResponseEntity.ok(StatusResponse.create(new ResponseDetails(responseText), StatusLevel.SUCCESS));
    }
}
