package ru.effectivemobile.boperations.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.constraint.UserPermission;
import ru.effectivemobile.boperations.dto.AppUserAccountProjection;
import ru.effectivemobile.boperations.repository.AppAccountJpaRepository;
import ru.effectivemobile.boperations.service.AppUserDetails;

import java.util.List;

@Tag(name = "Account")
@SecurityRequirement(name = "JwtToken")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/users/{userId}/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@UserPermission
public class AppUserAccountController {

    private final AppAccountJpaRepository accountJpaRepository;

    @Operation(summary = "List user accounts")
    @Parameter(name = "userId", in = ParameterIn.PATH, schema = @Schema(format = "uuid"))
    @GetMapping
    public List<AppUserAccountProjection> listAccounts(@AuthenticationPrincipal AppUserDetails userDetails) {
        return accountJpaRepository.findAllByUser_Id(userDetails.getId(), AppUserAccountProjection.class);
    }
}
