package ru.effectivemobile.boperations.domain.core.boundary.request;

import java.time.Instant;

/**
 * Запрос поиска клиентов
 */
public interface ProfileSearchRequest extends Paging {

    String getName();

    String getPhone();

    String getEmail();

    Instant getBirthday();

    default boolean hasName() {
        return getName() != null && !getName().isEmpty();
    }

    default boolean hasPhone() {
        return getPhone() != null && !getPhone().isEmpty();
    }

    default boolean hasEmail() {
        return getEmail() != null && !getEmail().isEmpty();
    }

    default boolean hasBirthday() {
        return getBirthday() != null;
    }
}
