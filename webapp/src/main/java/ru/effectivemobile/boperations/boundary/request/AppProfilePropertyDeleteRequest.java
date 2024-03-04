package ru.effectivemobile.boperations.boundary.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyDeleteRequest;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppProfilePropertyDeleteRequest implements ProfilePropertyDeleteRequest {
    private final UUID userId;

    private final UUID propertyId;
}
