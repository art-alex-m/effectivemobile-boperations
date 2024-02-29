package ru.effectivemobile.boperations.domain.core.repository;

import ru.effectivemobile.boperations.domain.core.model.DomainUser;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserAuthAccountDbRequest;

/**
 * Создает аккаунт пользователя
 */
public interface CreateUserAuthAccountDbRepository {
    DomainUser create(CreateUserAuthAccountDbRequest request);

    boolean existsByUsername(String username);
}
