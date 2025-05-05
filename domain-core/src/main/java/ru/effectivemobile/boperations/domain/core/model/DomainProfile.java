package ru.effectivemobile.boperations.domain.core.model;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Профиль пользователя
 */
public interface DomainProfile extends DomainUserable {
    UUID getId();

    ProfileProperty<Instant> getBirthday();

    ProfileProperty<String> getName();

    Set<ProfileProperty<String>> getPhones();

    Set<ProfileProperty<String>> getEmails();

    Instant getCreatedAt();
}
