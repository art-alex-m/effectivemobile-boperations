package ru.effectivemobile.boperations.repository.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserBankAccountDbRequest;

@AllArgsConstructor
@Getter
public class AppCreateUserBankAccountDbRequest implements CreateUserBankAccountDbRequest {
    private DomainUser user;
}
