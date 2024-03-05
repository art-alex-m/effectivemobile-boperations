package ru.effectivemobile.boperations.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppAccountOperation;

@Repository
public interface AppAccountOperationJpaRepository extends CrudRepository<AppAccountOperation, AppAccountOperation.Pk> {
}
