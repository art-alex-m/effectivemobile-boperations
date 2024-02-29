package ru.effectivemobile.boperations.domain.core.model;

/**
 * Типы операций с банковским счетом
 */
public enum AccountOperationType {
    /**
     * Пополнение
     */
    TOPUP,

    /**
     * Списание денежных средств
     */
    WITHDRAW;
}
