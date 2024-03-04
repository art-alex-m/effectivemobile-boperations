package ru.effectivemobile.boperations.domain.core.boundary.request;

import java.util.UUID;

public interface ProfilePropertyDeleteRequest {
    UUID getPropertyId();

    UUID getUserId();
}
