package ru.effectivemobile.boperations.boundary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.stream.IntStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.jdbc.Sql;
import ru.effectivemobile.boperations.boundary.request.AppUserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.support.DataJpaTestDockerized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@DataJpaTestDockerized
@Sql(value = "/db/changelog/fixtures/accounts/01-accounts.sql", executionPhase = BEFORE_TEST_CLASS)
@Sql(value = "/db/changelog/fixtures/accounts/02-operations.sql", executionPhase = BEFORE_TEST_CLASS)
public class AppUserWithdrawOperationParallelTest {

    private static final UUID userFrom = UUID.fromString("d52e7c9e-c9b9-423c-9e4f-1a35145a2182");
    private static final UUID userTo = UUID.fromString("b2e0413e-45e3-40de-920c-ce1c2bc31d9f");
    private static final String balanceJpql = "select ac.accountBalance.balance from AppAccount ac where ac.user.id = :userId";

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @PersistenceContext
    EntityManager entityManager;

    AppUserWithdrawOperationInteractor sut;

    @BeforeEach
    void setUp() {
        sut = new AppUserWithdrawOperationInteractor(entityManagerFactory);
    }

    @Test
    void givenAmountParallel_whenWithdraw_thenNotEnoughFundsException() throws Exception {
        List<ParallelWithdraw> tasks = IntStreams.range(10)
                .mapToObj(value -> BigDecimal.valueOf(150 + value))
                .map(amount -> new AppUserWithdrawOperationRequest(userFrom, userTo, amount))
                .map(request -> new ParallelWithdraw(sut, request))
                .toList();


        List<Future<UserWithdrawOperationResponse>> result = taskExecutor.getThreadPoolExecutor().invokeAll(tasks);


        BigDecimal balanceTo = getAccountBalance(userTo);
        BigDecimal balanceFrom = getAccountBalance(userFrom);
        assertThat(balanceFrom).usingComparator(BigDecimal::compareTo)
                .isLessThan(BigDecimal.valueOf(500)).isGreaterThan(BigDecimal.ZERO);
        assertThat(balanceTo).usingComparator(BigDecimal::compareTo)
                .isLessThan(BigDecimal.valueOf(1100)).isGreaterThan(BigDecimal.valueOf(500));
        result.forEach(future -> {
            try {
                System.out.printf("Thread process operation #%s%n", future.get().getOperationId());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private BigDecimal getAccountBalance(UUID userId) {
        return entityManager.createQuery(balanceJpql, BigDecimal.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @AllArgsConstructor
    public static class ParallelWithdraw implements Callable<UserWithdrawOperationResponse> {
        private final AppUserWithdrawOperationInteractor interactor;
        private final UserWithdrawOperationRequest request;

        @Override
        public UserWithdrawOperationResponse call() throws Exception {
            System.out.printf("Thread #%d amount %f%n", Thread.currentThread().getId(), request.getAmount());
            return interactor.withdraw(request);
        }
    }

    @TestConfiguration
    @EnableAsync
    public static class TestParallelConfiguration {
        @Bean
        public ThreadPoolTaskExecutor taskExecutor() {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(8);
            taskExecutor.setMaxPoolSize(16);
            taskExecutor.setQueueCapacity(64);
            return taskExecutor;
        }
    }
}
