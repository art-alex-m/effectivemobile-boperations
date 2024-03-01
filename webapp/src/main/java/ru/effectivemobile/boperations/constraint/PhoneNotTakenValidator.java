package ru.effectivemobile.boperations.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.repository.AppProfilePhoneJpaRepository;

@AllArgsConstructor
@Component
public class PhoneNotTakenValidator implements ConstraintValidator<PhoneNotTaken, String> {

    private final AppProfilePhoneJpaRepository phoneJpaRepository;

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return !phoneJpaRepository.existsByValue(phone);
    }
}
