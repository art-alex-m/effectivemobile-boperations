package ru.effectivemobile.boperations.domain.core.repository.request;

import ru.effectivemobile.boperations.domain.core.model.Account;

public interface CreateAccountOperationDbRequest {
    double getAmount();

    Account getAccountTopup();

    Account getAccountWithdraw();
}
