package ru.effectivemobile.boperations.repository;

import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.domain.core.model.Account;
import ru.effectivemobile.boperations.domain.core.repository.CreateUserBankAccountDbRepository;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserBankAccountDbRequest;

@Component
public class AppCreateUserBankAccountDbRepository implements CreateUserBankAccountDbRepository {
    @Override
    public Account create(CreateUserBankAccountDbRequest request) {
        return null;
    }
}
