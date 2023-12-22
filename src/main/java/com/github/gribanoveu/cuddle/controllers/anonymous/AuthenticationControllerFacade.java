package com.github.gribanoveu.cuddle.controllers.anonymous;

import com.github.gribanoveu.cuddle.constants.EmailMessages;
import com.github.gribanoveu.cuddle.dtos.enums.TokenType;
import com.github.gribanoveu.cuddle.dtos.request.auth.LoginDto;
import com.github.gribanoveu.cuddle.dtos.request.auth.RefreshTokenDto;
import com.github.gribanoveu.cuddle.dtos.response.auth.TokenResponse;
import com.github.gribanoveu.cuddle.entities.services.email.EmailService;
import com.github.gribanoveu.cuddle.entities.services.token.TokenService;
import com.github.gribanoveu.cuddle.security.CustomUserDetails;
import com.github.gribanoveu.cuddle.security.CustomUserDetailsService;
import com.github.gribanoveu.cuddle.utils.RefreshTokenUtils;
import com.github.gribanoveu.cuddle.utils.emails.EmailTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationControllerFacade {
    @Value("${time-variable.accessTokenLifetime}")
    private Duration accessTokenLifetime;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final CustomUserDetailsService usrDetailsService;
    private final RefreshTokenUtils refreshTokenUtils;
    private final EmailService emailService;

    public ResponseEntity<TokenResponse> authenticateUser(LoginDto request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authManager.authenticate(authenticationToken);
        var user = (CustomUserDetails) auth.getPrincipal();
        var accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        var encryptedRefreshToken = refreshTokenUtils.generateEncryptedRefreshToken(request.email());
        emailService.sendMail(EmailTemplates.simpleEmail(request.email(),
                EmailMessages.loginSubject, EmailMessages.loginTemplate));

        return ResponseEntity.ok(TokenResponse.create(accessTokenLifetime.toSeconds(),
                accessToken, encryptedRefreshToken)); // encrypted token so that it is not used as a main token
    }

    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenDto request) {
        var email = refreshTokenUtils.validateRefreshTokenAndExtractEmail(request.refreshToken());
        var user = (CustomUserDetails) usrDetailsService.loadUserByUsername(email);
        var accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        var encryptedRefreshToken = refreshTokenUtils.generateEncryptedRefreshToken(email);

        return ResponseEntity.ok(TokenResponse.create(accessTokenLifetime.toSeconds(),
                accessToken, encryptedRefreshToken));
    }
}
