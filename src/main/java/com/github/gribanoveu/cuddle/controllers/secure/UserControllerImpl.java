package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.entities.services.user.UserService;
import com.github.gribanoveu.cuddle.entities.tables.User;
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

    public ResponseEntity<User> getUserData(Authentication authentication) {
        var userData = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(userData);
    }

    public ResponseEntity<StatusResponse> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getName());
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DELETED, StatusLevel.SUCCESS));
    }
}

