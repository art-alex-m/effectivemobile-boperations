package ru.effectivemobile.boperations.domain.core.model;

import java.time.Instant;
import java.util.Set;

/**
 * Профиль пользователя
 */
public interface DomainProfile extends DomainUserable {
    ProfileProperty<Instant> getBirthday();

    ProfileProperty<String> getName();

    Set<? extends ProfileProperty<String>> getPhones();

    Set<? extends ProfileProperty<String>> getEmails();
}
