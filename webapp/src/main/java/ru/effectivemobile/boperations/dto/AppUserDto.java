package ru.effectivemobile.boperations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppUserDto implements DomainUser {
    private final UUID id;
}
