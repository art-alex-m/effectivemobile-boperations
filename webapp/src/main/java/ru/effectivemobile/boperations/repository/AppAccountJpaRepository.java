package ru.effectivemobile.boperations.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppAccount;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppAccountJpaRepository extends CrudRepository<AppAccount, UUID> {
    Optional<AppAccount> findFirstByUser_Id(UUID userId);
}
