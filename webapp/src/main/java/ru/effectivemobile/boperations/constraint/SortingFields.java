package ru.effectivemobile.boperations.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Inherited
@Constraint(validatedBy = SortingFieldsValidator.class)
public @interface SortingFields {
    String message() default "Sorting fields unexpected, empty or null: ${validatedValue}. Expected {expected}";

    String[] expected() default {};

    int minLength() default 2;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
