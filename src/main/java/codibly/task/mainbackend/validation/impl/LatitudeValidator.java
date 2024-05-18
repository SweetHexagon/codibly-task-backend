package codibly.task.mainbackend.validation.impl;

import codibly.task.mainbackend.validation.interfaces.ValidLatitude;
import codibly.task.mainbackend.validation.interfaces.ValidLongitude;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class LatitudeValidator implements ConstraintValidator<ValidLatitude, Double> {

    @Override
    public void initialize(ValidLatitude constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double latitude, ConstraintValidatorContext context) {
        return latitude != null && latitude >= -90 && latitude <= 90;
    }
}
