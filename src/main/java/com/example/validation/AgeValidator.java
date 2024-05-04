package com.example.validation;

import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDate;
import java.time.Period;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeConstraint, LocalDate> {

    @Value("${user.age.requirement}")
    private int userAgeRequirement;

    @Override
    public void initialize(AgeConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);

        if (period.getYears() < userAgeRequirement) {
            throw new IllegalArgumentException("User's age must be older than " + userAgeRequirement);
        }
        return true;
    }
}
