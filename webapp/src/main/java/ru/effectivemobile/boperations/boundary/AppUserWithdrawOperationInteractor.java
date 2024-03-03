package ru.effectivemobile.boperations.boundary;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.boperations.boundary.response.AppUserWithdrawOperationResponse;
import ru.effectivemobile.boperations.domain.core.boundary.UserWithdrawOperationInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.domain.core.exception.BoperationsDomainException;
import ru.effectivemobile.boperations.domain.core.model.AccountOperationType;
import ru.effectivemobile.boperations.entity.AppAccount;
import ru.effectivemobile.boperations.entity.AppAccountOperation;
import ru.effectivemobile.boperations.repository.AppAccountJpaRepository;
import ru.effectivemobile.boperations.repository.AppAccountOperationJpaRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class AppUserWithdrawOperationInteractor implements UserWithdrawOperationInteractor {

    private final AppAccountJpaRepository accountJpaRepository;

    private final AppAccountOperationJpaRepository operationJpaRepository;

    private final EntityManager entityManager;

    @Transactional
    @Override
    public UserWithdrawOperationResponse withdraw(UserWithdrawOperationRequest request) {

        AppAccount accountFrom = accountJpaRepository.findFirstByUser_Id(request.getUserIdFrom())
                .orElseThrow(() -> new BoperationsDomainException("Invalid account from"));

        if (accountFrom.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BoperationsDomainException("Insufficient funds in the account from");
        }

        AppAccount accountTo = accountJpaRepository.findFirstByUser_Id(request.getUserIdTo())
                .orElseThrow(() -> new BoperationsDomainException("Invalid account to"));

        AppAccountOperation withdraw = new AppAccountOperation(accountFrom, request.getAmount(),
                AccountOperationType.WITHDRAW);
        operationJpaRepository.save(withdraw);

        AppAccountOperation topup = new AppAccountOperation(withdraw.getId(), accountTo, request.getAmount(),
                AccountOperationType.TOPUP);
        operationJpaRepository.save(topup);

        List.of(accountFrom, accountTo, withdraw, topup).forEach(entityManager::detach);

        return new AppUserWithdrawOperationResponse(withdraw.getId());
    }
}
