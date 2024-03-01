package ru.effectivemobile.boperations.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.repository.AppUserJpaRepository;

@AllArgsConstructor
@Component
public class UsernameNotTakenValidator implements ConstraintValidator<UsernameNotTaken, String> {

    private final AppUserJpaRepository userJpaRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userJpaRepository.existsByUsername(username);
    }
}
