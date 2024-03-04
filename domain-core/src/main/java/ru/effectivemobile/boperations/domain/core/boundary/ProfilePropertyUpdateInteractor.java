package ru.effectivemobile.boperations.domain.core.boundary;

import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyUpdateRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.ProfilePropertyCrudResponse;

/**
 * Сценарий обновления параметра профиля
 *
 * @param <T> тип параметра
 */
public interface ProfilePropertyUpdateInteractor<T> {
    ProfilePropertyCrudResponse<T> update(ProfilePropertyUpdateRequest<T> request);
}
