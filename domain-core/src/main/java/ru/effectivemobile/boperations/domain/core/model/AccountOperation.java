package ru.effectivemobile.boperations.domain.core.model;

import java.util.UUID;

/**
 * Операция с банковским счетом
 */
public interface AccountOperation {
    UUID getId();

    Account getAccount();

    double getAmount();

    AccountOperationType getType();
}
