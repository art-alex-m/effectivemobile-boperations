package ru.effectivemobile.boperations.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class AppUserController {

    private final CreateUserInteractor interactor;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DomainUser createUser(@Validated @RequestBody AppCreateUserRequest request) {
        DomainUser user = interactor.create(request).getUser();
        return new AppUserDto(user.getId());
    }
}
