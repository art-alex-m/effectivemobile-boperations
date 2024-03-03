package ru.effectivemobile.boperations.domain.core.boundary.request;

import java.math.BigDecimal;
import java.util.UUID;

public interface UserWithdrawOperationRequest {
    UUID getUserIdFrom();

    UUID getUserIdTo();

    BigDecimal getAmount();
}
