package ru.effectivemobile.boperations.domain.core.boundary.request;

import java.util.UUID;

public interface ProfilePropertyCreateRequest<T> {
    UUID getUserId();

    T getValue();
}
