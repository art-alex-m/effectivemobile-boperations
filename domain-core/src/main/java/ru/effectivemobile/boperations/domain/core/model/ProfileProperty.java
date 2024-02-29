package ru.effectivemobile.boperations.domain.core.model;

import java.util.UUID;

/**
 * Параметр профиля пользователя: email, телефон, фио и тд
 *
 * @param <T>
 */
public interface ProfileProperty<T> extends Timestamps {
    T getValue();

    UUID getId();
}
