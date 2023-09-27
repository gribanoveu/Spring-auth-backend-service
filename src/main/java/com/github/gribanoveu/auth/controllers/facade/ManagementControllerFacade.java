package com.github.gribanoveu.auth.controllers.facade;

import com.github.gribanoveu.auth.controllers.dtos.request.RegisterDto;
import com.github.gribanoveu.auth.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.services.contract.PermissionService;
import com.github.gribanoveu.auth.entities.services.contract.UserService;
import com.github.gribanoveu.auth.entities.tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.github.gribanoveu.auth.constants.ErrorMessages.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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

    public ResponseEntity<?> registerUser(RegisterDto request) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(PASSWORD_NOT_EQUALS, BAD_REQUEST);
        if (userService.userExistByEmail(request.email())) throw new CredentialEx(USER_ALREADY_EXIST, BAD_REQUEST);

        var user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPermissionCollection(Set.of(permissionService.getDefaultUserPermission()));

        userService.saveUser(user);
        return ResponseEntity.ok(StatusResponse.create(OK, USER_CREATED));
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(StatusResponse.create(OK, USER_DELETED));
    }

    public ResponseEntity<?> blockUser(Long userId) {
        return null;
    }

    public ResponseEntity<?> unlockUser(Long userId) {
        return null;
    }


    public ResponseEntity<?> resetUserPasswordToDefault(Long userId) {
        return null;
    }
}
