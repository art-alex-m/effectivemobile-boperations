package ru.effectivemobile.boperations.repository;

import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserAuthAccountDbRepository;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserAuthAccountDbRequest;

@Component
public class AppCreateUserAuthAccountDbRepository implements CreateUserAuthAccountDbRepository {

    @Override
    public DomainUser create(CreateUserAuthAccountDbRequest request) {
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}
