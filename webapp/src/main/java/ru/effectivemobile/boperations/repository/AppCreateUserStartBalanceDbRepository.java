package ru.effectivemobile.boperations.repository;

import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserStartBalanceDbRepository;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateAccountOperationDbRequest;

@Component
public class AppCreateUserStartBalanceDbRepository implements CreateUserStartBalanceDbRepository {
    @Override
    public boolean create(CreateAccountOperationDbRequest request) {
        return false;
    }
}
