package ru.effectivemobile.boperations.constraint;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Разрешен мандатный доступ по идентификатору пользователя "userId"
 * <br>
 * Сопоставляет uuid из url с uuid из контекста авторизованного пользователя
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("authentication.name == @pathParam.get('userId')")
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UserPermission {
    String PATH_TOKEN = "userId";
}
