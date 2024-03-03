package ru.effectivemobile.boperations.domain.core.boundary.response;

import ru.effectivemobile.boperations.domain.core.model.DomainUserable;

import java.util.Set;

public interface LoginResponse extends DomainUserable {
    Set<String> getPermissions();
}