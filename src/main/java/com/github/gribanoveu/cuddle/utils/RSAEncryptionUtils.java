package com.github.gribanoveu.cuddle.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * Генерация ключевой пары и сертификатов с помощью OpenSSL.
 * <p>
 * Для генерации ключевой пары выполните следующие команды:
 * <pre>{@code
 * openssl genrsa -out keypair.pem 2048
 * openssl rsa -in keypair.pem -pubout -out public.pem
 * openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
 * }</pre>
 * Затем поместите сертификаты в папку resources/certs.
 *
 * @author Evgeny Gribanov
 * @version 24.10.2023
 */
@Component
@RequiredArgsConstructor
public class RSAEncryptionUtils {
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
        return Base64.getEncoder().encodeToString(bytes);
    }

    private byte[] base64StringToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

}
