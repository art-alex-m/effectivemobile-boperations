package ru.effectivemobile.boperations.domain.core.repository.request;

import ru.effectivemobile.boperations.domain.core.model.DomainUserable;

import java.time.Instant;

public interface CreateUserProfileDbRequest extends DomainUserable {
    String getName();

    String getPhone();

    String getEmail();

    Instant getBirthday();
}
