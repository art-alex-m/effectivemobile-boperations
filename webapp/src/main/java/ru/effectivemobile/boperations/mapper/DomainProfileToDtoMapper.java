package ru.effectivemobile.boperations.mapper;

import org.mapstruct.Mapper;
import ru.effectivemobile.boperations.domain.core.model.DomainProfile;
import ru.effectivemobile.boperations.dto.AppProfileDto;

@Mapper
public interface DomainProfileToDtoMapper {
    AppProfileDto toProfileDto(DomainProfile profile);
}
