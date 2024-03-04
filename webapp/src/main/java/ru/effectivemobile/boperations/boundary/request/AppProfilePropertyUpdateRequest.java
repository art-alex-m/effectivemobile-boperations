package ru.effectivemobile.boperations.boundary.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyUpdateRequest;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppProfilePropertyUpdateRequest<T> implements ProfilePropertyUpdateRequest<T> {
    private final UUID userId;

    private final UUID propertyId;

    private final T value;
}
