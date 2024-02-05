package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.constants.Constants;
import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.enums.BanReason;
import com.github.gribanoveu.cuddle.dtos.enums.DisableReason;
import com.github.gribanoveu.cuddle.dtos.request.RestrictionDto;
import com.github.gribanoveu.cuddle.dtos.response.UsersResponse;
import com.github.gribanoveu.cuddle.entities.services.EmailService;
import com.github.gribanoveu.cuddle.entities.services.UserService;
import com.github.gribanoveu.cuddle.exeptions.errors.ModeratorMessage;
import com.github.gribanoveu.cuddle.exeptions.errors.UserMessage;
import com.github.gribanoveu.cuddle.exeptions.responses.RestResponse;
import com.github.gribanoveu.cuddle.utils.emails.EmailTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<UsersResponse> getAllUsersList(Pageable pageable) {
        return ResponseEntity.ok(UsersResponse.create(userService.getAllUsers(pageable)));
    }

    public ResponseEntity<RestResponse> deleteUser(String email) {
        var user = userService.findUserByEmail(email);
        userService.deleteUserByEmail(email);
        emailService.sendMail(EmailTemplates.simpleEmail(user.getEmail(),
                EmailMessages.deleteSubject, EmailMessages.deleteModerTemplate));
        return ResponseEntity.ok(RestResponse.create(UserMessage.USER_DELETED));
    }

    public ResponseEntity<RestResponse> disableUser(RestrictionDto request) {
        var user = userService.findUserByEmail(request.userEmail()); // todo
        var disableReason = DisableReason.getDisableReasonByCode(request.reasonCode());
        userService.disableUser(user, disableReason.getCode());
        emailService.sendMail(EmailTemplates.disableUser(user.getEmail(), disableReason.getMessage()));
        return ResponseEntity.ok(RestResponse.create(ModeratorMessage.USER_DISABLED));
    }

    public ResponseEntity<RestResponse> enabledUser(String email) {
        var user = userService.findUserByEmail(email);
        userService.enabledUser(user);
        emailService.sendMail(EmailTemplates.simpleEmail(user.getEmail(),
                EmailMessages.enabledSubject, EmailMessages.enabledTemplate));
        return ResponseEntity.ok(RestResponse.create(ModeratorMessage.USER_ENABLED));
    }

    public ResponseEntity<RestResponse> banUser(RestrictionDto request) {
        var user = userService.findUserByEmail(request.userEmail());
        var banReason = BanReason.getBanExpirationByCode(request.reasonCode());
        userService.lockUser(user, banReason.getBanExpiration().withNano(0), request.reasonCode());
        emailService.sendMail(EmailTemplates.banUser(user.getEmail(), banReason.getMessage(),
                banReason.getBanExpiration().format(Constants.DEFAULT_TIME_FORMAT)));
        return ResponseEntity.ok(RestResponse.create(ModeratorMessage.USER_BANNED));
    }

    public ResponseEntity<RestResponse> mercyUser(String email) {
        var user = userService.findUserByEmail(email);
        userService.unlockUser(user);
        emailService.sendMail(EmailTemplates.simpleEmail(user.getEmail(),
                EmailMessages.enabledSubject, EmailMessages.enabledTemplate));
        return ResponseEntity.ok(RestResponse.create(ModeratorMessage.USER_UN_BANNED));
    }
}