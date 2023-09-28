package com.github.gribanoveu.auth.entities.services.contract;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Evgeny Gribanov
 * @version 28.09.2023
 */
public interface RedisService {
    void saveOptCode(String email, int code, Duration codeDuration);
    Optional<Integer> getOtpCode(String email);
    Long getOtpExpire(String email, TimeUnit timeUnit);
}
