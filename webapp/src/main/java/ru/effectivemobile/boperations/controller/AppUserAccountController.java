package ru.effectivemobile.boperations.controller;

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

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users/{userId}/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@UserPermission
public class AppUserAccountController {

    private final AppAccountJpaRepository accountJpaRepository;

    @GetMapping
    public List<AppUserAccountProjection> listAccounts(@AuthenticationPrincipal AppUserDetails userDetails) {
        return accountJpaRepository.findAllByUser_Id(userDetails.getId(), AppUserAccountProjection.class);
    }
}
