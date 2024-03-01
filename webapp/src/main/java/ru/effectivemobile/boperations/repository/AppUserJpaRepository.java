package ru.effectivemobile.boperations.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppUser;

import java.util.UUID;

@Repository
public interface AppUserJpaRepository extends CrudRepository<AppUser, UUID> {
    boolean existsByUsername(String username);
}
