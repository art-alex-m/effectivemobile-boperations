package ru.effectivemobile.boperations.domain.core.model;

import java.time.Instant;
import java.util.Set;

/**
 * Профиль пользователя
 */
public interface DomainProfile extends DomainUserable {
    ProfileProperty<Instant> getBirthday();

    ProfileProperty<String> getName();

    Set<ProfileProperty<String>> getPhones();

    Set<ProfileProperty<String>> getEmails();
}
