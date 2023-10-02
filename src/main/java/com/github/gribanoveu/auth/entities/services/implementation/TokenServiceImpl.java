package com.github.gribanoveu.auth.entities.services.implementation;

import com.github.gribanoveu.auth.constants.ErrorMessages;
import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.enums.TokenType;
import com.github.gribanoveu.auth.entities.services.contract.TokenService;
import com.github.gribanoveu.auth.security.CustomUserDetails;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author Evgeny Gribanov
 * @version 30.08.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor @Getter
public class TokenServiceImpl implements TokenService {

    @Value("${time-variable.accessTokenLifetime}")
    private Duration accessTokenLifetime;
    @Value("${time-variable.refreshTokenLifetime}")
    private Duration refreshTokenLifetime;

    private final JwtEncoder jwtEncoder;

    @Override
    public String generateToken(CustomUserDetails usrDetails, TokenType tokenType) {
        var now = Instant.now();
        var permissions = usrDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        var builder = JwtClaimsSet.builder()
                .issuedAt(now)
                .subject(usrDetails.getUsername())
                .claim("position", usrDetails.getUserPosition())
                .claim("scope", permissions);
        // !! if change 'scope' to another name, authorities will be null
        // all authorities have 'SCOPE_' prefix by default

        switch (tokenType) {
            case ACCESS -> builder.expiresAt(now.plus(accessTokenLifetime));
            case REFRESH -> builder.expiresAt(now.plus(refreshTokenLifetime));
        }

        JwtClaimsSet claims = builder.build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String extractSubject(String token) {
        try {
            var claimsSet = getJwtClaimsSetFromToken(token);
            return claimsSet.getSubject();
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage());
        }
        throw new CredentialEx(ErrorMessages.TOKEN_NOT_VALID, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public List<String> extractClaimsAsList(String token, String claim) {
        try {
            var claimsSet = getJwtClaimsSetFromToken(token);
            return claimsSet.getStringListClaim(claim);
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage());
            throw new CredentialEx(ErrorMessages.TOKEN_NOT_VALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public String extractClaim(String token, String claim) {
        try {
            var claimsSet = getJwtClaimsSetFromToken(token);
            return claimsSet.getStringClaim(claim);
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage());
            throw new CredentialEx(ErrorMessages.TOKEN_NOT_VALID, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public Date extractExpire(String token) {
        try {
            var claimsSet = getJwtClaimsSetFromToken(token);
            return claimsSet.getExpirationTime();
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage());
            throw new CredentialEx(ErrorMessages.TOKEN_NOT_VALID, HttpStatus.UNAUTHORIZED);
        }

    }

    private JWTClaimsSet getJwtClaimsSetFromToken(String token) throws ParseException {
        if (token == null) throw new CredentialEx(ErrorMessages.TOKEN_NOT_VALID, HttpStatus.UNAUTHORIZED);
        var decodedJWT = SignedJWT.parse(token);
        return decodedJWT.getJWTClaimsSet();
    }
}