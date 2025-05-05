package ru.effectivemobile.boperations.boundary;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.boperations.domain.core.boundary.InterestOnBalanceInteractor;
import ru.effectivemobile.boperations.domain.core.model.Account;
import ru.effectivemobile.boperations.domain.core.model.AccountOperationType;
import ru.effectivemobile.boperations.entity.AppAccount;
import ru.effectivemobile.boperations.entity.AppAccountOperation;
import ru.effectivemobile.boperations.repository.AppAccountJpaRepository;
import ru.effectivemobile.boperations.repository.AppAccountOperationJpaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Начисление процентов на остаток
 *
 * <p>
 *     <a href="https://www.baeldung.com/spring-data-jpa-iterate-large-result-sets">Patterns for Iterating Over Large Result Sets With Spring Data JPA</a><br>
 * </p>
 */
@AllArgsConstructor
@Component
@Slf4j
public class AppInterestOnBalanceInteractor implements InterestOnBalanceInteractor {

    private static final int SCALE = 5;

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(0.05);

    private static final BigDecimal MAX_INCREASE = BigDecimal.valueOf(2.07);

    private static final BigDecimal EPSILON = BigDecimal.valueOf(0.003);

    private final AppAccountJpaRepository accountJpaRepository;

    private final AppAccountOperationJpaRepository operationJpaRepository;

    private final EntityManager entityManager;

    /**
     * Вычисляет текущую ставку процента
     *
     * @param account Account
     * @return BigDecimal
     */
    public static BigDecimal currentRate(Account account) {
        BigDecimal firstAmount = account.getFirstTopupAmount();
        BigDecimal balance = account.getBalance();
        BigDecimal calculatedRate = firstAmount
                .divide(balance, RoundingMode.FLOOR)
                .multiply(MAX_INCREASE)
                .subtract(BigDecimal.ONE)
                .setScale(SCALE, RoundingMode.CEILING);

        log.debug("Account #{} calculated rate {}, balance {}, topup {}", account.getId(), calculatedRate, balance,
                firstAmount);

        return calculatedRate.compareTo(BASE_RATE) < 0 ? calculatedRate : BASE_RATE;
    }

    /**
     * Начисляет процент на остаток по доступным счетам
     */
    @Transactional
    @Scheduled(fixedDelay = 60, initialDelay = 60, timeUnit = TimeUnit.SECONDS)
    public void charge() {
        try (Stream<AppAccount> accounts = accountJpaRepository.findAllSuitableForInterest(MAX_INCREASE, EPSILON)) {
            accounts.forEach(account -> {
                BigDecimal amount = account.getBalance().multiply(currentRate(account));
                AppAccountOperation operation = new AppAccountOperation(account, amount, AccountOperationType.TOPUP);
                operationJpaRepository.save(operation);
                entityManager.detach(operation);
                entityManager.detach(account);
            });
        }
    }
}
