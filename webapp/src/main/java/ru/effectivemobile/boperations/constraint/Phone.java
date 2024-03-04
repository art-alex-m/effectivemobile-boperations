package ru.effectivemobile.boperations.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^\\d+$", message = "Only digits required")
@Length(max = 14)
@Constraint(validatedBy = {})
public @interface Phone {
    String message() default "Phone number is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
