package ru.effectivemobile.boperations.domain.core.repository;

import ru.effectivemobile.boperations.domain.core.repository.request.CreateAccountOperationDbRequest;

/**
 * Пополняет баланс нового пользователя
 */
public interface CreateUserStartBalanceDbRepository {
    boolean create(CreateAccountOperationDbRequest request);
}
