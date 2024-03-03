package ru.effectivemobile.boperations.boundary.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effectivemobile.boperations.domain.core.boundary.request.UserWithdrawOperationRequest;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppUserWithdrawOperationRequest implements UserWithdrawOperationRequest {

    @Setter
    @Schema(hidden = true)
    private UUID userIdFrom;

    @NotNull
    private UUID userIdTo;

    @Positive
    private BigDecimal amount;
}
