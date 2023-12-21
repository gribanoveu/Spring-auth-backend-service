package com.github.gribanoveu.cuddle.controllers.facade.auth;

import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.controllers.dtos.request.auth.RegisterDto;
import com.github.gribanoveu.cuddle.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddle.entities.enums.Role;
import com.github.gribanoveu.cuddle.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddle.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddle.entities.services.email.EmailService;
import com.github.gribanoveu.cuddle.entities.services.user.UserService;
import com.github.gribanoveu.cuddle.entities.tables.User;
import com.github.gribanoveu.cuddle.utils.emails.EmailTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserControllerFacade {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ResponseEntity<User> getUserData(Authentication authentication) {
        var userData = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(userData);
    }

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

    public ResponseEntity<StatusResponse> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getName());
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DELETED, StatusLevel.SUCCESS));
    }
}

