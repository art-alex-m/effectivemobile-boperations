package ru.effectivemobile.boperations.mapper;

import org.mapstruct.Mapper;
import ru.effectivemobile.boperations.domain.core.model.DomainProfile;
import ru.effectivemobile.boperations.domain.core.model.ProfileProperty;
import ru.effectivemobile.boperations.dto.AppProfileDto;
import ru.effectivemobile.boperations.dto.AppProfilePropertyDto;

@Mapper
public interface DomainProfileToDtoMapper {
    AppProfileDto toProfileDto(DomainProfile profile);

    AppProfilePropertyDto<String> toPropertyStringDto(ProfileProperty<String> property);
}
