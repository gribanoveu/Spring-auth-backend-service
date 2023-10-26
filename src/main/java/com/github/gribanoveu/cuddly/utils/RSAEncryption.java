package com.github.gribanoveu.cuddly.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Evgeny Gribanov
 * @version 24.10.2023
 */
@Component
@RequiredArgsConstructor
public class RSAEncryption {
    private static final String ALGORITHM = "RSA";

    public String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return bytesToBase64String(encryptedBytes);
    }

    public String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = base64StringToBytes(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private String bytesToBase64String(byte[] bytes) {
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }

    private byte[] base64StringToBytes(String base64String) {
        return java.util.Base64.getDecoder().decode(base64String);
    }

}