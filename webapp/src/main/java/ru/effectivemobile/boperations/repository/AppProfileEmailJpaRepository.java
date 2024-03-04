package ru.effectivemobile.boperations.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppProfileEmail;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppProfileEmailJpaRepository extends CrudRepository<AppProfileEmail, UUID> {
    boolean existsByValue(String value);

    Optional<AppProfileEmail> findByIdAndProfile_Id(UUID id, UUID profileId);

    int countByProfile_Id(UUID profile_id);
}
