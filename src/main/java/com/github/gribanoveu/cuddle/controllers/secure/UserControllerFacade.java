package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.request.auth.RegisterDto;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.exeptions.CredentialEx;
import com.github.gribanoveu.cuddle.dtos.enums.Role;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
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

    public ResponseEntity<StatusResponse> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getName());
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DELETED, StatusLevel.SUCCESS));
    }
}

