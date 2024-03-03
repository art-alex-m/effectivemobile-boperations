package ru.effectivemobile.boperations.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.effectivemobile.boperations.domain.core.model.DomainUser;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Представление авторизационных данных пользователя
 */
@Getter
public class AppUserDetails implements UserDetails, DomainUser {

    private static final String PASSWORD_STUB = "[SECURED]";

    private final UUID id;

    private final String username;

    private final Collection<? extends GrantedAuthority> authorities;

    public AppUserDetails(UUID id, String username, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }

    public AppUserDetails(UUID id, String username, List<String> permissions) {
        this.id = id;
        this.username = username;
        this.authorities = permissions.stream().map(AppGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return PASSWORD_STUB;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @AllArgsConstructor
    @Getter
    public static class AppGrantedAuthority implements GrantedAuthority {
        private final String authority;
    }
}
