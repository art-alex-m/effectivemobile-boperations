package ru.effectivemobile.boperations.repository.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.model.Account;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateAccountOperationDbRequest;

@AllArgsConstructor
@Getter
public class AppCreateAccountOperationDbRequest implements CreateAccountOperationDbRequest {
    private double amount;

    private Account accountTopup;

    private Account accountWithdraw;
}
