package com.github.gribanoveu.cuddle.controllers.secure;

import com.github.gribanoveu.cuddle.dtos.enums.Role;
import com.github.gribanoveu.cuddle.dtos.response.UsersResponse;
import com.github.gribanoveu.cuddle.entities.services.UserService;
import com.github.gribanoveu.cuddle.exeptions.errors.AuthMessage;
import com.github.gribanoveu.cuddle.exeptions.errors.ModeratorMessage;
import com.github.gribanoveu.cuddle.exeptions.responses.RestException;
import com.github.gribanoveu.cuddle.exeptions.responses.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Evgeny Gribanov
 * @version 22.12.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleControllerImpl {
    private final UserService userService;

    public ResponseEntity<RestResponse> getUserRole(String email) {
        var userRole = userService.findUserByEmail(email).getRole().getAuthority();
        return ResponseEntity.ok(RestResponse.create(userRole));
    }

    public ResponseEntity<RestResponse> updateToModerator(String email) {
        var user = userService.findUserByEmail(email);
        if (user.getRole().equals(Role.ADMIN)) throw new RestException(AuthMessage.ACCESS_DENIED);
        userService.updateRole(user, Role.MODERATOR);
        return ResponseEntity.ok(RestResponse.create(ModeratorMessage.PERMISSION_UPDATED_MODERATOR));
    }

    public ResponseEntity<RestResponse> updateToUser(String email) {
        var user = userService.findUserByEmail(email);
        if (user.getRole().equals(Role.ADMIN)) throw new RestException(AuthMessage.ACCESS_DENIED);
        userService.updateRole(user, Role.USER);
        return ResponseEntity.ok(RestResponse.create(ModeratorMessage.PERMISSION_UPDATED_USER));
    }

    public ResponseEntity<UsersResponse> getModerList(Pageable pageable) {
        return ResponseEntity.ok(UsersResponse.create(userService.getAllModers(pageable)));
    }

    public ResponseEntity<UsersResponse> getAllUsersList(Pageable pageable) {
        return ResponseEntity.ok(UsersResponse.create(userService.getAllUsers(pageable)));
    }
}
