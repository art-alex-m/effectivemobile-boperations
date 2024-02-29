package ru.effectivemobile.boperations.repository.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserAuthAccountDbRequest;

@AllArgsConstructor
@Getter
public class AppCreateUserAuthAccountDbRequest implements CreateUserAuthAccountDbRequest {
    private String username;

    private String password;
}
