package ru.effectivemobile.boperations.boundary.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.effectivemobile.boperations.domain.core.boundary.request.CreateUserRequest;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppCreateUserRequest implements CreateUserRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Instant birthday;

    @Positive
    private double startBalance;
}
