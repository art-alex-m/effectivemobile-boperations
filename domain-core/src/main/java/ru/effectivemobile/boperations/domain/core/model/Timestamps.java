package ru.effectivemobile.boperations.domain.core.model;

import java.time.Instant;

public interface Timestamps {
    Instant getCreatedAt();

    Instant getUpdatedAt();
}
