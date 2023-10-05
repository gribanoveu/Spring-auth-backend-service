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
class ChangeEmailDtoTest extends BaseValidatorTest {

    @Test
    void testValidChangeEmailDto() {
        ChangeEmailDto changeEmailDto = new ChangeEmailDto("newemail@example.com");
        Set<ConstraintViolation<ChangeEmailDto>> violations = validator.validate(changeEmailDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidChangeEmailDtoWithIncorrectEmailFormat() {
        ChangeEmailDto changeEmailDto = new ChangeEmailDto("newemailexample.com");
        Set<ConstraintViolation<ChangeEmailDto>> violations = validator.validate(changeEmailDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidChangeEmailDtoWithLongEmail() {
        ChangeEmailDto changeEmailDto = new ChangeEmailDto(StringUtils.repeat("a", 254) + "@example.com");
        Set<ConstraintViolation<ChangeEmailDto>> violations = validator.validate(changeEmailDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidChangeEmailDtoWithNullEmail() {
        ChangeEmailDto changeEmailDto = new ChangeEmailDto(null);
        Set<ConstraintViolation<ChangeEmailDto>> violations = validator.validate(changeEmailDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }
}