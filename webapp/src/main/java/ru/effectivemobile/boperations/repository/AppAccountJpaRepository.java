package ru.effectivemobile.boperations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * AppAccountJpaRepository
 */
@Repository
public interface AppAccountJpaRepository extends CrudRepository<AppAccount, UUID> {
    Optional<AppAccount> findFirstByUser_Id(UUID userId);

    @Query("select ac from AppAccount ac where ac.accountBalance.balance > 0 " +
            "and (ac.accountBalance.balance + :epsilon) / ac.firstTopup.amount < :maxIncrease")
    Stream<AppAccount> findAllSuitableForInterest(BigDecimal maxIncrease, BigDecimal epsilon);

    /**
     * Список счетов пользователя в нужной проекции
     *
     * <p>
     * <a href="https://www.baeldung.com/spring-data-jpa-projections">Spring Data JPA Projections</a><br>
     * </p>
     */
    <T> List<T> findAllByUser_Id(UUID userId, Class<T> type);
}
