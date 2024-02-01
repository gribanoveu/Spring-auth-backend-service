package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.dtos.enums.ResponseCode;
import com.github.gribanoveu.cuddle.dtos.enums.Role;
import com.github.gribanoveu.cuddle.dtos.enums.StatusLevel;
import com.github.gribanoveu.cuddle.dtos.response.ResponseDetails;
import com.github.gribanoveu.cuddle.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddle.dtos.response.UsersResponse;
import com.github.gribanoveu.cuddle.entities.services.UserService;
import com.github.gribanoveu.cuddle.exeptions.CredentialEx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author Evgeny Gribanov
 * @version 22.12.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleControllerImpl {
    private final UserService userService;

    public ResponseEntity<StatusResponse> getUserRole(String email) {
        var userRole = userService.findUserByEmail(email).getRole().getAuthority();
        return ResponseEntity.ok(StatusResponse.create(
                new ResponseDetails(userRole), StatusLevel.SUCCESS));
    }

    public ResponseEntity<StatusResponse> updateToModerator(String email) {
        var user = userService.findUserByEmail(email);
        if (user.getRole().equals(Role.ADMIN)) throw new CredentialEx(ResponseCode.ACCESS_DENIED);
        userService.updateRole(user, Role.MODERATOR);
        return ResponseEntity.ok(StatusResponse.create(
                ResponseCode.PERMISSION_UPDATED_MODERATOR, StatusLevel.SUCCESS));
    }

    public ResponseEntity<StatusResponse> updateToUser(String email) {
        var user = userService.findUserByEmail(email);
        if (user.getRole().equals(Role.ADMIN)) throw new CredentialEx(ResponseCode.ACCESS_DENIED);
        userService.updateRole(user, Role.USER);
        return ResponseEntity.ok(StatusResponse.create(
                ResponseCode.PERMISSION_UPDATED_USER, StatusLevel.SUCCESS));
    }

    public ResponseEntity<UsersResponse> getModerList(Pageable pageable) {
        return ResponseEntity.ok(UsersResponse.create(userService.getAllModers(pageable)));
    }

    public ResponseEntity<UsersResponse> getAllUsersList(Pageable pageable) {
        return ResponseEntity.ok(UsersResponse.create(userService.getAllUsers(pageable)));
    }
}
