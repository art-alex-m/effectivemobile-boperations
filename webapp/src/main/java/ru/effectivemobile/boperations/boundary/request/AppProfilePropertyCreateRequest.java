package ru.effectivemobile.boperations.boundary.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyCreateRequest;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppProfilePropertyCreateRequest<T> implements ProfilePropertyCreateRequest<T> {
    private final UUID userId;

    private final T value;
}
