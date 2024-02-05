package com.github.gribanoveu.cuddle.endpoints.secure;

import com.github.gribanoveu.cuddle.controllers.secure.UserRoleControllerImpl;
import com.github.gribanoveu.cuddle.dtos.response.UsersResponse;
import com.github.gribanoveu.cuddle.exeptions.responses.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evgeny Gribanov
 * @version 21.12.2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/role")
@Tag(name="Контроллер для назначения модераторов", description="Управляет ролями пользователя")
public class UserRoleController {
    private final UserRoleControllerImpl userRoleControllerImpl;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить роль пользователя")
    @GetMapping
    public ResponseEntity<RestResponse> getUserRole(@RequestParam String email) {
        return userRoleControllerImpl.getUserRole(email);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получить список всех модераторов")
    @GetMapping("/moders")
    public ResponseEntity<UsersResponse> getModersList(@ParameterObject @PageableDefault Pageable pageable) {
        return userRoleControllerImpl.getModerList(pageable);
    }

    @Operation(summary = "Получить список всех пользователей")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/users")
    public ResponseEntity<UsersResponse> getUsersList(@ParameterObject @PageableDefault Pageable pageable) {
        return userRoleControllerImpl.getAllUsersList(pageable);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до модератора")
    @PatchMapping
    public ResponseEntity<RestResponse> updateToModerator(@RequestParam String email) {
        return userRoleControllerImpl.updateToModerator(email);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновить роль до пользователя")
    @DeleteMapping
    public ResponseEntity<RestResponse> updateToUser(@RequestParam String email) {
        return userRoleControllerImpl.updateToUser(email);
    }
}
