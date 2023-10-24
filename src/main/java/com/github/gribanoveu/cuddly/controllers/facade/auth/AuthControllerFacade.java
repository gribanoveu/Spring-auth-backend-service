package com.github.gribanoveu.cuddly.controllers.facade.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.LoginDto;
import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.RefreshTokenDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.auth.TokenResponse;
import com.github.gribanoveu.cuddly.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.cuddly.entities.enums.ResponseCode;
import com.github.gribanoveu.cuddly.entities.enums.TokenType;
import com.github.gribanoveu.cuddly.entities.services.token.TokenService;
import com.github.gribanoveu.cuddly.security.CustomUserDetails;
import com.github.gribanoveu.cuddly.security.CustomUserDetailsService;
import com.github.gribanoveu.cuddly.utils.RSAEncryption;
import com.github.gribanoveu.cuddly.config.RsaProperties;
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

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthControllerFacade {
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final CustomUserDetailsService usrDetailsService;
    private final RsaProperties rsaKeys;
    private final RSAEncryption rsaEncryption;

    @Value("${time-variable.accessTokenLifetime}") private Duration accessTokenLifetime;

    public ResponseEntity<TokenResponse> authenticateUser(LoginDto request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authManager.authenticate(authenticationToken);
        var user = (CustomUserDetails) auth.getPrincipal();
        var accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        var refreshToken = generateRefreshToken(request.email());

        return ResponseEntity.ok(TokenResponse.create(accessTokenLifetime.toSeconds(), accessToken, refreshToken));
    }

    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenDto request) {
        var email = encryptRefreshToken(request.refreshToken());
        var user = (CustomUserDetails) usrDetailsService.loadUserByUsername(email);
        var accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        var refreshToken = generateRefreshToken(email);

        return ResponseEntity.ok(TokenResponse.create(accessTokenLifetime.toSeconds(), accessToken, refreshToken));
    }

    private String generateRefreshToken(String email) {
        RSAPublicKey publicKey = rsaKeys.publicKey();
        try {
            return rsaEncryption.encrypt(email, publicKey);
        } catch (Exception e) {
            throw new CredentialEx(ResponseCode.TOKEN_NOT_VALID);
        }
    }

    private String encryptRefreshToken(String token) {
        RSAPrivateKey privateKey = rsaKeys.privateKey();
        try {
            return rsaEncryption.decrypt(token, privateKey);
        } catch (Exception e) {
            throw new CredentialEx(ResponseCode.TOKEN_NOT_VALID);
        }
    }

}
