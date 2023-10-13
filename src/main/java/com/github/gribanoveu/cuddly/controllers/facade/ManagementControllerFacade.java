package com.github.gribanoveu.cuddly.controllers.facade;

import com.github.gribanoveu.cuddly.constants.Constants;
import com.github.gribanoveu.cuddly.controllers.dtos.request.RegisterDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.controllers.dtos.response.UsersResponse;
import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddly.entities.services.contract.PermissionService;
import com.github.gribanoveu.cuddly.entities.services.contract.UserService;
import com.github.gribanoveu.cuddly.entities.tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author Evgeny Gribanov
 * @version 26.09.2023
 */
@Service
@RequiredArgsConstructor
public class ManagementControllerFacade {
    private final UserService userService;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllUsersList(int pageNumber, int pageSize) {
        return ResponseEntity.ok(UsersResponse.create(OK, userService.getAllUsers(pageNumber, pageSize)));
    }

    public ResponseEntity<?> registerUser(RegisterDto request) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(ResponseCode.PASSWORD_NOT_EQUALS);
        if (userService.userExistByEmail(request.email())) throw new CredentialEx(ResponseCode.USER_ALREADY_EXIST);

        var user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPermissionCollection(Set.of(permissionService.getDefaultUserPermission()));

        userService.saveUser(user);
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_CREATED, StatusLevel.SUCCESS));
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

    public ResponseEntity<?> resetUserPasswordToDefault(Long userId) {
        var user = userService.findUserById(userId);
        userService.updateUserPasswordAndCredentialsExpiredById(user,
                passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DEFAULT_PASSWORD, StatusLevel.SUCCESS));
    }
}