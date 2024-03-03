package ru.effectivemobile.boperations.dto;

import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Schema(dependentSchemas = {
        @StringToClassMapItem(key = "AppProfilePropertyDto", value = AppProfilePropertyDto.class)
})
@SchemaProperties({
        @SchemaProperty(name = "name", schema = @Schema(ref = "#/components/schemas/AppProfilePropertyDto")),
        @SchemaProperty(name = "birthday", schema = @Schema(ref = "#/components/schemas/AppProfilePropertyDto")),
        @SchemaProperty(name = "emails", array = @ArraySchema(
                schema = @Schema(ref = "#/components/schemas/AppProfilePropertyDto"), uniqueItems = true)),
        @SchemaProperty(name = "phones", array = @ArraySchema(
                schema = @Schema(ref = "#/components/schemas/AppProfilePropertyDto"), uniqueItems = true))
})
public class AppProfileDto {
    private UUID id;

    private AppUserDto user;

    @Schema(hidden = true)
    private AppProfilePropertyDto<String> name;

    @Schema(hidden = true)
    private AppProfilePropertyDto<Instant> birthday;

    @Schema(hidden = true)
    private Set<AppProfilePropertyDto<String>> phones;

    @Schema(hidden = true)
    private Set<AppProfilePropertyDto<String>> emails;
}
