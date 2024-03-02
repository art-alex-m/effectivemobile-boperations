package ru.effectivemobile.boperations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppProfileDto {
    private UUID id;

    private AppUserDto user;

    private AppProfilePropertyDto<String> name;

    private AppProfilePropertyDto<Instant> birthday;

    private Set<AppProfilePropertyDto<String>> phones;

    private Set<AppProfilePropertyDto<String>> emails;
}
