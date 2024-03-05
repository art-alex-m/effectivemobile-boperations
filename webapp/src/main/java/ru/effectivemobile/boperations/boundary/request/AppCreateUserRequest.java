package ru.effectivemobile.boperations.boundary.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.effectivemobile.boperations.constraint.BusinessLogicCheckGroup;
import ru.effectivemobile.boperations.constraint.EmailNotTaken;
import ru.effectivemobile.boperations.constraint.Phone;
import ru.effectivemobile.boperations.constraint.PhoneNotTaken;
import ru.effectivemobile.boperations.constraint.UsernameNotTaken;
import ru.effectivemobile.boperations.domain.core.boundary.request.CreateUserRequest;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@GroupSequence({AppCreateUserRequest.class, BusinessLogicCheckGroup.class})
public class AppCreateUserRequest implements CreateUserRequest {
    @NotEmpty
    @UsernameNotTaken(groups = BusinessLogicCheckGroup.class)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    @Phone
    @PhoneNotTaken(groups = BusinessLogicCheckGroup.class)
    private String phone;

    @NotEmpty
    @Email
    @EmailNotTaken(groups = BusinessLogicCheckGroup.class)
    @Schema(format = "email")
    private String email;

    @NotNull
    private Instant birthday;

    @Positive
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private double startBalance;
}
