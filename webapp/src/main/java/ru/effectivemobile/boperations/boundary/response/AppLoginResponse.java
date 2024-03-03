package ru.effectivemobile.boperations.boundary.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.boundary.response.LoginResponse;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;

import java.util.Set;

@AllArgsConstructor
@Getter
public class AppLoginResponse implements LoginResponse {
    private final DomainUser user;

    private final Set<String> permissions;
}
