package ru.effectivemobile.boperations.boundary;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.boperations.domain.core.boundary.InterestOnBalanceInteractor;
import ru.effectivemobile.boperations.domain.core.model.AccountOperationType;
import ru.effectivemobile.boperations.entity.AppAccountOperation;
import ru.effectivemobile.boperations.repository.AppAccountJpaRepository;
import ru.effectivemobile.boperations.repository.AppAccountOperationJpaRepository;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Начисление процентов на остаток
 */
@AllArgsConstructor
@Component
public class AppInterestOnBalanceInteractor implements InterestOnBalanceInteractor {

    private final AppAccountJpaRepository accountJpaRepository;

    private final AppAccountOperationJpaRepository operationJpaRepository;

    private final BigDecimal rate = BigDecimal.valueOf(0.05);

    private final double maxIncrease = 2.07;

    @Transactional
    @Scheduled(fixedDelay = 60, initialDelay = 60, timeUnit = TimeUnit.SECONDS)
    public void charge() {
        accountJpaRepository.findAllSuitableForInterest(maxIncrease).forEach(account -> {
            AppAccountOperation operation =
                    new AppAccountOperation(account, account.getBalance().multiply(rate), AccountOperationType.TOPUP);
            operationJpaRepository.save(operation);
        });
    }
}
