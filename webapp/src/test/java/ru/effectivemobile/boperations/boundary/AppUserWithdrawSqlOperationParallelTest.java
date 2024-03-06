package ru.effectivemobile.boperations.boundary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.stream.IntStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.jdbc.Sql;
import ru.effectivemobile.boperations.boundary.request.AppUserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.UserWithdrawOperationInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.support.DataJpaTestDockerized;
import ru.effectivemobile.boperations.support.ParallelWithdraw;
import ru.effectivemobile.boperations.support.TestParallelConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Import(TestParallelConfiguration.class)
@DataJpaTestDockerized
@Sql(value = "/db/changelog/fixtures/accounts/01-accounts.sql", executionPhase = BEFORE_TEST_CLASS)
@Sql(value = "/db/changelog/fixtures/accounts/02-operations.sql", executionPhase = BEFORE_TEST_CLASS)
public class AppUserWithdrawSqlOperationParallelTest {

    private static final UUID userFrom = UUID.fromString("d52e7c9e-c9b9-423c-9e4f-1a35145a2182");
    private static final UUID userTo = UUID.fromString("b2e0413e-45e3-40de-920c-ce1c2bc31d9f");
    private static final String balanceJpql = "select ac.accountBalance.balance from AppAccount ac where ac.user.id = :userId";

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PersistenceContext
    EntityManager entityManager;

    UserWithdrawOperationInteractor sut;

    @BeforeEach
    void setUp() {
        sut = new AppUserWithdrawSqlOperationInteractor(jdbcTemplate);
    }

    @Test
    void givenAmountParallel_whenWithdraw_thenNotEnoughFundsException() throws Exception {
        List<ParallelWithdraw> tasks = IntStreams.range(32)
                .mapToObj(value -> BigDecimal.valueOf(150 + value))
                .map(amount -> new AppUserWithdrawOperationRequest(userFrom, userTo, amount))
                .map(request -> new ParallelWithdraw(sut, request))
                .toList();


        List<Future<UserWithdrawOperationResponse>> result = taskExecutor.getThreadPoolExecutor().invokeAll(tasks);


        while (taskExecutor.getActiveCount() > 0) ;
        BigDecimal balanceTo = getAccountBalance(userTo);
        BigDecimal balanceFrom = getAccountBalance(userFrom);
        assertThat(balanceFrom).usingComparator(BigDecimal::compareTo)
                .isLessThan(BigDecimal.valueOf(500)).isGreaterThan(BigDecimal.ZERO);
        assertThat(balanceTo).usingComparator(BigDecimal::compareTo)
                .isLessThan(BigDecimal.valueOf(1100)).isGreaterThan(BigDecimal.valueOf(500));
        result.forEach(future -> {
            try {
                System.out.printf("Withdraw: Thread process operation #%s%n", future.get().getOperationId());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Withdraw: " + e.getMessage());
            }
        });
    }

    private BigDecimal getAccountBalance(UUID userId) {
        return entityManager.createQuery(balanceJpql, BigDecimal.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
