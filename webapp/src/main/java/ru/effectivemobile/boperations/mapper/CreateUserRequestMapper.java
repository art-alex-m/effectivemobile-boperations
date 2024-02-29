package ru.effectivemobile.boperations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effectivemobile.boperations.boundary.request.AppCreateUserRequest;
import ru.effectivemobile.boperations.domain.core.model.Account;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;
import ru.effectivemobile.boperations.repository.request.AppCreateAccountOperationDbRequest;
import ru.effectivemobile.boperations.repository.request.AppCreateUserAuthAccountDbRequest;
import ru.effectivemobile.boperations.repository.request.AppCreateUserProfileDbRequest;

@Mapper
public interface CreateUserRequestMapper {

    @Mapping(source = "passwordHash", target = "password")
    AppCreateUserAuthAccountDbRequest toAuthAccountDbRequest(AppCreateUserRequest request, String passwordHash);

    AppCreateUserProfileDbRequest toUserProfileDbRequest(AppCreateUserRequest request, DomainUser user);

    @Mapping(source = "request.startBalance", target = "amount")
    AppCreateAccountOperationDbRequest toOperationDbRequest(AppCreateUserRequest request, Account accountTopup,
            Account accountWithdraw);
}
