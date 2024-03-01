package ru.effectivemobile.boperations.domain.core.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Банковский счет пользователя
 */
public interface Account extends DomainUserable {
    UUID getId();

    Instant getCreatedAt();

    BigDecimal getBalance();
}
