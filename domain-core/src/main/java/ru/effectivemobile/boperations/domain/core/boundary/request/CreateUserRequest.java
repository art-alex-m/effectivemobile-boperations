package ru.effectivemobile.boperations.domain.core.boundary.request;

import java.math.BigDecimal;
import java.time.Instant;

public interface CreateUserRequest {
    String getUsername();

    String getPassword();

    String getName();

    String getPhone();

    String getEmail();

    Instant getBirthday();

    BigDecimal getStartBalance();
}
