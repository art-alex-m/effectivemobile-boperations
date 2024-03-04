package ru.effectivemobile.boperations.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.boundary.request.AppCreateUserRequest;
import ru.effectivemobile.boperations.domain.core.boundary.CreateUserInteractor;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;
import ru.effectivemobile.boperations.dto.AppUserDto;

@Tag(name = "User")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController {

    private final CreateUserInteractor interactor;

    @Operation(summary = "Create new user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDto createUser(@Validated @RequestBody AppCreateUserRequest request) {
        DomainUser user = interactor.create(request).getUser();
        return new AppUserDto(user.getId());
    }
}
