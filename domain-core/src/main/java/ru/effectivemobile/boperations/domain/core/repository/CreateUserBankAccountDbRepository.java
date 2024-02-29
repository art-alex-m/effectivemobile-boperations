package ru.effectivemobile.boperations.domain.core.repository;

import ru.effectivemobile.boperations.domain.core.model.Account;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserBankAccountDbRequest;

/**
 * Создает банковский счет пользователя
 */
public interface CreateUserBankAccountDbRepository {
    Account create(CreateUserBankAccountDbRequest request);
}
