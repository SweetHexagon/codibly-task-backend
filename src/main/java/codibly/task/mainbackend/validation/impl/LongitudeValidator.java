package codibly.task.mainbackend.validation.impl;

import codibly.task.mainbackend.validation.interfaces.ValidLongitude;
import codibly.task.mainbackend.validation.interfaces.ValidLongitude;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<ValidLongitude, Double> {

    @Override
    public void initialize(ValidLongitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double longitude, ConstraintValidatorContext context) {
        return longitude != null && longitude >= -180 && longitude <= 180;
    }
}
