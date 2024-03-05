package ru.effectivemobile.boperations.boundary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.domain.core.boundary.ProfileSearchInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfileSearchRequest;
import ru.effectivemobile.boperations.domain.core.model.DomainProfile;
import ru.effectivemobile.boperations.entity.AppProfile;
import ru.effectivemobile.boperations.repository.AppProfileJpaRepository;
import ru.effectivemobile.boperations.service.AppPagingService;

import java.util.ArrayList;
import java.util.List;

import static ru.effectivemobile.boperations.repository.AppProfileJpaRepository.birthdayGteThan;
import static ru.effectivemobile.boperations.repository.AppProfileJpaRepository.emailEquals;
import static ru.effectivemobile.boperations.repository.AppProfileJpaRepository.nameStartsWith;
import static ru.effectivemobile.boperations.repository.AppProfileJpaRepository.phoneEquals;

@AllArgsConstructor
@Component
public class AppProfileSearchInteractor implements ProfileSearchInteractor {

    private final AppPagingService pagingService;

    private final AppProfileJpaRepository profileJpaRepository;

    public Page<DomainProfile> search(ProfileSearchRequest request) {
        Pageable pageable = pagingService.paginate(request);

        List<Specification<AppProfile>> specifications = new ArrayList<>(4);
        if (request.hasBirthday()) {
            specifications.add(birthdayGteThan(request.getBirthday()));
        }
        if (request.hasName()) {
            specifications.add(nameStartsWith(request.getName()));
        }
        if (request.hasPhone()) {
            specifications.add(phoneEquals(request.getPhone()));
        }
        if (request.hasEmail()) {
            specifications.add(emailEquals(request.getEmail()));
        }

        Page<AppProfile> profiles = specifications.isEmpty()
                ? profileJpaRepository.findAll(pageable)
                : profileJpaRepository.findAll(Specification.allOf(specifications), pageable);

        return profiles.map(profile -> (DomainProfile) profile);
    }
}
