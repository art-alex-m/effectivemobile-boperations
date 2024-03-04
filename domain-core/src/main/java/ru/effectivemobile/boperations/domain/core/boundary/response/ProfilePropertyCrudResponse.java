package ru.effectivemobile.boperations.domain.core.boundary.response;

import ru.effectivemobile.boperations.domain.core.model.ProfileProperty;

public interface ProfilePropertyCrudResponse<T> {
    ProfileProperty<T> getProperty();
}
