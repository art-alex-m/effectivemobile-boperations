package ru.effectivemobile.boperations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AppLoginDto {
    private String authorizationToken;

    private UUID userId;
}
