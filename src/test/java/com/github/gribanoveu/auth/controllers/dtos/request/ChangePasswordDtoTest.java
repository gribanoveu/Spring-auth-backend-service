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
class ChangePasswordDtoTest extends BaseValidatorTest {

    @Test
    void testValidChangePasswordDto() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("oldPassword", "newPassword", "newPassword");
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidChangePasswordDtoWithEmptyOldPassword() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("", "newPassword", "newPassword");
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidChangePasswordDtoWithEmptyPassword() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("oldPassword", "", "newPassword");
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidChangePasswordDtoWithEmptyConfirmPassword() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("oldPassword", "newPassword", "");
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidChangePasswordDtoWithLongOldPassword() {
        String longOldPassword = StringUtils.repeat("a", 81);
        ChangePasswordDto changePasswordDto = new ChangePasswordDto(longOldPassword, "newPassword", "newPassword");
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidChangePasswordDtoWithLongPassword() {
        String longPassword = StringUtils.repeat("a", 81);
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("oldPassword", longPassword, "newPassword");
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidChangePasswordDtoWithLongConfirmPassword() {
        String longConfirmPassword = StringUtils.repeat("a", 81);
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("oldPassword", "newPassword", longConfirmPassword);
        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(changePasswordDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }
}