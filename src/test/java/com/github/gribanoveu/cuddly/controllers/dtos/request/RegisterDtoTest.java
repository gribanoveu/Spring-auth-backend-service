package com.github.gribanoveu.cuddly.controllers.dtos.request;

import com.github.gribanoveu.cuddly.base.BaseValidatorTest;
import com.github.gribanoveu.cuddly.constants.ValidationMessages;
import jakarta.validation.ConstraintViolation;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

/**
 * @author Evgeny Gribanov
 * @version 05.10.2023
 */
class RegisterDtoTest extends BaseValidatorTest {

    @Test
    void testValidRegisterDto() {
        RegisterDto registerDto = new RegisterDto("test@example.com", "password", "password");
        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidRegisterDtoWithIncorrectEmailFormat() {
        RegisterDto registerDto = new RegisterDto("testexample.com", "password", "password");
        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRegisterDtoWithLongEmail() {
        String longEmail = StringUtils.repeat("a", 31) + "@example.com";
        RegisterDto registerDto = new RegisterDto(longEmail, "password", "password");
        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRegisterDtoWithEmptyPassword() {
        RegisterDto registerDto = new RegisterDto("test@example.com", "", "password");
        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRegisterDtoWithLongPassword() {
        String longPassword = StringUtils.repeat("a", 81);
        RegisterDto registerDto = new RegisterDto("test@example.com", longPassword, "password");
        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRegisterDtoWithEmptyConfirmPassword() {
        RegisterDto registerDto = new RegisterDto("test@example.com", "password", "");
        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRegisterDtoWithLongConfirmPassword() {
        String longConfirmPassword = StringUtils.repeat("a", 81);
        RegisterDto registerDto = new RegisterDto("test@example.com", "password", longConfirmPassword);
        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(registerDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }
}