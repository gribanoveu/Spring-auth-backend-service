package com.github.gribanoveu.auth.controllers.dtos.request;

import com.github.gribanoveu.auth.base.BaseValidatorTest;
import com.github.gribanoveu.auth.constants.ValidationMessages;
import jakarta.validation.ConstraintViolation;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

/**
 * @author Evgeny Gribanov
 * @version 04.10.2023
 */
class LoginDtoTest extends BaseValidatorTest {

    @Test
    void testValidLoginDto() {
        LoginDto loginDto = new LoginDto("test@example.com", "password");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidLoginDtoWithIncorrectEmailFormat() {
        LoginDto loginDto = new LoginDto("testexample.com", "password");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidLoginDtoWithEmptyEmail() {
        LoginDto loginDto = new LoginDto("", "password");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsAll(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE, ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidLoginDtoWithEmptyPassword() {
        LoginDto loginDto = new LoginDto("test@example.com", "");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidLoginDtoWithLongEmail() {
        LoginDto loginDto = new LoginDto(StringUtils.repeat("a", 254) + "@example.com", "password");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidLoginDtoWithLongPassword() {
        LoginDto loginDto = new LoginDto("test@example.com", StringUtils.repeat("a", 254));

        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidLoginDtoWithNullEmail() {
        LoginDto loginDto = new LoginDto(null, "password");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidLoginDtoWithNullPassword() {
        LoginDto loginDto = new LoginDto("test@example.com", null);
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

}