package ru.effectivemobile.boperations.domain.core.boundary;

import ru.effectivemobile.boperations.domain.core.boundary.request.CreateUserRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.CreateUserResponse;

/**
 * Сценарий создания пользователя
 */
public interface CreateUserInteractor {
    CreateUserResponse create(CreateUserRequest request);
}
