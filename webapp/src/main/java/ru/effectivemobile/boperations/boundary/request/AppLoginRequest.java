package ru.effectivemobile.boperations.boundary.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.effectivemobile.boperations.domain.core.boundary.request.LoginRequest;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppLoginRequest implements LoginRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
