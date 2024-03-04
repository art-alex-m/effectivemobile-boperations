package ru.effectivemobile.boperations.domain.core.boundary;

import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyDeleteRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.ProfilePropertyCrudResponse;

/**
 * Сценарий удаления параметра профиля
 *
 * @param <T> тип параметра
 */
public interface ProfilePropertyDeleteInteractor<T> {
    ProfilePropertyCrudResponse<T> delete(ProfilePropertyDeleteRequest request);
}
