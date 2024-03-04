package ru.effectivemobile.boperations.boundary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.effectivemobile.boperations.boundary.response.AppProfilePropertyCrudResponse;
import ru.effectivemobile.boperations.domain.core.boundary.ProfilePropertyCreateInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.ProfilePropertyDeleteInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.ProfilePropertyUpdateInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyCreateRequest;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyDeleteRequest;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyUpdateRequest;
import ru.effectivemobile.boperations.domain.core.boundary.response.ProfilePropertyCrudResponse;
import ru.effectivemobile.boperations.domain.core.exception.BoperationsDomainException;
import ru.effectivemobile.boperations.entity.AppProfile;
import ru.effectivemobile.boperations.entity.AppProfileEmail;
import ru.effectivemobile.boperations.repository.AppProfileEmailJpaRepository;

import java.util.UUID;

/**
 * Реализует сценарии управления параметрами email
 */
@Component
@AllArgsConstructor
public class AppProfileEmailCrudInteractor implements ProfilePropertyCreateInteractor<String>,
        ProfilePropertyUpdateInteractor<String>, ProfilePropertyDeleteInteractor<String> {

    private final AppProfileEmailJpaRepository emailJpaRepository;

    @Override
    public ProfilePropertyCrudResponse<String> create(ProfilePropertyCreateRequest<String> request) {

        AppProfile profile = new AppProfile(request.getUserId());
        AppProfileEmail property = new AppProfileEmail(request.getValue(), profile);
        emailJpaRepository.save(property);

        return new AppProfilePropertyCrudResponse<>(property);
    }

    @Override
    public ProfilePropertyCrudResponse<String> delete(ProfilePropertyDeleteRequest request) {

        if (emailJpaRepository.countByProfile_Id(request.getUserId()) == 1) {
            throw new BoperationsDomainException("At least one email required");
        }

        AppProfileEmail property = getAppProfileEmail(request.getPropertyId(), request.getUserId());
        emailJpaRepository.delete(property);

        return new AppProfilePropertyCrudResponse<>(property);
    }

    @Override
    public ProfilePropertyCrudResponse<String> update(ProfilePropertyUpdateRequest<String> request) {

        AppProfileEmail property = getAppProfileEmail(request.getPropertyId(), request.getUserId());
        property.setValue(request.getValue());
        emailJpaRepository.save(property);

        return new AppProfilePropertyCrudResponse<>(property);
    }

    private AppProfileEmail getAppProfileEmail(UUID propertyId, UUID userId) {
        return emailJpaRepository.findByIdAndProfile_Id(propertyId, userId)
                .orElseThrow(() -> new BoperationsDomainException("Property not found by id"));
    }
}
