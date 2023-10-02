package com.github.gribanoveu.auth.entities.services.contract;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Evgeny Gribanov
 * @version 28.09.2023
 */
public interface RedisOtpService {
    void saveOptCode(String email, String code, Duration codeDuration);
    Optional<String> getOtpCode(String email);
    Long getOtpExpire(String email, TimeUnit timeUnit);
    Boolean deleteOtpCode(String email);
    Boolean otpCodeValid(String email, String code);
}
