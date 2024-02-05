package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.response.UserDataResponse;
import com.github.gribanoveu.cuddle.entities.services.EmailService;
import com.github.gribanoveu.cuddle.entities.services.UserService;
import com.github.gribanoveu.cuddle.exeptions.errors.UserMessage;
import com.github.gribanoveu.cuddle.exeptions.responses.RestResponse;
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

    public ResponseEntity<UserDataResponse> getUserData(Authentication authentication) {
        var userData = userService.findUserByEmail(authentication.getName());
        var resp = new UserDataResponse(userData.getUlid(), userData.getEmail(), userData.getBirthDate());
        return ResponseEntity.ok(resp);
    }

    public ResponseEntity<RestResponse> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getName());
        emailService.sendMail(EmailTemplates.simpleEmail(authentication.getName(),
                EmailMessages.deleteSubject, EmailMessages.deleteSelfTemplate));
        return ResponseEntity.ok(RestResponse.create(UserMessage.USER_DELETED));
    }
}