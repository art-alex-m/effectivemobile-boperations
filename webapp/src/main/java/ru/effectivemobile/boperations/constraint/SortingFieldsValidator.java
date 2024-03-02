package ru.effectivemobile.boperations.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;
import java.util.Set;

public class SortingFieldsValidator implements ConstraintValidator<SortingFields, Collection<String>> {

    private Set<String> expected;

    private int minLength;

    @Override
    public void initialize(SortingFields constraintAnnotation) {
        expected = Set.of(constraintAnnotation.expected());
        minLength = constraintAnnotation.minLength();
    }

    @Override
    public boolean isValid(Collection<String> fields, ConstraintValidatorContext constraintValidatorContext) {
        if (fields == null) {
            return true;
        }

        for (String field : fields) {
            if (field == null || field.length() < minLength) {
                return false;
            }
            if (!expected.isEmpty() && !(expected.contains(field) || expected.contains(field.substring(1)))) {
                return false;
            }
        }
        return true;
    }
}
