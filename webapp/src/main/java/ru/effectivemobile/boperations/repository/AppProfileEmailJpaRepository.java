package ru.effectivemobile.boperations.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppProfileEmail;

import java.util.UUID;

@Repository
public interface AppProfileEmailJpaRepository extends CrudRepository<AppProfileEmail, UUID> {
    boolean existsByValue(String value);
}
