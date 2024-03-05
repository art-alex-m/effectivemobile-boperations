package ru.effectivemobile.boperations.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.boundary.request.AppUserWithdrawOperationRequest;
import ru.effectivemobile.boperations.boundary.response.AppUserWithdrawOperationResponse;
import ru.effectivemobile.boperations.constraint.UserPermission;
import ru.effectivemobile.boperations.domain.core.boundary.UserWithdrawOperationInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.service.AppUserDetails;

@Tag(name = "Account")
@SecurityRequirement(name = "JwtToken")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/users/{userId}/accounts/1/operations", produces = MediaType.APPLICATION_JSON_VALUE)
@UserPermission
public class AppUserOperationController {

    private final UserWithdrawOperationInteractor interactor;

    @Operation(summary = "Intrabank transfer", responses = {
            @ApiResponse(responseCode = "201", description = "Create operation",
                    content = @Content(schema = @Schema(implementation = AppUserWithdrawOperationResponse.class)))})
    @Parameter(name = "userId", in = ParameterIn.PATH, schema = @Schema(format = "uuid"))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserWithdrawOperationResponse withdraw(@Valid @RequestBody AppUserWithdrawOperationRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        request.setUserIdFrom(userDetails.getId());
        return interactor.withdraw(request);
    }
}
