package ru.effectivemobile.boperations.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(name = "AppAccountDto")
public interface AppUserAccountProjection {

    @Value("#{target.getBalance()}")
    BigDecimal getBalance();

    UUID getId();
}
