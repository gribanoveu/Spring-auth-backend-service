package com.github.gribanoveu.cuddly.controllers.facade.auth;

import com.github.gribanoveu.cuddly.constants.EmailMessages;
import com.github.gribanoveu.cuddly.constants.RegexpFormat;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.LoginDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RefreshTokenDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.auth.TokenResponse;
import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.TokenType;
import com.github.gribanoveu.cuddly.entities.services.email.EmailService;
import com.github.gribanoveu.cuddly.entities.services.token.TokenService;
import com.github.gribanoveu.cuddly.security.CustomUserDetails;
import com.github.gribanoveu.cuddly.security.CustomUserDetailsService;
import com.github.gribanoveu.cuddly.utils.RSAEncryption;
import com.github.gribanoveu.cuddly.config.RsaProperties;
import com.github.gribanoveu.cuddly.utils.RefreshTokenUtils;
import com.github.gribanoveu.cuddly.utils.emails.EmailTemplates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthControllerFacade {
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
