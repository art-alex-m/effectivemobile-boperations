package ru.effectivemobile.boperations.boundary;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.boundary.response.AppLoginResponse;
import ru.effectivemobile.boperations.domain.core.boundary.LoginInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.LoginRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.LoginResponse;
import ru.effectivemobile.boperations.domain.core.exception.BoperationsDomainException;
import ru.effectivemobile.boperations.domain.core.model.DomainPermission;
import ru.effectivemobile.boperations.entity.AppUser;
import ru.effectivemobile.boperations.repository.AppUserJpaRepository;

import java.util.Set;

@AllArgsConstructor
@Component
public class AppLoginInteractor implements LoginInteractor {

    private final PasswordEncoder passwordEncoder;

    private final AppUserJpaRepository userJpaRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        RuntimeException invalidUser = new BoperationsDomainException("Invalid username or password");

        AppUser user = userJpaRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> invalidUser);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw invalidUser;
        }

        return new AppLoginResponse(user, Set.of(DomainPermission.PERMIT_ALL));
    }
}
