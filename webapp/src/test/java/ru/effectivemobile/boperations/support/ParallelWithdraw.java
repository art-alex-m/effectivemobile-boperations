package ru.effectivemobile.boperations.support;

import lombok.AllArgsConstructor;
import ru.effectivemobile.boperations.domain.core.boundary.UserWithdrawOperationInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class ParallelWithdraw implements Callable<UserWithdrawOperationResponse> {
    private final UserWithdrawOperationInteractor interactor;
    private final UserWithdrawOperationRequest request;

    @Override
    public UserWithdrawOperationResponse call() throws Exception {
        System.out.printf("Withdraw: Thread #%d amount %f%n", Thread.currentThread().getId(), request.getAmount());
        return interactor.withdraw(request);
    }
}