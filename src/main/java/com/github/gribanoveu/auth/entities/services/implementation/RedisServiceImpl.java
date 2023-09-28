package com.github.gribanoveu.auth.entities.services.implementation;

import com.github.gribanoveu.auth.controllers.exeptions.CredentialEx;
import com.github.gribanoveu.auth.entities.services.contract.RedisService;
import com.github.gribanoveu.auth.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

import static com.github.gribanoveu.auth.constants.ErrorMessages.OTP_CODE_EXIST;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

/**
 * @author Evgeny Gribanov
 * @version 28.09.2023
 */
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonUtils jsonUtils;

    @Override
    public void saveOptCode(String email, int code, Duration codeDuration) {
        var otpCode = getOtpCode(email);
        if (otpCode.isPresent()) throw new CredentialEx(
            String.format(OTP_CODE_EXIST, codeDuration.toMinutes()), TOO_MANY_REQUESTS);
        redisTemplate.opsForValue().set(email, code, codeDuration);
    }

    @Override
    public Optional<Integer> getOtpCode(String email) {
        var code = (Integer) redisTemplate.opsForValue().get(email);
        return Optional.ofNullable(code);
    }
}
