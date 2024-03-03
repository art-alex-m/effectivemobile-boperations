package ru.effectivemobile.boperations.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.boperations.boundary.request.AppLoginRequest;
import ru.effectivemobile.boperations.domain.core.boundary.LoginInteractor;
import ru.effectivemobile.boperations.domain.core.boundary.response.LoginResponse;
import ru.effectivemobile.boperations.dto.AppLoginDto;
import ru.effectivemobile.boperations.service.AppAuthTokenManager;

@Tag(name = "Api")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppLoginController {

    private final LoginInteractor interactor;

    private final AppAuthTokenManager tokenManager;

    @Operation(summary = "Login")
    @PostMapping
    public AppLoginDto login(@Valid @RequestBody AppLoginRequest request) {
        LoginResponse loginResponse = interactor.login(request);
        return new AppLoginDto(tokenManager.create(loginResponse), loginResponse.getUser().getId());
    }
}
