package ru.effectivemobile.boperations.repository.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;
import ru.effectivemobile.boperations.domain.core.repository.request.CreateUserProfileDbRequest;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class AppCreateUserProfileDbRequest implements CreateUserProfileDbRequest {
    private String name;

    private String phone;

    private String email;

    private Instant birthday;

    private DomainUser user;
}
