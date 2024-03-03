package ru.effectivemobile.boperations.domain.core.boundary;

import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;

/**
 * Сценарий внутрибанковского перевода средств между пользователями
 */
public interface UserWithdrawOperationInteractor {
    UserWithdrawOperationResponse withdraw(UserWithdrawOperationRequest request);
}
