package com.example.validation;

import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeConstraint, Date> {

    @Value("${user.age.requirement}")
    private int userAgeRequirement;


    @Override
    public void initialize(AgeConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }

        LocalDate birthDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);

        return period.getYears() >= userAgeRequirement;
    }
}
