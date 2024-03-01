package ru.effectivemobile.boperations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppProfile;

import java.util.UUID;

@Repository
public interface AppProfileJpaRepository extends JpaRepository<AppProfile, UUID> {
}
