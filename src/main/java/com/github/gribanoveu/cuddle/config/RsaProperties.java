package com.github.gribanoveu.cuddle.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author Evgeny Gribanov
 * @version 30.08.2023
 */
@ConfigurationProperties(prefix = "rsa")
public record RsaProperties(
        RSAPrivateKey privateKey,
        RSAPublicKey publicKey,
        String keyId
) {}
