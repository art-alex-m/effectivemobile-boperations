package ru.effectivemobile.boperations.domain.core.exception;

/**
 * Исключение предметной области
 */
public class BoperationsDomainException extends RuntimeException {
    public BoperationsDomainException(String message) {
        super(message);
    }
}
