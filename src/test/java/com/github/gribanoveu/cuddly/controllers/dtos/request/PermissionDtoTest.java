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
class PermissionDtoTest extends BaseValidatorTest {
    @Test
    void testValidPermissionDto() {
        PermissionDto permissionDto = new PermissionDto("TT_PERMISSION");
        Set<ConstraintViolation<PermissionDto>> violations = validator.validate(permissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidPermissionDtoWithIncorrectPermissionFormat() {
        PermissionDto permissionDto = new PermissionDto("read-write");
        Set<ConstraintViolation<PermissionDto>> violations = validator.validate(permissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidPermissionDtoWithLongPermissionName() {
        String longPermissionName = StringUtils.repeat("a", 31);
        PermissionDto permissionDto = new PermissionDto(longPermissionName);
        Set<ConstraintViolation<PermissionDto>> violations = validator.validate(permissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsAll(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE, ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidPermissionDtoWithNullPermissionName() {
        PermissionDto permissionDto = new PermissionDto(null);
        Set<ConstraintViolation<PermissionDto>> violations = validator.validate(permissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE));
    }
}