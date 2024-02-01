package com.github.gribanoveu.cuddle.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Evgeny Gribanov
 * @version 29.08.2023
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JsonUtils {
    private final ObjectMapper objectMapper;

    /**
     * Converts the given DTO object to a JSON string.
     *
     * @param  dto  the DTO object to convert
     * @return      the JSON string representation of the DTO object
     */
    public String convertDtoToJson(Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * Generates a random OTP (One-Time Password) code  between 100,000 (inclusive) and 1,000,000 (exclusive).
     *
     * @return  the randomly generated OTP code
     */
    public Integer generateRandomOtpCode() {
        return ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
    }
}