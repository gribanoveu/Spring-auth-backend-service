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
class UpdatePermissionDtoTest extends BaseValidatorTest {
    @Test
    void testValidUpdatePermissionDto() {
        UpdatePermissionDto updatePermissionDto = new UpdatePermissionDto("TT_PERMISSION", "TT_PERMISSION_NEW");
        Set<ConstraintViolation<UpdatePermissionDto>> violations = validator.validate(updatePermissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage).hasSize(0);
    }

    @Test
    void testInvalidUpdatePermissionDtoWithEmptyPermissionName() {
        UpdatePermissionDto updatePermissionDto = new UpdatePermissionDto("", "TT_PERMISSION");
        Set<ConstraintViolation<UpdatePermissionDto>> violations = validator.validate(updatePermissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsAll(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE, ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidUpdatePermissionDtoWithIncorrectPermissionNameFormat() {
        UpdatePermissionDto updatePermissionDto = new UpdatePermissionDto("invalid_permission", "TT_PERMISSION");
        Set<ConstraintViolation<UpdatePermissionDto>> violations = validator.validate(updatePermissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidUpdatePermissionDtoWithLongPermissionName() {
        String longPermissionName = StringUtils.repeat("a", 31).toUpperCase();
        UpdatePermissionDto updatePermissionDto = new UpdatePermissionDto("TT_" + longPermissionName, "TT_PERMISSION");
        Set<ConstraintViolation<UpdatePermissionDto>> violations = validator.validate(updatePermissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidUpdatePermissionDtoWithEmptyNewName() {
        UpdatePermissionDto updatePermissionDto = new UpdatePermissionDto("TT_PERMISSION", "");
        Set<ConstraintViolation<UpdatePermissionDto>> violations = validator.validate(updatePermissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsAll(List.of(ValidationMessages.NOT_BLANK_EXCEPTION_MESSAGE, ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidUpdatePermissionDtoWithIncorrectNewNameFormat() {
        UpdatePermissionDto updatePermissionDto = new UpdatePermissionDto("TT_PERMISSION", "invalid_new_permission");
        Set<ConstraintViolation<UpdatePermissionDto>> violations = validator.validate(updatePermissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.PATTERN_EXCEPTION_MESSAGE));
    }

    @Test
    void testInvalidUpdatePermissionDtoWithLongNewName() {
        String longNewName = StringUtils.repeat("a", 31).toUpperCase();
        UpdatePermissionDto updatePermissionDto = new UpdatePermissionDto("TT_PERMISSION", "TT_" + longNewName);
        Set<ConstraintViolation<UpdatePermissionDto>> violations = validator.validate(updatePermissionDto);
        Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
                .isEqualTo(List.of(ValidationMessages.SIZE_EXCEPTION_MESSAGE));
    }
}