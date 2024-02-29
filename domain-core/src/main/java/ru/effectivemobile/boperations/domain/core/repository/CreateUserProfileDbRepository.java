package ru.effectivemobile.boperations.domain.core.repository;

import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserProfileDbRequest;

/**
 * Создает профиль пользователя с персональными данными
 */
public interface CreateUserProfileDbRepository {
    boolean create(CreateUserProfileDbRequest request);

    boolean existsPhone(String phone);

    boolean existsEmail(String email);
}
