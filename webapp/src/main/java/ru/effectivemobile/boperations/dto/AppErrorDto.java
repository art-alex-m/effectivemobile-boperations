package ru.effectivemobile.boperations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Представление ошибки сервиса
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppErrorDto {
    private String className;

    private String field;

    private String message;
}
