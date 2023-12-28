package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.constants.Constants;
import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.enums.BanReason;
import com.github.gribanoveu.cuddle.dtos.enums.DisableReason;
import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.dtos.request.RestrictionDto;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.dtos.response.auth.UsersResponse;
import com.github.gribanoveu.cuddle.entities.services.email.EmailService;
import com.github.gribanoveu.cuddle.entities.services.user.UserService;
import com.github.gribanoveu.cuddle.utils.emails.EmailTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModeratorControllerImpl {
    private final UserService userService;
    private final EmailService emailService;

    public ResponseEntity<?> getAllUsersList(Pageable pageable) {
        return ResponseEntity.ok(UsersResponse.create(OK, userService.getAllUsers(pageable)));
    }

    public ResponseEntity<?> deleteUser(String email) {
        var user = userService.findUserByEmail(email);
        userService.deleteUserByEmail(email);
        emailService.sendMail(EmailTemplates.simpleEmail(user.getEmail(),
                EmailMessages.deleteSubject, EmailMessages.deleteModerTemplate));
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DELETED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> disableUser(RestrictionDto request) {
        var user = userService.findUserByEmail(request.userEmail()); // todo
        var disableReason = DisableReason.getDisableReasonByCode(request.reasonCode());
        userService.disableUser(user, disableReason.getCode());
        emailService.sendMail(EmailTemplates.disableUser(user.getEmail(), disableReason.getMessage()));
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DISABLED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> enabledUser(String email) {
        var user = userService.findUserByEmail(email);
        userService.enabledUser(user);
        emailService.sendMail(EmailTemplates.simpleEmail(user.getEmail(),
                EmailMessages.enabledSubject, EmailMessages.enabledTemplate));
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_ENABLED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> banUser(RestrictionDto request) {
        var user = userService.findUserByEmail(request.userEmail());
        var banReason = BanReason.getBanExpirationByCode(request.reasonCode());
        userService.lockUser(user, banReason.getBanExpiration().withNano(0), request.reasonCode());
        emailService.sendMail(EmailTemplates.banUser(user.getEmail(), banReason.getMessage(),
                banReason.getBanExpiration().format(Constants.DEFAULT_TIME_FORMAT)));
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_BANNED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> mercyUser(String email) {
        var user = userService.findUserByEmail(email);
        userService.unlockUser(user);
        emailService.sendMail(EmailTemplates.simpleEmail(user.getEmail(),
                EmailMessages.enabledSubject, EmailMessages.enabledTemplate));
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_UN_BANNED, StatusLevel.SUCCESS));
    }
}