package com.github.gribanoveu.cuddle.utils;

import com.github.gribanoveu.cuddle.config.RsaProperties;
import com.github.gribanoveu.cuddle.constants.RegexpFormat;
import com.github.gribanoveu.cuddle.exeptions.errors.AuthMessage;
import com.github.gribanoveu.cuddle.exeptions.responses.RestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Evgeny Gribanov
 * @version 24.10.2023
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenUtils {
    @Value("${time-variable.refreshTokenLifetime}")
    private Duration refreshTokenLifetime;
    private final RsaProperties rsaKeys;
    private final RSAEncryptionUtils rsaEncryptionUtils;

    public String generateEncryptedRefreshToken(String email) {
        RSAPublicKey publicKey = rsaKeys.publicKey();
        try {
            final String TOKEN_SEPARATOR = "|"; // admin@email.com|1698159942361
            var refreshTokenExpire = Instant.now().plus(refreshTokenLifetime).toEpochMilli();
            var refreshToken = email + TOKEN_SEPARATOR + refreshTokenExpire;
            return rsaEncryptionUtils.encrypt(refreshToken, publicKey);
        } catch (Exception e) {
            throw new RestException(AuthMessage.TOKEN_NOT_VALID);
        }
    }

    public String validateRefreshTokenAndExtractEmail(String token) {
        RSAPrivateKey privateKey = rsaKeys.privateKey();
        try {
            var tokenValue = rsaEncryptionUtils.decrypt(token, privateKey);
            checkTokenValidity(tokenValue);
            return extractEmailFromToken(tokenValue);
        } catch (Exception e) {
            throw new RestException(AuthMessage.TOKEN_NOT_VALID);
        }
    }

    private void checkTokenValidity(String tokenValue) {
        Pattern datePattern = Pattern.compile(RegexpFormat.UNIX_DATE_TOKEN_PATTERN);
        Matcher dateMatcher = datePattern.matcher(tokenValue);
        if (dateMatcher.find()) {
            var refreshTokenDateInMillis = Long.parseLong(dateMatcher.group(1));
            if (Instant.now().toEpochMilli() > refreshTokenDateInMillis) {
                throw new RestException(AuthMessage.TOKEN_NOT_VALID);
            }
        }
    }

    private String extractEmailFromToken(String tokenValue) {
        Pattern emailPattern = Pattern.compile(RegexpFormat.EMAIL_TOKEN_PATTERN);
        Matcher emailMatcher = emailPattern.matcher(tokenValue);
        if (emailMatcher.find()) {
            return emailMatcher.group(1);
        }
        throw new RestException(AuthMessage.TOKEN_NOT_VALID);
    }
}
