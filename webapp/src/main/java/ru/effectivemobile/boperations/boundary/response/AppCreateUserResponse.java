package ru.effectivemobile.boperations.boundary.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.boundary.response.CreateUserResponse;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;

@AllArgsConstructor
@Getter
public class AppCreateUserResponse implements CreateUserResponse {
    DomainUser user;
}
