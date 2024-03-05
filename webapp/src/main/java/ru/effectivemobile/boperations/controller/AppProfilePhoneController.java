package ru.effectivemobile.boperations.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.boundary.AppProfilePhoneCrudInteractor;
import ru.effectivemobile.boperations.boundary.request.AppProfilePhoneRequest;
import ru.effectivemobile.boperations.boundary.request.AppProfilePropertyCreateRequest;
import ru.effectivemobile.boperations.boundary.request.AppProfilePropertyDeleteRequest;
import ru.effectivemobile.boperations.boundary.request.AppProfilePropertyUpdateRequest;
import ru.effectivemobile.boperations.constraint.UserPermission;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyCreateRequest;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyDeleteRequest;
import ru.effectivemobile.boperations.domain.core.boundary.request.ProfilePropertyUpdateRequest;
import ru.effectivemobile.boperations.dto.AppProfilePropertyDto;
import ru.effectivemobile.boperations.mapper.DomainProfileToDtoMapper;
import ru.effectivemobile.boperations.service.AppUserDetails;

import java.util.UUID;

@Tag(name = "Profile")
@SecurityRequirement(name = "JwtToken")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/profiles/{userId}/phones", produces = MediaType.APPLICATION_JSON_VALUE)
@UserPermission
public class AppProfilePhoneController {

    private final AppProfilePhoneCrudInteractor interactor;

    private final DomainProfileToDtoMapper mapper;

    @Operation(summary = "Add new phone")
    @Parameter(name = "userId", in = ParameterIn.PATH, schema = @Schema(format = "uuid"))
    @PostMapping
    public AppProfilePropertyDto<String> create(@Valid @RequestBody AppProfilePhoneRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        ProfilePropertyCreateRequest<String> createRequest = new AppProfilePropertyCreateRequest<>(userDetails.getId(),
                request.getValue());

        return mapper.toPropertyStringDto(interactor.create(createRequest).getProperty());
    }

    @Operation(summary = "Update phone")
    @Parameter(name = "userId", in = ParameterIn.PATH, schema = @Schema(format = "uuid"))
    @Parameter(name = "propertyId", in = ParameterIn.PATH, schema = @Schema(format = "uuid"))
    @PatchMapping("/{propertyId}")
    public AppProfilePropertyDto<String> update(@PathVariable UUID propertyId,
            @Valid @RequestBody AppProfilePhoneRequest request, @AuthenticationPrincipal AppUserDetails userDetails) {

        ProfilePropertyUpdateRequest<String> updateRequest = new AppProfilePropertyUpdateRequest<>(userDetails.getId(),
                propertyId, request.getValue());

        return mapper.toPropertyStringDto(interactor.update(updateRequest).getProperty());
    }

    @Operation(summary = "Delete phone", description = "Last phone cannot be deleted")
    @Parameter(name = "userId", in = ParameterIn.PATH, schema = @Schema(format = "uuid"))
    @Parameter(name = "propertyId", in = ParameterIn.PATH, schema = @Schema(format = "uuid"))
    @DeleteMapping("/{propertyId}")
    public AppProfilePropertyDto<String> delete(@PathVariable UUID propertyId,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        ProfilePropertyDeleteRequest deleteRequest = new AppProfilePropertyDeleteRequest(userDetails.getId(),
                propertyId);

        return mapper.toPropertyStringDto(interactor.delete(deleteRequest).getProperty());
    }
}
