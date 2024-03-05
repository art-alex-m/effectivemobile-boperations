package ru.effectivemobile.boperations.boundary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.boundary.response.AppUserWithdrawOperationResponse;
import ru.effectivemobile.boperations.domain.core.boundary.UserWithdrawOperationInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.domain.core.exception.BoperationsDomainException;
import ru.effectivemobile.boperations.domain.core.model.AccountOperationType;
import ru.effectivemobile.boperations.entity.AppAccount;
import ru.effectivemobile.boperations.entity.AppAccountOperation;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * AppUserWithdrawOperationInteractor
 *
 * <p>
 * <a href="https://thorben-janssen.com/5-ways-to-initialize-lazy-relations-and-when-to-use-them/">5 ways to initialize lazy associations and when to use them</a><br>
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AppUserWithdrawOperationInteractor implements UserWithdrawOperationInteractor {
    public static final String ACCOUNT_JPQL = "from AppAccount ac left join fetch ac.accountBalance ab where ac.user.id = :userId";

    private final EntityManagerFactory emFactory;

    private final Random random = new Random();

    /**
     * FIXME:
     * Примененный алгоритм зависит от нагрузки на БД.<br>
     * Тут по правильному нужно делать сначала резервирование средств, потом списание, потом начисление<br>
     * При этом разбивать списание средств на пулы обработчиков, которые обрабатывают заранее выделенный диапазон счетов.<br>
     * <br>
     * Также можно использовать блокировки на уровне записи в таблице БД.<br>
     * Но в даной архитектуре баланс счета - вычисляемое свойство. Хотя можем сделать дополнительную служебную таблицу
     * account_lock и работать по примеру liquibase, блокируя строку.<br>
     * <a href="https://stackoverflow.com/questions/51002790/locking-a-specific-row-in-postgres">Locking a specific row in Postgres</a>
     */
    @Override
    public UserWithdrawOperationResponse withdraw(UserWithdrawOperationRequest request) {
        sleepRandomTime();

        try (EntityManager entityManager = emFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            AppAccount accountFrom = getAccount(entityManager, request.getUserIdFrom())
                    .orElseThrow(() -> new BoperationsDomainException("Invalid account from"));

            if (accountFrom.getBalance().compareTo(request.getAmount()) < 0) {
                throw new BoperationsDomainException("Insufficient funds in the account from");
            }

            AppAccount accountTo = getAccount(entityManager, request.getUserIdTo())
                    .orElseThrow(() -> new BoperationsDomainException("Invalid account to"));

            AppAccountOperation withdraw = new AppAccountOperation(accountFrom, request.getAmount(),
                    AccountOperationType.WITHDRAW);
            entityManager.persist(withdraw);

            AppAccountOperation topup = new AppAccountOperation(withdraw.getId(), accountTo, request.getAmount(),
                    AccountOperationType.TOPUP);
            entityManager.persist(topup);

            entityManager.getTransaction().commit();

            return new AppUserWithdrawOperationResponse(withdraw.getId());
        }
    }

    private Optional<AppAccount> getAccount(EntityManager entityManager, UUID userId) {
        try {
            return Optional.of(entityManager.createQuery(ACCOUNT_JPQL)
                            .setParameter("userId", userId).getSingleResult())
                    .map(obj -> (AppAccount) obj);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    private void sleepRandomTime() {
        try {
            int sleep = random.nextInt(500, 1200);
            Thread.sleep(sleep);
        } catch (Exception ignored) {
        }
    }
}
