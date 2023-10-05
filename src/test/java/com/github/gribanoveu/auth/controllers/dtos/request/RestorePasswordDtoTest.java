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
 * @version 05.10.2023
 */
class RestorePasswordDtoTest extends BaseValidatorTest {
    @Test
    void testValidRestorePasswordDto() {
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("test@example.com", "123456", "password", "password");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidRestorePasswordDtoWithIncorrectEmailFormat() {
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("testexample.com", "123456", "password", "password");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRestorePasswordDtoWithLongEmail() {
        String longEmail = StringUtils.repeat("a", 31) + "@example.com";
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto(longEmail, "123456", "password", "password");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRestorePasswordDtoWithEmptyOtpCode() {
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("test@example.com", "", "password", "password");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsAll(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE, ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRestorePasswordDtoWithIncorrectOtpCodeFormat() {
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("test@example.com", "1234567", "password", "password");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRestorePasswordDtoWithEmptyPassword() {
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("test@example.com", "123456", "", "password");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRestorePasswordDtoWithLongPassword() {
        String longPassword = StringUtils.repeat("a", 81);
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("test@example.com", "123456", longPassword, "password");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRestorePasswordDtoWithEmptyConfirmPassword() {
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("test@example.com", "123456", "password", "");
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidRestorePasswordDtoWithLongConfirmPassword() {
        String longConfirmPassword = StringUtils.repeat("a", 81);
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto("test@example.com", "123456", "password", longConfirmPassword);
        Set<ConstraintViolation<RestorePasswordDto>> violations = validator.validate(restorePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }
}