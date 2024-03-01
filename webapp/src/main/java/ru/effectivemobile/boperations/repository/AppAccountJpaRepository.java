package ru.effectivemobile.boperations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppAccount;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface AppAccountJpaRepository extends CrudRepository<AppAccount, UUID> {
    Optional<AppAccount> findFirstByUser_Id(UUID userId);

    @Query("select ac from AppAccount ac where ac.accountBalance.balance > 0 and ac.accountBalance.balance / ac.firstTopup.amount <= :maxIncrease")
    Stream<AppAccount> findAllSuitableForInterest(double maxIncrease);
}
