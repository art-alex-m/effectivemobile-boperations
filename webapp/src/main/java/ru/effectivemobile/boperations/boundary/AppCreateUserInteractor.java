package ru.effectivemobile.boperations.boundary;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.domain.core.boundary.CreateUserInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.CreateUserRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.CreateUserResponse;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserAuthAccountDbRepository;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserBankAccountDbRepository;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserProfileDbRepository;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserStartBalanceDbRepository;
import ru.effectivemobile.boperations.mapper.CreateUserRequestMapper;

@Component
@AllArgsConstructor
public class AppCreateUserInteractor implements CreateUserInteractor {

    private final PasswordEncoder passwordEncoder;

    private final CreateUserAuthAccountDbRepository accountDbRepository;

    private final CreateUserProfileDbRepository profileDbRepository;

    private final CreateUserBankAccountDbRepository bankAccountDbRepository;

    private final CreateUserStartBalanceDbRepository balanceDbRepository;

    private final CreateUserRequestMapper mapper;

    @Override
    public CreateUserResponse create(CreateUserRequest request) {
        return null;
    }
}
