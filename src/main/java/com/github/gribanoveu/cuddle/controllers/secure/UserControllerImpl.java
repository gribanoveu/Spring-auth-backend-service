package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.entities.services.EmailService;
import com.github.gribanoveu.cuddle.entities.services.UserService;
import com.github.gribanoveu.cuddle.entities.tables.User;
import com.github.gribanoveu.cuddle.exeptions.CredentialEx;
import com.github.gribanoveu.cuddle.utils.emails.EmailTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserControllerImpl {
    private final UserService userService;
    private final EmailService emailService;

    public ResponseEntity<User> getUserData(Authentication authentication) {
        var userData = userService.findUserByEmail(authentication.getName());
        if (userData.getProfileCreated()) return ResponseEntity.ok(userData);
        throw new CredentialEx(ResponseCode.PROFILE_NOT_CREATED);
    }

    public ResponseEntity<StatusResponse> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getName());
        emailService.sendMail(EmailTemplates.simpleEmail(authentication.getName(),
                EmailMessages.deleteSubject, EmailMessages.deleteSelfTemplate));
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DELETED, StatusLevel.SUCCESS));
    }
}