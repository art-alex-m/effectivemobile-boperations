package ru.effectivemobile.boperations.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
@Schema(name = "AppProfilePropertyDto")
public class AppProfilePropertyDto<T> {

    @Schema(description = "Profile property type", examples = {"STRING", "INSTANT"})
    private String type;

    private UUID id;

    @Schema(type = "string")
    private T value;

    public AppProfilePropertyDto(UUID id, T value) {
        this.id = id;
        this.value = value;
        this.type = value.getClass().getSimpleName().toUpperCase();
    }
}
