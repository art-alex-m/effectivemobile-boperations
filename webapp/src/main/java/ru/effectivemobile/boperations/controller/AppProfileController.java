package ru.effectivemobile.boperations.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.boundary.AppProfileSearchInteractor;
import ru.effectivemobile.boperations.boundary.request.AppProfileSearchRequest;
import ru.effectivemobile.boperations.dto.AppProfileDto;
import ru.effectivemobile.boperations.mapper.DomainProfileToDtoMapper;

import java.util.List;

@Tag(name = "Api")
@SecurityRequirement(name = "JwtToken")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppProfileController {

    private final AppProfileSearchInteractor interactor;

    private final DomainProfileToDtoMapper mapper;

    @Operation(summary = "Search system users")
    @ApiResponse(responseCode = "200", description = "Search result",
            content = @Content(schema = @Schema(implementation = AppPageProfileDto.class)))
    @GetMapping
    public Page<AppProfileDto> search(@Valid @ParameterObject AppProfileSearchRequest request) {
        return interactor.search(request).map(mapper::toProfileDto);
    }

    /**
     * Описание спецификации ответа Page<AppProfileDto>
     */
    @Schema(name = "AppPageProfileDto", dependentSchemas = {
            @StringToClassMapItem(key = "AppProfileDto", value = AppProfileDto.class)
    })
    @SchemaProperties({
            @SchemaProperty(name = "content", array = @ArraySchema(
                    schema = @Schema(ref = "#/components/schemas/AppProfileDto"))),
    })
    private interface AppPageProfileDto extends Page<AppProfileDto> {
        @Schema(hidden = true)
        @Override
        List<AppProfileDto> getContent();
    }
}
