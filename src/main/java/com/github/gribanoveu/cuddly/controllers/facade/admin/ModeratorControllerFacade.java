package com.github.gribanoveu.cuddly.controllers.facade.admin;

import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.controllers.dtos.response.auth.UsersResponse;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddly.entities.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 */
@Service
@RequiredArgsConstructor
public class ModeratorControllerFacade {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllUsersList(int pageNumber, int pageSize) {
        return ResponseEntity.ok(UsersResponse.create(OK, userService.getAllUsers(pageNumber, pageSize)));
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DELETED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> disableUser(Long userId) {
        var user = userService.findUserById(userId);
        userService.updateEnabled(user, false);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DISABLED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> enabledUser(Long userId) {
        var user = userService.findUserById(userId);
        userService.updateEnabled(user, true);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_ENABLED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> banUser(Long userId) {
        var user = userService.findUserById(userId);
        userService.updateLocked(user, true);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_BANNED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> mercyUser(Long userId) {
        var user = userService.findUserById(userId);
        userService.updateLocked(user, false);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_UN_BANNED, StatusLevel.SUCCESS));
    }
}