package com.github.gribanoveu.cuddly.controllers.facade.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RegisterDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.StatusResponse;
import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.Role;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.StatusLevel;
import com.github.gribanoveu.cuddly.entities.services.user.UserService;
import com.github.gribanoveu.cuddly.entities.tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Service
@RequiredArgsConstructor
public class UserControllerFacade {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getUserData(Authentication authentication) {
        var userData = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(userData);
    }

    public ResponseEntity<?> registerUser(RegisterDto request) {
        if (!request.password().equals(request.confirmPassword())) throw new CredentialEx(ResponseCode.PASSWORD_NOT_EQUALS);
        if (userService.userExistByEmail(request.email())) throw new CredentialEx(ResponseCode.USER_ALREADY_EXIST);

        var user = new User();
        user.setEmail(request.email());
        user.setBirthDate(LocalDate.parse(request.birthDate(), DateTimeFormatter.ISO_LOCAL_DATE)); // yyyy-MM-dd
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        userService.saveUser(user);

        var location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/v1/user/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created (location).body(StatusResponse.create(
                ResponseCode.USER_CREATED, StatusLevel.SUCCESS));
    }

    public ResponseEntity<?> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getName());
        return ResponseEntity.ok(StatusResponse.create(ResponseCode.USER_DELETED, StatusLevel.SUCCESS));
    }
}

