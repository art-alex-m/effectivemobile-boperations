package ru.effectivemobile.boperations.domain.core.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Операция с банковским счетом
 */
public interface AccountOperation extends Timestamps {
    UUID getId();

    Account getAccount();

    BigDecimal getAmount();

    AccountOperationType getType();
}
