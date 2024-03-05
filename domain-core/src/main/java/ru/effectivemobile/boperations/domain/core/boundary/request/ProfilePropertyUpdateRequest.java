package ru.effectivemobile.boperations.domain.core.boundary.request;

import java.util.UUID;

public interface ProfilePropertyUpdateRequest<T> extends ProfilePropertyCreateRequest<T> {
    UUID getPropertyId();
}
