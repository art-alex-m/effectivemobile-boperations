package ru.effectivemobile.boperations.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.repository.AppProfileEmailJpaRepository;

@AllArgsConstructor
@Component
public class EmailNotTakenValidator implements ConstraintValidator<EmailNotTaken, String> {

    private final AppProfileEmailJpaRepository emailJpaRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !emailJpaRepository.existsByValue(email);
    }
}
