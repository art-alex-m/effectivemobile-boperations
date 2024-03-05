package ru.effectivemobile.boperations.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.repository.AppUserJpaRepository;

@RequiredArgsConstructor
@Component
public class UsernameNotTakenValidator implements ConstraintValidator<UsernameNotTaken, String> {

    private final AppUserJpaRepository userJpaRepository;

    private boolean not = false;

    @Override
    public void initialize(UsernameNotTaken constraintAnnotation) {
        this.not = constraintAnnotation.not();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userJpaRepository.existsByUsername(username) == not;
    }
}
