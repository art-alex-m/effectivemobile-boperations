package ru.effectivemobile.boperations.boundary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.stream.IntStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.effectivemobile.boperations.boundary.request.AppUserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.domain.core.exception.BoperationsDomainException;
import ru.effectivemobile.boperations.domain.core.model.AccountOperationType;
import ru.effectivemobile.boperations.entity.AppAccountOperation;
import ru.effectivemobile.boperations.repository.AppAccountJpaRepository;
import ru.effectivemobile.boperations.repository.AppAccountOperationJpaRepository;
import ru.effectivemobile.boperations.support.DataJpaTestDockerized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@DataJpaTestDockerized
@Sql(value = "/db/changelog/fixtures/accounts/01-accounts.sql", executionPhase = BEFORE_TEST_CLASS)
@Sql(value = "/db/changelog/fixtures/accounts/02-operations.sql", executionPhase = BEFORE_TEST_CLASS)
class AppUserWithdrawOperationInteractorTest {
    private static final UUID userFrom = UUID.fromString("d52e7c9e-c9b9-423c-9e4f-1a35145a2182");
    private static final UUID userTo = UUID.fromString("b2e0413e-45e3-40de-920c-ce1c2bc31d9f");
    private static final String balanceJpql = "select ac.accountBalance.balance from AppAccount ac where ac.user.id = :userId";
    private static final String operationsJpql = "select op from AppAccountOperation op where op.primaryKey.operationId = :id order by op.createdAt asc";

    @Autowired
    AppAccountJpaRepository accountJpaRepository;

    @Autowired
    AppAccountOperationJpaRepository operationJpaRepository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    EntityManager entityManager;

    AppUserWithdrawOperationInteractor sut;

    @BeforeEach
    void setUp() {
        sut = new AppUserWithdrawOperationInteractor(entityManagerFactory);
    }

    @Test
    void givenRequest_whenWithdraw_thenSuccessAccountOperations() {
        BigDecimal amount = BigDecimal.valueOf(100);
        UserWithdrawOperationRequest request = new AppUserWithdrawOperationRequest(userFrom, userTo, amount);

        UserWithdrawOperationResponse result = sut.withdraw(request);

        assertThat(result).isNotNull();
        assertThat(result.getOperationId()).isNotNull();
        List<AppAccountOperation> operations = getOperations(result.getOperationId());
        assertThat(operations).hasSize(2);
        assertThat(operations.get(0).getType()).isEqualTo(AccountOperationType.WITHDRAW);
        assertThat(operations.get(1).getType()).isEqualTo(AccountOperationType.TOPUP);
        assertThat(operations.get(0).getAmount()).usingComparator(BigDecimal::compareTo).isEqualTo(amount);
        assertThat(operations.get(1).getAmount()).usingComparator(BigDecimal::compareTo).isEqualTo(amount);
        assertThat(operations.get(0).getAccount().getUser().getId()).isEqualTo(userFrom);
        assertThat(operations.get(1).getAccount().getUser().getId()).isEqualTo(userTo);
        BigDecimal balanceFrom = getAccountBalance(userFrom);
        BigDecimal balanceTo = getAccountBalance(userTo);
        assertThat(balanceFrom).usingComparator(BigDecimal::compareTo).isEqualTo(BigDecimal.valueOf(830));
        assertThat(balanceTo).usingComparator(BigDecimal::compareTo).isEqualTo(BigDecimal.valueOf(270));
    }

    @Test
    void givenAmountSequence_whenWithdraw_thenNotEnoughFundsException() {

        Throwable result = catchThrowable(() -> {
            IntStreams.range(10)
                    .mapToObj(value -> BigDecimal.valueOf(150 + value))
                    .map(amount -> new AppUserWithdrawOperationRequest(userFrom, userTo, amount))
                    .forEach(sut::withdraw);
        });


        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(BoperationsDomainException.class)
                .hasMessageContaining("Insufficient funds in the account from");
        BigDecimal balanceTo = getAccountBalance(userTo);
        BigDecimal balanceFrom = getAccountBalance(userFrom);
        assertThat(balanceFrom).usingComparator(BigDecimal::compareTo)
                .isGreaterThan(BigDecimal.ONE).isLessThan(BigDecimal.valueOf(500));
        assertThat(balanceTo).usingComparator(BigDecimal::compareTo)
                .isGreaterThan(BigDecimal.valueOf(170)).isLessThan(BigDecimal.valueOf(1500));
    }

    @Test
    void givenInfinityAmount_whenWithdraw_thenNotEnoughFundsException() {
        BigDecimal amount = BigDecimal.valueOf(100000);
        UserWithdrawOperationRequest request = new AppUserWithdrawOperationRequest(userFrom, userTo, amount);

        Throwable result = catchThrowable(() -> sut.withdraw(request));

        assertThat(result).isInstanceOf(BoperationsDomainException.class)
                .hasMessageContaining("Insufficient funds in the account from");
    }

    @Test
    void givenUnknownUserFrom_whenWithdraw_thenInvalidAccountFromException() {
        UUID userFrom = UUID.fromString("d52e7c9e-0000-0000-0000-1a35145a2182");
        BigDecimal amount = BigDecimal.valueOf(0);
        UserWithdrawOperationRequest request = new AppUserWithdrawOperationRequest(userFrom, userTo, amount);

        Throwable result = catchThrowable(() -> sut.withdraw(request));

        assertThat(result).isInstanceOf(BoperationsDomainException.class)
                .hasMessageContaining("Invalid account from");
    }

    @Test
    void givenUnknownUserTo_whenWithdraw_thenInvalidAccountToException() {
        UUID userTo = UUID.fromString("d52e7c9e-0000-0000-0000-1a35145a2182");
        BigDecimal amount = BigDecimal.valueOf(0);
        UserWithdrawOperationRequest request = new AppUserWithdrawOperationRequest(userFrom, userTo, amount);

        Throwable result = catchThrowable(() -> sut.withdraw(request));

        assertThat(result).isInstanceOf(BoperationsDomainException.class)
                .hasMessageContaining("Invalid account to");
    }

    private BigDecimal getAccountBalance(UUID userId) {
        return entityManager.createQuery(balanceJpql, BigDecimal.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    private List<AppAccountOperation> getOperations(UUID operationId) {
        return entityManager.createQuery(operationsJpql, AppAccountOperation.class)
                .setParameter("id", operationId)
                .getResultList();
    }
}
