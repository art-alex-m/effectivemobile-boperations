package ru.effectivemobile.boperations.domain.core.boundary;

import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyCreateRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.ProfilePropertyCrudResponse;

/**
 * Сценарий создания параметра профиля
 *
 * @param <T> тип параметра
 */
public interface ProfilePropertyCreateInteractor<T> {
    ProfilePropertyCrudResponse<T> create(ProfilePropertyCreateRequest<T> request);
}
