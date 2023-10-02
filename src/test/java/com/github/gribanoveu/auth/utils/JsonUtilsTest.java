package com.github.gribanoveu.auth.utils;

import com.github.gribanoveu.auth.controllers.dtos.request.LoginDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Evgeny Gribanov
 * @version 02.10.2023
 */
class JsonUtilsTest {
    private final JsonUtils jsonUtils = new JsonUtils();

    @Test
    public void testConvertDtoToJson_WithInvalidDto() {
        var loginDto = new LoginDto("",  null);
        var json = jsonUtils.convertDtoToJson(loginDto);
        var expectedJson = "{\"email\":\"\",\"password\":null}";
        Assertions.assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void testConvertDtoToJson_WithValidDto() {
        var loginDto = new LoginDto("john@doe.com", "123");
        String expectedJson = "{\"email\":\"john@doe.com\",\"password\":\"123\"}";
        String actualJson = jsonUtils.convertDtoToJson(loginDto);
        Assertions.assertThat(actualJson).isEqualTo(expectedJson);
    }

    @Test
    public void testGenerateRandomOtpCode() {
        Integer otpCode = jsonUtils.generateRandomOtpCode();
        assertTrue(otpCode >= 100_000 && otpCode < 1_000_000);
    }
}