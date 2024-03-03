package ru.effectivemobile.boperations.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.boundary.request.AppUserWithdrawOperationRequest;
import ru.effectivemobile.boperations.constraint.UserPermission;
import ru.effectivemobile.boperations.domain.core.boundary.UserWithdrawOperationInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.response.UserWithdrawOperationResponse;
import ru.effectivemobile.boperations.service.AppUserDetails;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users/{userId}/accounts/1/operations",
        produces = MediaType.APPLICATION_JSON_VALUE)
@UserPermission
public class AppUserOperationController {

    private final UserWithdrawOperationInteractor interactor;

    @PostMapping
    public UserWithdrawOperationResponse withdraw(@Valid @RequestBody AppUserWithdrawOperationRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        request.setUserIdFrom(userDetails.getId());
        return interactor.withdraw(request);
    }
}
