package ru.effectivemobile.boperations.domain.core.boundary;

import ru.effectivemobile.boperations.domain.core.boundary.request.LoginRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.LoginResponse;

/**
 * Сценарий идентификации пользователя
 */
public interface LoginInteractor {
    LoginResponse login(LoginRequest request);
}
