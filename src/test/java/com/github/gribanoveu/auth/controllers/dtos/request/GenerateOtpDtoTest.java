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
class GenerateOtpDtoTest extends BaseValidatorTest {

    @Test
    void testValidGenerateOtpDto() {
        GenerateOtpDto generateOtpDto = new GenerateOtpDto("test@example.com");
        Set<ConstraintViolation<GenerateOtpDto>> violations = validator.validate(generateOtpDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidGenerateOtpDtoWithIncorrectEmailFormat() {
        GenerateOtpDto generateOtpDto = new GenerateOtpDto("testexample.com");
        Set<ConstraintViolation<GenerateOtpDto>> violations = validator.validate(generateOtpDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidGenerateOtpDtoWithLongEmail() {
        String longEmail = StringUtils.repeat("a", 31) + "@example.com";
        GenerateOtpDto generateOtpDto = new GenerateOtpDto(longEmail);
        Set<ConstraintViolation<GenerateOtpDto>> violations = validator.validate(generateOtpDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidGenerateOtpDtoWithNullEmail() {
        GenerateOtpDto generateOtpDto = new GenerateOtpDto(null);
        Set<ConstraintViolation<GenerateOtpDto>> violations = validator.validate(generateOtpDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }
}