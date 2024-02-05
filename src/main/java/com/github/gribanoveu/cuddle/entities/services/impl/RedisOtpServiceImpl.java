package com.github.gribanoveu.cuddle.entities.services.impl;

import com.github.gribanoveu.cuddle.entities.services.RedisOtpService;
import com.github.gribanoveu.cuddle.exeptions.errors.UserMessage;
import com.github.gribanoveu.cuddle.exeptions.responses.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Evgeny Gribanov
 * @version 28.09.2023
 */
@Service
@RequiredArgsConstructor
public class RedisOtpServiceImpl implements RedisOtpService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveOptCode(String email, String code, Duration codeDuration) {
        var otpCode = getOtpCode(email);
        if (otpCode.isPresent()) throw new RestException(UserMessage.OTP_CODE_EXIST);
        redisTemplate.opsForValue().set(email, code, codeDuration);
    }

    @Override
    public Optional<String> getOtpCode(String email) {
        var code = (String) redisTemplate.opsForValue().get(email);
        return Optional.ofNullable(code);
    }

    @Override
    public Long getOtpExpire(String email, TimeUnit timeUnit) {
        return redisTemplate.getExpire(email, timeUnit);
    }

    @Override
    public Boolean deleteOtpCode(String email) {
       return redisTemplate.delete(email);
    }


    /**
     * Checks if the provided OTP code is valid for the given email.
     */
    @Override
    public Boolean otpCodeValid(String email, String code) {
        var otpCode = getOtpCode(email);
        return otpCode.isPresent() && otpCode.get().equals(code);
    }

}
