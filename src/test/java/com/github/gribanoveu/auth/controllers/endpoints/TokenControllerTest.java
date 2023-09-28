package com.github.gribanoveu.auth.controllers.endpoints;

import com.github.gribanoveu.auth.BaseMockMvcTest;
import com.github.gribanoveu.auth.controllers.dtos.request.LoginDto;
import com.github.gribanoveu.auth.entities.enums.TokenType;
import com.github.gribanoveu.auth.entities.services.implementation.TokenServiceImpl;
import com.github.gribanoveu.auth.security.CustomUserDetails;
import com.github.gribanoveu.auth.security.CustomUserDetailsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Evgeny Gribanov
 * @version 12.09.2023
 */
class TokenControllerTest extends BaseMockMvcTest {
    @Autowired private TokenServiceImpl tokenService;
    @Autowired private CustomUserDetailsService usrDetailsService;

    @Test
    public void testCreateAuthToken_validData_createToken() throws Exception {
        var request = new LoginDto("admin@email.com", "Qwerty123");

        this.mockMvc.perform(post("/v1/token/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    public void testCreateAuthToken_adminData_extractRolesAndPermissions() throws Exception {
        var request = new LoginDto("admin@email.com", "Qwerty123");

        var result = this.mockMvc.perform(post("/v1/token/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();

        var accessToken = testJsonUtils.getJsonValueFromMvcResult(result, "accessToken");
        var position = tokenService.extractClaim(accessToken,"position");
        var permissions = tokenService.extractClaimsAsList(accessToken, "scope");

        Assertions.assertThat(position).isEqualTo("ADMIN");
        Assertions.assertThat(permissions).isEqualTo(
                List.of("AU_USERS_MANAGEMENT", "AU_MAIN_INFO_VIEW", "AU_PERMISSIONS_MANAGEMENT"));
    }

    @Test
    public void testCreateAuthToken_userData_extractRolesAndPermissions() throws Exception {
        var request = new LoginDto("user@email.com", "Qwerty123");

        var result = this.mockMvc.perform(post("/v1/token/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();

        var accessToken = testJsonUtils.getJsonValueFromMvcResult(result, "accessToken");
        var position = tokenService.extractClaim(accessToken,"position");
        var permissions = tokenService.extractClaimsAsList(accessToken, "scope");

        Assertions.assertThat(permissions).isEqualTo(List.of("AU_MAIN_INFO_VIEW"));
        Assertions.assertThat(position).isEqualTo("USER");
    }

    @Test
    void testAuthToken_credentialsBadUser_throwException() throws Exception {
        var request = new LoginDto("some@mail.com", "password");

        this.mockMvc.perform(post("/v1/token/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJsonUtils.convertDtoToJson(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.reason").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    public void testCreateRefreshToken_adminData_extractRolesAndPermissions() throws Exception {
        var user = (CustomUserDetails) usrDetailsService.loadUserByUsername("admin@email.com");
        var refreshToken = tokenService.generateToken(user, TokenType.REFRESH);

        var result = this.mockMvc.perform(get("/v1/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + refreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();

        var accessToken = testJsonUtils.getJsonValueFromMvcResult(result, "accessToken");
        var position = tokenService.extractClaim(accessToken,"position");
        var permissions = tokenService.extractClaimsAsList(accessToken, "scope");

        Assertions.assertThat(position).isEqualTo("ADMIN");
        Assertions.assertThat(permissions).isEqualTo(
                List.of("AU_USERS_MANAGEMENT", "AU_MAIN_INFO_VIEW", "AU_PERMISSIONS_MANAGEMENT"));
    }

    @Test
    public void testCreateRefreshToken_adminData_extractRefreshTokenTime() throws Exception {
        var user = (CustomUserDetails) usrDetailsService.loadUserByUsername("admin@email.com");
        var refreshToken = tokenService.generateToken(user, TokenType.REFRESH);

        var result = this.mockMvc.perform(get("/v1/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + refreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("OK"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();

        var accessToken = testJsonUtils.getJsonValueFromMvcResult(result, "accessToken");
        var exp = tokenService.extractExpire(accessToken);
        var tokenLifetime = tokenService.getRefreshTokenLifetime();

        var refreshDateTime = testJsonUtils.calculateTokenDateTimeFromDate(exp, tokenLifetime);
        Assertions.assertThat(refreshDateTime).isAfter(LocalDateTime.now());
    }
}