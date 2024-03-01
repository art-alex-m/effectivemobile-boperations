package ru.effectivemobile.boperations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.effectivemobile.boperations.domain.core.exception.BoperationsDomainException;
import ru.effectivemobile.boperations.dto.AppError;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleValidationException(MethodArgumentNotValidException ex) {
        FieldError error = (FieldError) ex.getBindingResult().getAllErrors().get(0);
        return new AppError(ex.getClass().getName(), error.getField(), error.getDefaultMessage());
    }

    @ExceptionHandler(BoperationsDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError handleValidationException(BoperationsDomainException ex) {
        return handleAllErrors(ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppError handleSecurityException(AccessDeniedException ex) {
        return handleAllErrors(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppError handleAllErrors(Exception ex) {
        return new AppError(ex.getClass().getName(), "message", ex.getMessage());
    }
}
