package com.github.gribanoveu.auth.entities.services.contract;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Evgeny Gribanov
 * @version 28.09.2023
 */
public interface RedisService {
    void saveOptCode(String email, Integer code, Duration codeDuration);
    Optional<Integer> getOtpCode(String email);
    Long getOtpExpire(String email, TimeUnit timeUnit);
    Boolean deleteOtpCode(String email);
    Boolean otpCodeValid(String email, Integer code);
}
