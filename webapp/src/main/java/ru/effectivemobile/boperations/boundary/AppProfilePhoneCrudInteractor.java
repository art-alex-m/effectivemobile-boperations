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
import ru.effectivemobile.boperations.entity.AppProfilePhone;
import ru.effectivemobile.boperations.repository.AppProfilePhoneJpaRepository;

import java.util.UUID;

/**
 * Реализует сценарии управления параметрами phone
 */
@Component
@AllArgsConstructor
public class AppProfilePhoneCrudInteractor implements ProfilePropertyCreateInteractor<String>,
        ProfilePropertyUpdateInteractor<String>, ProfilePropertyDeleteInteractor<String> {

    private final AppProfilePhoneJpaRepository phoneJpaRepository;

    @Override
    public ProfilePropertyCrudResponse<String> create(ProfilePropertyCreateRequest<String> request) {

        AppProfile profile = new AppProfile(request.getUserId());
        AppProfilePhone property = new AppProfilePhone(request.getValue(), profile);
        phoneJpaRepository.save(property);

        return new AppProfilePropertyCrudResponse<>(property);
    }

    @Override
    public ProfilePropertyCrudResponse<String> delete(ProfilePropertyDeleteRequest request) {

        if (phoneJpaRepository.countByProfile_Id(request.getUserId()) == 1) {
            throw new BoperationsDomainException("At least one phone required");
        }

        AppProfilePhone property = getAppProfilePhone(request.getPropertyId(), request.getUserId());
        phoneJpaRepository.delete(property);

        return new AppProfilePropertyCrudResponse<>(property);
    }

    @Override
    public ProfilePropertyCrudResponse<String> update(ProfilePropertyUpdateRequest<String> request) {

        AppProfilePhone property = getAppProfilePhone(request.getPropertyId(), request.getUserId());
        property.setValue(request.getValue());
        phoneJpaRepository.save(property);

        return new AppProfilePropertyCrudResponse<>(property);
    }

    private AppProfilePhone getAppProfilePhone(UUID propertyId, UUID userId) {
        return phoneJpaRepository.findByIdAndProfile_Id(propertyId, userId)
                .orElseThrow(() -> new BoperationsDomainException("Property not found by id"));
    }
}
