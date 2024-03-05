package ru.effectivemobile.boperations.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.boperations.entity.AppProfile;

import java.time.Instant;
import java.util.UUID;

/**
 * AppProfileJpaRepository
 *
 * <p>
 * <a href="https://www.baeldung.com/spring-data-criteria-queries">Use Criteria Queries in a Spring Data Application</a><br>
 * <a href="https://www.baeldung.com/spring-data-jpa-pagination-sorting">Pagination and Sorting using Spring Data JPA</a><br>
 * <a href="https://www.baeldung.com/jpa-criteria-api-in-expressions">Criteria API – An Example of IN Expressions</a><br>
 * </p>
 */
@Repository
public interface AppProfileJpaRepository extends JpaRepository<AppProfile, UUID>, JpaSpecificationExecutor<AppProfile> {

    static Specification<AppProfile> birthdayGteThan(Instant birthday) {
        return (account, criteria, builder) -> builder.greaterThan(account.get("birthday").get("value"), birthday);
    }

    static Specification<AppProfile> nameStartsWith(String name) {
        return (account, criteria, builder) -> builder.like(account.get("name").get("value"), name + "%");
    }

    static Specification<AppProfile> phoneEquals(String phone) {
        return (account, criteria, builder) -> builder.equal(account.get("phones").get("value"), phone);
    }

    static Specification<AppProfile> emailEquals(String email) {
        return (account, criteria, builder) -> builder.equal(account.get("emails").get("value"), email);
    }
}
