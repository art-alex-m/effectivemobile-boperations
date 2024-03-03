package ru.effectivemobile.boperations.boundary.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppUserWithdrawOperationResponse implements UserWithdrawOperationResponse {
    private final UUID operationId;
}
