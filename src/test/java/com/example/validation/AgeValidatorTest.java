package com.example.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

class AgeValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private AgeValidator validator;

    @Value("${user.age.requirement}")
    private int userAgeRequirement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new AgeValidator();
        setAgeRequirement(validator, 18);
    }

    @Test
    void isValid_ValidBirthDate_Success() {
        LocalDate birthDate = LocalDate.now().minusYears(30);

        boolean result = validator.isValid(birthDate, constraintValidatorContext);

        assertTrue(result);
    }

    @Test
    void isValid_BirthDateYoungerThanRequirement_Unsuccess() {
        LocalDate birthDate = LocalDate.now().minusYears(16);

        assertThrows(IllegalArgumentException.class,
                () -> validator.isValid(birthDate, constraintValidatorContext));
    }

    @Test
    void isValid_NullBirthDate_Unsuccess() {
        boolean result = validator.isValid(null, constraintValidatorContext);

        assertFalse(result);
    }

    @Test
    void checkMessage_Sucess() {
        AgeConstraint ageConstraint = mock(AgeConstraint.class);
        when(ageConstraint.message()).thenReturn("Custom message for age constraint");

        assertEquals("Custom message for age constraint", ageConstraint.message());
    }

    private void setAgeRequirement(AgeValidator validator, int ageRequirement) {
        try {
            Field field = AgeValidator.class.getDeclaredField("userAgeRequirement");
            field.setAccessible(true);
            field.setInt(validator, ageRequirement);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
