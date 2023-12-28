package com.github.gribanoveu.cuddle.utils.validators;

import com.github.gribanoveu.cuddle.constants.RegexpFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * @author Evgeny Gribanov
 * @version 23.10.2023
 */
public class MinimalAgeValidator implements ConstraintValidator<MinimalAge, String> {
    private static final int MIN_AGE = 18;

    @Override
    public void initialize(MinimalAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) return false;
        if (!birthDate.matches(RegexpFormat.BIRTHDAY_PATTERN)) return false;
        var now = LocalDate.now();
        var date = LocalDate.parse(birthDate);
        var age = now.minusYears(date.getYear())
                .minusMonths(date.getMonthValue())
                .minusDays(date.getDayOfMonth())
                .getYear();

        return age >= MIN_AGE;
    }
}
