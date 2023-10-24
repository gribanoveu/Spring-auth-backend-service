package com.github.gribanoveu.cuddly.controllers.facade.auth;

import com.github.gribanoveu.cuddly.controllers.dtos.request.auth.LoginDto;
import com.github.gribanoveu.cuddly.controllers.dtos.response.auth.TokenResponse;
import com.github.gribanoveu.cuddly.entities.enums.TokenType;
import com.github.gribanoveu.cuddly.entities.services.token.TokenService;
import com.github.gribanoveu.cuddly.security.CustomUserDetails;
import com.github.gribanoveu.cuddly.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@Service
@RequiredArgsConstructor
public class TokenControllerFacade {
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final CustomUserDetailsService usrDetailsService;
    @Value("${time-variable.accessTokenLifetime}") private Duration accessTokenLifetime;

    public ResponseEntity<TokenResponse> authenticateUser(LoginDto request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authManager.authenticate(authenticationToken);
        var user = (CustomUserDetails) auth.getPrincipal();
        var accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        var refreshToken = tokenService.generateToken(user, TokenType.REFRESH);

        return ResponseEntity.ok(TokenResponse.create(accessTokenLifetime.toSeconds(), accessToken, refreshToken));
    }

    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request) {
        var headerAuth = request.getHeader("Authorization");
        var bearerTokenFromHeader = headerAuth.substring(7);
        var email = tokenService.extractSubject(bearerTokenFromHeader);
        var user = (CustomUserDetails) usrDetailsService.loadUserByUsername(email);
        var accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        var refreshToken = tokenService.generateToken(user, TokenType.REFRESH);

        return ResponseEntity.ok(TokenResponse.create(accessTokenLifetime.toSeconds(), accessToken, refreshToken));
    }

}
